package minecraft.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public final class MysteryShop {

    public enum Tier {
        SCRAP("scrap", "Scrap Pouch", Material.BUNDLE, 1_000L,
                AiItemFactory.Quality.COMMON, 0.015, 0.75, "Mostly junk, occasionally useful."),
        ADVENTURER("adventurer", "Adventurer Crate", Material.BARREL, 10_000L,
                AiItemFactory.Quality.UNCOMMON, 0.018, 1.06, "A traveller's mixed supplies."),
        TREASURE("treasure", "Treasure Crate", Material.CHEST, 50_000L,
                AiItemFactory.Quality.RARE, 0.020, 1.12, "Gear with a real chance of profit."),
        RELIC("relic", "Relic Crate", Material.ENDER_CHEST, 200_000L,
                AiItemFactory.Quality.EPIC, 0.025, 1.25, "High-value relics and enchanted gear."),
        NEURAL("neural", "Neural Vault", Material.BEACON, 1_000_000L,
                AiItemFactory.Quality.LEGENDARY, 0.030, 3.00, "The engine's rarest and riskiest vault.");

        private final String id;
        private final String label;
        private final Material icon;
        private final long baseCost;
        private final AiItemFactory.Quality quality;
        private final double jackpotChance;
        private final double payoutFactor;
        private final String description;

        Tier(String id, String label, Material icon, long baseCost,
             AiItemFactory.Quality quality, double jackpotChance, double payoutFactor, String description) {
            this.id = id;
            this.label = label;
            this.icon = icon;
            this.baseCost = baseCost;
            this.quality = quality;
            this.jackpotChance = jackpotChance;
            this.payoutFactor = payoutFactor;
            this.description = description;
        }

        public String id() {
            return id;
        }

        public String label() {
            return label;
        }

        public Material icon() {
            return icon;
        }

        public AiItemFactory.Quality quality() {
            return quality;
        }

        public double jackpotChance() {
            return jackpotChance;
        }

        public String description() {
            return description;
        }

        public double payoutFactor() {
            return payoutFactor;
        }

        public static Tier byId(String id) {
            if (id == null) return null;
            for (Tier tier : values()) {
                if (tier.id.equalsIgnoreCase(id) || tier.name().equalsIgnoreCase(id)) return tier;
            }
            return null;
        }
    }

    public record Status(int stock, int capacity, long restockAt) {
    }

    public record Result(Tier tier, List<ItemStack> items, long value, boolean jackpot) {
        public ItemStack primary() {
            ItemStack best = null;
            long bestValue = -1;
            for (ItemStack item : items) {
                long value = AiItemFactory.value(item);
                if (value > bestValue) {
                    best = item;
                    bestValue = value;
                }
            }
            return best;
        }
    }

    private static final class TierStock {
        int stock;
        long nextRestock;
    }

    private static final int INPUTS = 3;
    private static final int HIDDEN = 6;
    private static final int OUTPUTS = 3;

    private final EconomyPlugin plugin;
    private final File file;
    private final Map<Tier, TierStock> stocks = new LinkedHashMap<>();
    private final Map<UUID, Integer> dryStreaks = new LinkedHashMap<>();

    private Random rng = new Random(7331);
    private NeuralNetwork brain = new NeuralNetwork(7331, INPUTS, HIDDEN, OUTPUTS);
    private boolean enabled = true;
    private int capacity = 8;
    private long restockMillis = 20L * 60_000L;
    private long seed = 7331;
    private double payoutScale = 1.0;
    private double priceMultiplier = 1.0;
    private boolean broadcastJackpots = true;
    private long opens;
    private long spent;
    private long paid;
    private long biggestWin;

    public MysteryShop(EconomyPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "mystery.yml");
    }

    public void load() {
        applySettings();
        rng = new Random(seed);
        for (Tier tier : Tier.values()) stocks.put(tier, new TierStock());

        boolean fresh = !file.exists();
        if (!fresh) read();
        long now = System.currentTimeMillis();
        for (Tier tier : Tier.values()) {
            TierStock stock = stocks.get(tier);
            if (fresh) stock.stock = capacity;
            if (stock.nextRestock <= 0) stock.nextRestock = now + jitter(restockMillis);
            if (fresh && tier == Tier.NEURAL) {
                stock.stock = Math.max(0, capacity / 2);
                stock.nextRestock = now + jitter(restockMillis);
            }
        }
        save();
    }

    public void reload() {
        applySettings();
        save();
    }

    private void applySettings() {
        ConfigurationSection cfg = plugin.getConfig();
        enabled = cfg.getBoolean("mystery.enabled", true);
        capacity = Math.max(1, Math.min(100, cfg.getInt("mystery.stock-per-tier", 8)));
        restockMillis = Math.max(1, cfg.getLong("mystery.restock-minutes", 20)) * 60_000L;
        seed = cfg.getLong("mystery.seed", 7331L);
        payoutScale = Math.max(0.25, Math.min(3.0, cfg.getDouble("mystery.payout-scale", 1.0)));
        priceMultiplier = Math.max(0.1, Math.min(20.0, cfg.getDouble("mystery.price-multiplier", 1.0)));
        broadcastJackpots = cfg.getBoolean("mystery.broadcast-jackpots", true);
    }

    public boolean enabled() {
        return enabled;
    }

    public List<Tier> tiers() {
        return List.of(Tier.values());
    }

    public long cost(Tier tier) {
        return Math.max(1, Math.round(tier.baseCost * priceMultiplier));
    }

    public Status status(Tier tier) {
        TierStock stock = stocks.get(tier);
        return new Status(stock.stock, capacity, stock.nextRestock);
    }

    public String restockText(Tier tier) {
        long ms = status(tier).restockAt() - System.currentTimeMillis();
        if (ms <= 0) return "soon";
        long minutes = (ms + 59_999) / 60_000;
        return minutes + "m";
    }

    public void tick() {
        if (!enabled) return;
        long now = System.currentTimeMillis();
        boolean changed = false;
        for (Tier tier : Tier.values()) {
            TierStock stock = stocks.get(tier);
            if (stock.stock < capacity && now >= stock.nextRestock) {
                stock.stock = capacity;
                stock.nextRestock = now + jitter(restockMillis);
                changed = true;
            }
        }
        if (changed) save();
    }

    public Result open(Player player, Tier tier) {
        if (tier == null) {
            player.sendMessage(ChatColor.RED + "Unknown crate.");
            return null;
        }
        if (!enabled) {
            player.sendMessage(ChatColor.RED + "The mystery shop is closed right now.");
            return null;
        }
        TierStock stock = stocks.get(tier);
        if (stock.stock <= 0) {
            player.sendMessage(ChatColor.RED + tier.label() + " is out of stock. Restocking in "
                    + restockText(tier) + ".");
            return null;
        }
        long cost = cost(tier);
        if (EconomyData.getBalance(player) < cost) {
            player.sendMessage(ChatColor.RED + "Not enough money! " + tier.label() + " costs "
                    + EconomyData.formatMoney(cost) + ".");
            return null;
        }

        UUID uuid = player.getUniqueId();
        int streak = dryStreaks.getOrDefault(uuid, 0);
        double[] input = input(tier, streak);
        double[] output = brain.forward(input);
        double multiplier = rollMultiplier();
        double luck = Math.max(-0.25, Math.min(0.40,
                (0.5 - output[0]) * 0.16 + Math.min(0.30, streak * 0.018) + (output[1] - 0.5) * 0.06));
        multiplier *= Math.max(0.5, 1.0 + luck);
        boolean jackpot = rng.nextDouble() < tier.jackpotChance
                * Math.max(0.4, 1.0 + (0.5 - output[1]) * 0.6 + streak * 0.012)
                || multiplier >= 4.0;
        if (jackpot) multiplier = Math.max(multiplier, 4.2);

        AiItemFactory.Quality quality = tier.quality();
        if (jackpot) quality = AiItemFactory.Quality.byRank(quality.rank() + 1);
        else if (quality.rank() < 4 && rng.nextDouble() < Math.max(0, output[1] - 0.35) * 0.5 + streak * 0.004) {
            quality = AiItemFactory.Quality.byRank(quality.rank() + 1);
        }

        long target = Math.max(100, Math.round(cost * multiplier * payoutScale * tier.payoutFactor()));
        List<ItemStack> items = AiItemFactory.lootBundle(rng, quality, target, jackpot);
        if (!Items.canFitAll(player, items)) {
            player.sendMessage(ChatColor.RED + "Your inventory is full! Free up space first.");
            return null;
        }
        if (!EconomyData.withdraw(player.getUniqueId(), cost)) {
            player.sendMessage(ChatColor.RED + "Not enough money! Need " + EconomyData.formatMoney(cost) + ".");
            return null;
        }

        long value = 0;
        for (ItemStack item : items) {
            Items.give(player, item, item.getAmount());
            value += AiItemFactory.value(item);
        }

        stock.stock--;
        if (stock.stock <= 0) stock.nextRestock = System.currentTimeMillis() + jitter(restockMillis);
        opens++;
        spent += cost;
        paid += value;
        biggestWin = Math.max(biggestWin, value);
        dryStreaks.put(uuid, jackpot ? 0 : streak + 1);

        double payoutTarget = NeuralNetwork.clamp01((double) value / Math.max(1, cost * 3));
        brain.train(input, new double[]{payoutTarget, jackpot ? 1.0 : 0.0, 0.5}, 0.06);
        save();

        if (jackpot && broadcastJackpots) {
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + player.getName()
                    + ChatColor.RESET + ChatColor.LIGHT_PURPLE + " hit the jackpot in " + tier.label()
                    + ": " + ChatColor.GOLD + EconomyData.formatMoney(value) + ChatColor.LIGHT_PURPLE + "!");
        }
        return new Result(tier, items, value, jackpot);
    }

    private double[] input(Tier tier, int streak) {
        TierStock stock = stocks.get(tier);
        double dry = Math.min(1.0, streak / 20.0);
        double ratio = spent <= 0 ? 0.5 : NeuralNetwork.clamp01((double) paid / spent);
        double stockRatio = capacity <= 0 ? 1 : (double) stock.stock / capacity;
        return new double[]{dry, ratio, stockRatio};
    }

    private double rollMultiplier() {
        double roll = rng.nextDouble();
        if (roll < 0.60) return 0.10 + rng.nextDouble() * 0.40;
        if (roll < 0.82) return 0.50 + rng.nextDouble() * 0.45;
        if (roll < 0.95) return 0.95 + rng.nextDouble() * 0.75;
        if (roll < 0.99) return 1.70 + rng.nextDouble() * 1.30;
        return 3.00 + rng.nextDouble() * 4.00;
    }

    public long opens() {
        return opens;
    }

    public long spent() {
        return spent;
    }

    public long paid() {
        return paid;
    }

    public long biggestWin() {
        return biggestWin;
    }

    public int dryStreak(Player player) {
        return dryStreaks.getOrDefault(player.getUniqueId(), 0);
    }

    public int trainedSamples() {
        return brain.trained();
    }

    public double returnRate() {
        return spent <= 0 ? 0 : (double) paid / spent;
    }

    private long jitter(long base) {
        return (long) (base * (0.7 + rng.nextDouble() * 0.6));
    }

    private void read() {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection brainSection = cfg.getConfigurationSection("brain");
        if (brainSection != null) {
            int[] layers = parseLayers(brainSection.getString("layers",
                    INPUTS + "," + HIDDEN + "," + OUTPUTS));
            if (layers.length >= 2 && layers[0] == INPUTS && layers[layers.length - 1] == OUTPUTS) {
                NeuralNetwork loaded = new NeuralNetwork(Long.hashCode(seed), layers);
                List<Double> weights = brainSection.getDoubleList("weights");
                if (weights.size() == loaded.toFlat().length) {
                    loaded.fromFlat(weights.stream().mapToDouble(Double::doubleValue).toArray());
                    loaded.setTrained(brainSection.getInt("trained", 0));
                    brain = loaded;
                }
            }
        }
        ConfigurationSection stockSection = cfg.getConfigurationSection("stocks");
        if (stockSection != null) {
            for (Tier tier : Tier.values()) {
                ConfigurationSection s = stockSection.getConfigurationSection(tier.id());
                if (s == null) continue;
                TierStock stock = stocks.get(tier);
                stock.stock = Math.max(0, Math.min(capacity, s.getInt("stock", capacity)));
                stock.nextRestock = s.getLong("next-restock", 0);
            }
        }
        ConfigurationSection players = cfg.getConfigurationSection("players");
        if (players != null) {
            for (String key : players.getKeys(false)) {
                try {
                    int streak = Math.max(0, players.getInt(key + ".dry-streak", 0));
                    if (streak > 0) dryStreaks.put(UUID.fromString(key), streak);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        opens = Math.max(0, cfg.getLong("stats.opens", 0));
        spent = Math.max(0, cfg.getLong("stats.spent", 0));
        paid = Math.max(0, cfg.getLong("stats.paid", 0));
        biggestWin = Math.max(0, cfg.getLong("stats.biggest-win", 0));
    }

    public void save() {
        YamlConfiguration cfg = new YamlConfiguration();
        cfg.set("brain.layers", brain.layerSpec());
        List<Double> weights = new ArrayList<>();
        for (double value : brain.toFlat()) weights.add(value);
        cfg.set("brain.weights", weights);
        cfg.set("brain.trained", brain.trained());
        for (Map.Entry<UUID, Integer> entry : dryStreaks.entrySet()) {
            if (entry.getValue() > 0) cfg.set("players." + entry.getKey() + ".dry-streak", entry.getValue());
        }
        cfg.set("stats.opens", opens);
        cfg.set("stats.spent", spent);
        cfg.set("stats.paid", paid);
        cfg.set("stats.biggest-win", biggestWin);
        for (Tier tier : Tier.values()) {
            TierStock stock = stocks.get(tier);
            cfg.set("stocks." + tier.id() + ".stock", stock.stock);
            cfg.set("stocks." + tier.id() + ".next-restock", stock.nextRestock);
        }
        File tmp = new File(file.getParentFile(), "mystery.yml.tmp");
        try {
            Files.createDirectories(file.getParentFile().toPath());
            Files.writeString(tmp.toPath(), cfg.saveToString(), StandardCharsets.UTF_8);
            Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save mystery.yml", ex);
        }
    }

    private static int[] parseLayers(String raw) {
        try {
            String[] parts = raw.split(",");
            int[] layers = new int[parts.length];
            for (int i = 0; i < parts.length; i++) layers[i] = Integer.parseInt(parts[i].trim());
            return layers;
        } catch (RuntimeException ex) {
            return new int[]{INPUTS, HIDDEN, OUTPUTS};
        }
    }
}
