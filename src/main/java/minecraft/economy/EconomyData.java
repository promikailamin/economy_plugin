package minecraft.economy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

/**
 * Price table + persistent player balances.
 *
 * <p>Balances are keyed by UUID so offline players can be paid / fined and so
 * name changes never lose money. Everything is saved to {@code balances.yml}.</p>
 */
public final class EconomyData {

    private static JavaPlugin plugin;
    private static long startingBalance = 1000L;
    private static long maxBalance = 1_000_000_000_000_000L;

    private static final Map<String, ShopEntry> ENTRIES = new LinkedHashMap<>();
    private static final Map<UUID, Long> BALANCES = new ConcurrentHashMap<>();
    private static final Map<UUID, String> NAMES = new ConcurrentHashMap<>();
    private static final Set<UUID> SCOREBOARD = ConcurrentHashMap.newKeySet();
    private static final AtomicBoolean DIRTY = new AtomicBoolean(false);

    private EconomyData() {
    }

    public static final class ShopEntry {
        public final String id;
        public final String name;
        public final long buy;
        public final long sell;
        public final boolean obtainable;
        public final boolean buyable;
        public final String category;
        public final String note;
        public final Material material;

        public ShopEntry(String id, String name, long buy, long sell, boolean obtainable,
                         boolean buyable, String category, String note, Material material) {
            this.id = id;
            this.name = name;
            this.buy = buy;
            this.sell = sell;
            this.obtainable = obtainable;
            this.buyable = buyable;
            this.category = category;
            this.note = note;
            this.material = material;
        }

        public String fullId() {
            return "minecraft:" + id;
        }

        public boolean sellable() {
            return sell > 0;
        }
    }

    // ------------------------------------------------------------------ lifecycle

    public static synchronized void init(JavaPlugin pl) {
        plugin = pl;
        loadConfig();
        loadPrices();
        loadBalances();
    }

    /** Re-reads config.yml and prices.tsv (balances stay in memory). */
    public static synchronized void reload() {
        plugin.reloadConfig();
        loadConfig();
        loadPrices();
    }

    private static void loadConfig() {
        startingBalance = Math.max(0, plugin.getConfig().getLong("starting-balance", 1000L));
        maxBalance = Math.max(1, plugin.getConfig().getLong("max-balance", 1_000_000_000_000_000L));
    }

    public static long startingBalance() {
        return startingBalance;
    }

    public static long maxBalance() {
        return maxBalance;
    }

    // ------------------------------------------------------------------ prices

    public static synchronized void loadPrices() {
        ENTRIES.clear();

        File file = new File(plugin.getDataFolder(), "prices.tsv");
        if (!file.exists()) {
            plugin.saveResource("prices.tsv", false);
        }

        int missingMaterial = 0, bad = 0, arbitrage = 0;
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;
                String[] parts = line.split("\t", -1);
                if (parts.length < 7) continue;
                try {
                    String id = normalizeId(parts[0]);
                    String name = parts[1].trim();
                    long buy = Long.parseLong(parts[2].trim());
                    long sell = Long.parseLong(parts[3].trim());
                    boolean obtainable = "yes".equalsIgnoreCase(parts[4].trim());
                    boolean buyable = "yes".equalsIgnoreCase(parts[5].trim());
                    String category = parts[6].trim().toLowerCase(Locale.ROOT);
                    String note = parts.length > 7 ? parts[7].trim() : "";

                    // Creative-only items are never part of the economy.
                    if (!obtainable || category.equals("creative")) continue;

                    // The dragon egg can never be bought from or sold to the server.
                    if (id.equals("dragon_egg")) {
                        buyable = false;
                        buy = 0;
                        sell = 0;
                    }
                    if (buy <= 0) buyable = false;

                    Material mat = Material.matchMaterial(id.toUpperCase(Locale.ROOT));
                    if (mat == null || mat.isAir() || !mat.isItem() || mat.isLegacy()) {
                        missingMaterial++; // id exists in the price list but not in this server version
                        continue;
                    }
                    if (buyable && sell > buy) arbitrage++;

                    ENTRIES.put(id, new ShopEntry(id, name, buy, sell, obtainable, buyable, category, note, mat));
                } catch (NumberFormatException ex) {
                    bad++;
                }
            }
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Failed to read prices.tsv", ex);
        }

        plugin.getLogger().info("Loaded " + ENTRIES.size() + " price entries (" + countBuyable() + " buyable)."
                + (missingMaterial > 0 ? " Skipped " + missingMaterial + " ids unknown to this server version." : "")
                + (bad > 0 ? " Skipped " + bad + " malformed lines." : ""));
        if (arbitrage > 0) {
            plugin.getLogger().warning(arbitrage + " items have a sell price higher than their buy price "
                    + "(players can profit by buying and re-selling). Check prices.tsv.");
        }
    }

    private static int countBuyable() {
        int c = 0;
        for (ShopEntry e : ENTRIES.values()) if (e.buyable) c++;
        return c;
    }

    public static synchronized ShopEntry get(String input) {
        return ENTRIES.get(normalizeId(input));
    }

    public static synchronized Collection<ShopEntry> all() {
        return Collections.unmodifiableCollection(new ArrayList<>(ENTRIES.values()));
    }

    public static String normalizeId(String input) {
        if (input == null) return "";
        String value = input.trim().toLowerCase(Locale.ROOT);
        if (value.startsWith("minecraft:")) value = value.substring("minecraft:".length());
        return value;
    }

    /** Finds an item by id, display name, or unique partial match among buyable items. */
    public static synchronized ShopEntry fuzzyMatch(String query) throws AmbiguousMatchException {
        String q = query.trim().toLowerCase(Locale.ROOT).replace(' ', '_');
        ShopEntry exact = ENTRIES.get(normalizeId(q));
        if (exact != null) return exact;

        List<ShopEntry> matches = searchBuyable(query);
        if (matches.isEmpty()) return null;
        if (matches.size() == 1) return matches.get(0);
        throw new AmbiguousMatchException(matches);
    }

    public static synchronized List<ShopEntry> searchBuyable(String query) {
        String q = query.trim().toLowerCase(Locale.ROOT);
        String qId = q.replace(' ', '_');
        List<ShopEntry> matches = new ArrayList<>();
        for (ShopEntry e : ENTRIES.values()) {
            if (!e.buyable) continue;
            if (e.id.contains(qId) || e.name.toLowerCase(Locale.ROOT).contains(q)) matches.add(e);
        }
        matches.sort(Comparator.comparing(e -> e.name.toLowerCase(Locale.ROOT)));
        return matches;
    }

    public static class AmbiguousMatchException extends Exception {
        private final List<ShopEntry> matches;

        public AmbiguousMatchException(List<ShopEntry> matches) {
            super("Ambiguous item match");
            this.matches = matches;
        }

        public List<ShopEntry> getMatches() {
            return matches;
        }
    }

    // ------------------------------------------------------------------ formatting

    public static String formatMoney(long amount) {
        return Money.format(amount);
    }

    public static long parseAmount(String input, boolean allowZero) {
        return Money.parse(input, maxBalance, allowZero);
    }

    // ------------------------------------------------------------------ balances

    private static long clamp(long v) {
        return Math.max(-maxBalance, Math.min(maxBalance, v));
    }

    public static long getBalance(UUID id) {
        return BALANCES.getOrDefault(id, startingBalance);
    }

    public static long getBalance(Player player) {
        return getBalance(player.getUniqueId());
    }

    public static synchronized void setBalance(UUID id, long amount) {
        BALANCES.put(id, clamp(amount));
        changed(id);
    }

    public static void setBalance(Player player, long amount) {
        setBalance(player.getUniqueId(), amount);
    }

    public static synchronized void deposit(UUID id, long amount) {
        if (amount <= 0) return;
        long cur = getBalance(id);
        long next = cur + amount;
        if (next < cur) next = maxBalance; // overflow
        BALANCES.put(id, clamp(next));
        changed(id);
    }

    public static void add(Player player, long amount) {
        deposit(player.getUniqueId(), amount);
    }

    /** Takes money only if the player can afford it. */
    public static synchronized boolean withdraw(UUID id, long amount) {
        if (amount <= 0) return true;
        long cur = getBalance(id);
        if (cur < amount) return false;
        BALANCES.put(id, cur - amount);
        changed(id);
        return true;
    }

    public static boolean take(Player player, long amount) {
        return withdraw(player.getUniqueId(), amount);
    }

    /** Takes money even if it pushes the balance below zero (fines). */
    public static synchronized void forceWithdraw(UUID id, long amount) {
        if (amount <= 0) return;
        long next = getBalance(id) - amount;
        BALANCES.put(id, clamp(next));
        changed(id);
    }

    public static void ensureAccount(Player player) {
        String old = NAMES.put(player.getUniqueId(), player.getName());
        if (!player.getName().equals(old)) DIRTY.set(true);
        if (BALANCES.putIfAbsent(player.getUniqueId(), startingBalance) == null) {
            DIRTY.set(true);
        }
    }

    private static void changed(UUID id) {
        DIRTY.set(true);
        if (Bukkit.isPrimaryThread()) {
            EconomyScoreboard.get().update(id);
        }
    }

    // ------------------------------------------------------------------ names / lookup

    public static String nameOf(UUID id) {
        String n = NAMES.get(id);
        return n != null ? n : id.toString().substring(0, 8);
    }

    public static UUID findUuid(String name) {
        Player online = Bukkit.getPlayerExact(name);
        if (online != null) return online.getUniqueId();
        for (Map.Entry<UUID, String> e : NAMES.entrySet()) {
            if (e.getValue().equalsIgnoreCase(name)) return e.getKey();
        }
        return null;
    }

    public static List<String> knownNames() {
        return new ArrayList<>(NAMES.values());
    }

    public static List<Map.Entry<UUID, Long>> top(int n) {
        List<Map.Entry<UUID, Long>> list = new ArrayList<>();
        for (UUID id : NAMES.keySet()) {
            list.add(Map.entry(id, getBalance(id)));
        }
        list.sort((a, b) -> Long.compare(b.getValue(), a.getValue()));
        return list.subList(0, Math.min(n, list.size()));
    }

    // ------------------------------------------------------------------ scoreboard preference

    public static boolean scoreboardEnabled(UUID id) {
        return SCOREBOARD.contains(id);
    }

    public static void setScoreboardEnabled(UUID id, boolean on) {
        if (on) SCOREBOARD.add(id);
        else SCOREBOARD.remove(id);
        DIRTY.set(true);
    }

    // ------------------------------------------------------------------ persistence

    private static File balancesFile() {
        return new File(plugin.getDataFolder(), "balances.yml");
    }

    private static void loadBalances() {
        BALANCES.clear();
        NAMES.clear();
        SCOREBOARD.clear();
        File f = balancesFile();
        if (!f.exists()) return;

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        ConfigurationSection players = cfg.getConfigurationSection("players");
        if (players == null) return;
        for (String key : players.getKeys(false)) {
            try {
                UUID id = UUID.fromString(key);
                ConfigurationSection s = players.getConfigurationSection(key);
                if (s == null) continue;
                BALANCES.put(id, s.getLong("balance", startingBalance));
                String name = s.getString("name");
                if (name != null) NAMES.put(id, name);
                if (s.getBoolean("scoreboard", false)) SCOREBOARD.add(id);
            } catch (IllegalArgumentException ignored) {
                // skip malformed uuid
            }
        }
        plugin.getLogger().info("Loaded " + BALANCES.size() + " player accounts.");
    }

    private static String snapshot() {
        YamlConfiguration cfg = new YamlConfiguration();
        for (Map.Entry<UUID, Long> e : BALANCES.entrySet()) {
            String base = "players." + e.getKey();
            cfg.set(base + ".balance", e.getValue());
            String name = NAMES.get(e.getKey());
            if (name != null) cfg.set(base + ".name", name);
            if (SCOREBOARD.contains(e.getKey())) cfg.set(base + ".scoreboard", true);
        }
        return cfg.saveToString();
    }

    private static void write(String data) {
        File target = balancesFile();
        File tmp = new File(target.getParentFile(), "balances.yml.tmp");
        try {
            Files.createDirectories(target.getParentFile().toPath());
            Files.writeString(tmp.toPath(), data, StandardCharsets.UTF_8);
            try {
                Files.move(tmp.toPath(), target.toPath(),
                        StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException atomicFail) {
                Files.move(tmp.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save balances.yml", ex);
            DIRTY.set(true); // try again next time
        }
    }

    /** Blocking save (used on shutdown and after auction trades). */
    public static synchronized void save() {
        if (!DIRTY.getAndSet(false)) return;
        write(snapshot());
    }

    /** Takes the snapshot on the calling (main) thread and writes the file asynchronously. */
    public static void saveAsync() {
        if (!DIRTY.getAndSet(false)) return;
        final String data;
        synchronized (EconomyData.class) {
            data = snapshot();
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> write(data));
    }
}
