package minecraft.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Level;

/**
 * Player-to-player auction house (fixed-price listings).
 *
 * <p>Listings and unclaimed items are saved to {@code auctions.yml} after every change so a
 * crash can never duplicate or lose an item.</p>
 */
public final class AuctionHouse {

    public static final class Listing {
        public final long id;
        public final UUID seller;
        public final String sellerName;
        public final ItemStack item;
        public final long price;
        public final long created;
        public final long expires;
        public final boolean synthetic;
        public final String archetype;
        public final double[] aiInput;

        Listing(long id, UUID seller, String sellerName, ItemStack item, long price, long created, long expires) {
            this(id, seller, sellerName, item, price, created, expires, false, null, null);
        }

        Listing(long id, UUID seller, String sellerName, ItemStack item, long price, long created, long expires,
                boolean synthetic, String archetype, double[] aiInput) {
            this.id = id;
            this.seller = seller;
            this.sellerName = sellerName;
            this.item = item;
            this.price = price;
            this.created = created;
            this.expires = expires;
            this.synthetic = synthetic;
            this.archetype = archetype;
            this.aiInput = aiInput;
        }
    }

    private final EconomyPlugin plugin;
    private final File file;
    private final Map<Long, Listing> listings = new LinkedHashMap<>();
    private final Map<UUID, List<ItemStack>> claims = new HashMap<>();
    private long nextId = 1;

    public AuctionHouse(EconomyPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "auctions.yml");
    }

    // ------------------------------------------------------------------ config

    private long durationMillis() {
        return plugin.getConfig().getLong("auction.duration-hours", 48) * 3_600_000L;
    }

    private int maxListings() {
        return plugin.getConfig().getInt("auction.max-listings-per-player", 10);
    }

    private double taxPercent() {
        return Math.max(0, Math.min(100, plugin.getConfig().getDouble("auction.tax-percent", 5)));
    }

    public long maxPrice() {
        return Math.min(EconomyData.maxBalance(), plugin.getConfig().getLong("auction.max-price", 1_000_000_000_000L));
    }

    // ------------------------------------------------------------------ queries

    /** All active listings, newest first. */
    public List<Listing> all() {
        List<Listing> list = new ArrayList<>(listings.values());
        list.sort((a, b) -> Long.compare(b.created, a.created));
        return list;
    }

    public Listing get(long id) {
        return listings.get(id);
    }

    public int countOf(UUID seller) {
        int c = 0;
        for (Listing l : listings.values()) if (l.seller.equals(seller)) c++;
        return c;
    }

    public int claimCount(UUID player) {
        List<ItemStack> list = claims.get(player);
        return list == null ? 0 : list.size();
    }

    public int syntheticCount() {
        int c = 0;
        for (Listing l : listings.values()) if (l.synthetic) c++;
        return c;
    }

    public int syntheticCount(String archetype) {
        int c = 0;
        for (Listing l : listings.values()) {
            if (l.synthetic && Objects.equals(l.archetype, archetype)) c++;
        }
        return c;
    }

    // ------------------------------------------------------------------ actions

    /** Lists the item the player is holding. */
    public void create(Player seller, long price) {
        ItemStack hand = seller.getInventory().getItemInMainHand();
        if (hand.getType().isAir()) {
            seller.sendMessage(ChatColor.RED + "Hold the item you want to sell.");
            return;
        }
        if (price > maxPrice()) {
            seller.sendMessage(ChatColor.RED + "Maximum price is " + EconomyData.formatMoney(maxPrice()) + ".");
            return;
        }
        if (countOf(seller.getUniqueId()) >= maxListings()) {
            seller.sendMessage(ChatColor.RED + "You already have " + maxListings() + " active listings.");
            return;
        }
        if (SellAxe.isSellAxe(hand)) {
            seller.sendMessage(ChatColor.RED + "Sell Axes can't be auctioned.");
            return;
        }

        ItemStack stored = hand.clone();
        seller.getInventory().setItemInMainHand(null);

        long now = System.currentTimeMillis();
        Listing l = new Listing(nextId++, seller.getUniqueId(), seller.getName(), stored, price, now, now + durationMillis());
        listings.put(l.id, l);
        save();

        seller.sendMessage(ChatColor.GREEN + "Listed " + stored.getAmount() + "x " + prettyName(stored) + " for "
                + ChatColor.GOLD + EconomyData.formatMoney(price) + ChatColor.GREEN + ".");
    }

    public long addSynthetic(UUID seller, String sellerName, ItemStack item, long price, String archetype,
                             double[] aiInput, long durationMillis) {
        long now = System.currentTimeMillis();
        Listing l = new Listing(nextId++, seller, sellerName, item.clone(), price, now, now + durationMillis,
                true, archetype, aiInput);
        listings.put(l.id, l);
        return l.id;
    }

    public void buy(Player buyer, long id) {
        Listing l = listings.get(id);
        if (l == null) {
            buyer.sendMessage(ChatColor.RED + "That listing is no longer available.");
            return;
        }
        if (l.seller.equals(buyer.getUniqueId())) {
            buyer.sendMessage(ChatColor.RED + "You can't buy your own listing.");
            return;
        }
        if (!Items.canFit(buyer, l.item, l.item.getAmount())) {
            buyer.sendMessage(ChatColor.RED + "Your inventory is full!");
            return;
        }
        if (!EconomyData.withdraw(buyer.getUniqueId(), l.price)) {
            buyer.sendMessage(ChatColor.RED + "Not enough money! Need " + EconomyData.formatMoney(l.price)
                    + ", have " + EconomyData.formatMoney(EconomyData.getBalance(buyer)) + ".");
            return;
        }

        listings.remove(id);
        Items.give(buyer, l.item, l.item.getAmount());

        buyer.sendMessage(ChatColor.GREEN + "Bought " + l.item.getAmount() + "x " + prettyName(l.item) + " for "
                + ChatColor.GOLD + EconomyData.formatMoney(l.price) + ChatColor.GREEN + ".");
        if (SpecialItems.isSpecial(l.item)) {
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + buyer.getName()
                    + ChatColor.RESET + ChatColor.LIGHT_PURPLE + " claimed the relic " + prettyName(l.item)
                    + ChatColor.LIGHT_PURPLE + " for " + ChatColor.GOLD + EconomyData.formatMoney(l.price)
                    + ChatColor.LIGHT_PURPLE + "!");
        }

        if (l.synthetic) {
            AuctionAi ai = EconomyPlugin.get().ai();
            if (ai != null) ai.onSold(l);
        } else {
            long tax = (long) Math.floor(l.price * taxPercent() / 100.0);
            long proceeds = l.price - tax;
            EconomyData.deposit(l.seller, proceeds);
            Player sellerOnline = Bukkit.getPlayer(l.seller);
            if (sellerOnline != null) {
                sellerOnline.sendMessage(ChatColor.GREEN + buyer.getName() + " bought your " + l.item.getAmount() + "x "
                        + prettyName(l.item) + " — you received " + ChatColor.GOLD + EconomyData.formatMoney(proceeds)
                        + ChatColor.GRAY + (tax > 0 ? " (" + EconomyData.formatMoney(tax) + " tax)" : "") + ChatColor.GREEN + ".");
            }
        }
        save();
        EconomyData.save(); // keep money and items consistent on disk
    }

    /** Removes a listing owned by the player and returns the item (or stores it for collection). */
    public void cancel(Player owner, long id) {
        Listing l = listings.get(id);
        if (l == null) {
            owner.sendMessage(ChatColor.RED + "That listing is no longer available.");
            return;
        }
        if (l.synthetic) {
            owner.sendMessage(ChatColor.RED + "That listing belongs to another seller.");
            return;
        }
        if (!l.seller.equals(owner.getUniqueId()) && !owner.hasPermission("economy.admin")) {
            owner.sendMessage(ChatColor.RED + "That isn't your listing.");
            return;
        }
        listings.remove(id);
        if (l.seller.equals(owner.getUniqueId()) && Items.canFit(owner, l.item, l.item.getAmount())) {
            Items.give(owner, l.item, l.item.getAmount());
            owner.sendMessage(ChatColor.YELLOW + "Listing cancelled, item returned.");
        } else {
            addClaim(l.seller, l.item);
            owner.sendMessage(ChatColor.YELLOW + "Listing cancelled. Use /ah collect to get the item back.");
        }
        save();
    }

    /** Moves expired listings into their owners' collection boxes. Returns how many expired. */
    public int expireOld() {
        long now = System.currentTimeMillis();
        List<Listing> expired = new ArrayList<>();
        for (Listing l : listings.values()) if (l.expires <= now) expired.add(l);
        for (Listing l : expired) {
            listings.remove(l.id);
            if (l.synthetic) {
                AuctionAi ai = EconomyPlugin.get().ai();
                if (ai != null) ai.onExpired(l);
                continue;
            }
            addClaim(l.seller, l.item);
            Player p = Bukkit.getPlayer(l.seller);
            if (p != null) {
                p.sendMessage(ChatColor.YELLOW + "Your auction for " + prettyName(l.item)
                        + " expired. Use /ah collect to get it back.");
            }
        }
        if (!expired.isEmpty()) save();
        return expired.size();
    }

    /** Gives the player as many collectable items as fit. */
    public void collect(Player player) {
        List<ItemStack> list = claims.get(player.getUniqueId());
        if (list == null || list.isEmpty()) {
            player.sendMessage(ChatColor.GRAY + "You have nothing to collect.");
            return;
        }
        int given = 0;
        Iterator<ItemStack> it = list.iterator();
        while (it.hasNext()) {
            ItemStack item = it.next();
            if (!Items.canFit(player, item, item.getAmount())) break;
            Items.give(player, item, item.getAmount());
            it.remove();
            given++;
        }
        if (list.isEmpty()) claims.remove(player.getUniqueId());
        save();
        if (given > 0) player.sendMessage(ChatColor.GREEN + "Collected " + given + " stack" + (given == 1 ? "" : "s") + ".");
        if (claimCount(player.getUniqueId()) > 0) {
            player.sendMessage(ChatColor.YELLOW + "Some items didn't fit — free up space and run /ah collect again.");
        }
    }

    private void addClaim(UUID owner, ItemStack item) {
        claims.computeIfAbsent(owner, k -> new ArrayList<>()).add(item.clone());
    }

    // ------------------------------------------------------------------ display helpers

    public static String prettyName(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) return item.getItemMeta().getDisplayName();
        StringBuilder sb = new StringBuilder();
        for (String part : item.getType().name().toLowerCase(Locale.ROOT).split("_")) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return sb.toString();
    }

    public static String timeLeft(long expires) {
        long ms = expires - System.currentTimeMillis();
        if (ms <= 0) return "expiring";
        long h = ms / 3_600_000L;
        long m = (ms % 3_600_000L) / 60_000L;
        return h > 0 ? h + "h " + m + "m" : m + "m";
    }

    // ------------------------------------------------------------------ persistence

    public void load() {
        listings.clear();
        claims.clear();
        nextId = 1;
        if (!file.exists()) return;

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        nextId = cfg.getLong("next-id", 1);

        ConfigurationSection ls = cfg.getConfigurationSection("listings");
        if (ls != null) {
            for (String key : ls.getKeys(false)) {
                try {
                    ConfigurationSection s = ls.getConfigurationSection(key);
                    if (s == null) continue;
                    ItemStack item = s.getItemStack("item");
                    if (item == null) continue;
                    long id = Long.parseLong(key);
                    List<Double> rawInput = s.getDoubleList("ai-input");
                    double[] aiInput = rawInput.isEmpty() ? null : rawInput.stream().mapToDouble(Double::doubleValue).toArray();
                    listings.put(id, new Listing(id, UUID.fromString(Objects.requireNonNull(s.getString("seller"))),
                            s.getString("seller-name", "?"), item, s.getLong("price"),
                            s.getLong("created"), s.getLong("expires"),
                            s.getBoolean("synthetic", false), s.getString("archetype", null), aiInput));
                    nextId = Math.max(nextId, id + 1);
                } catch (RuntimeException ex) {
                    plugin.getLogger().warning("Skipping malformed auction listing '" + key + "'");
                }
            }
        }

        ConfigurationSection cs = cfg.getConfigurationSection("claims");
        if (cs != null) {
            for (String key : cs.getKeys(false)) {
                try {
                    UUID owner = UUID.fromString(key);
                    List<ItemStack> items = new ArrayList<>();
                    for (Object o : cs.getList(key, List.of())) if (o instanceof ItemStack is) items.add(is);
                    if (!items.isEmpty()) claims.put(owner, items);
                } catch (IllegalArgumentException ignored) {
                    // bad uuid
                }
            }
        }
        plugin.getLogger().info("Loaded " + listings.size() + " auction listings.");
    }

    public void save() {
        YamlConfiguration cfg = new YamlConfiguration();
        cfg.set("next-id", nextId);
        for (Listing l : listings.values()) {
            String base = "listings." + l.id;
            cfg.set(base + ".seller", l.seller.toString());
            cfg.set(base + ".seller-name", l.sellerName);
            cfg.set(base + ".item", l.item);
            cfg.set(base + ".price", l.price);
            cfg.set(base + ".created", l.created);
            cfg.set(base + ".expires", l.expires);
            if (l.synthetic) {
                cfg.set(base + ".synthetic", true);
                cfg.set(base + ".archetype", l.archetype);
                if (l.aiInput != null) {
                    List<Double> input = new ArrayList<>(l.aiInput.length);
                    for (double v : l.aiInput) input.add(v);
                    cfg.set(base + ".ai-input", input);
                }
            }
        }
        for (Map.Entry<UUID, List<ItemStack>> e : claims.entrySet()) {
            cfg.set("claims." + e.getKey(), e.getValue());
        }

        File tmp = new File(file.getParentFile(), "auctions.yml.tmp");
        try {
            Files.createDirectories(file.getParentFile().toPath());
            Files.writeString(tmp.toPath(), cfg.saveToString(), StandardCharsets.UTF_8);
            Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save auctions.yml", ex);
        }
    }
}
