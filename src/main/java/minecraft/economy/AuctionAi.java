package minecraft.economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

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

public final class AuctionAi {

    private record Spec(String id, String label, Material icon, AiItemFactory.Quality quality,
                        double weight, int capacity) {
    }

    public record Status(String id, String label, Material icon, int stock, int capacity,
                         long restockAt, double demand, double priceIndex, int active,
                         long sold, long listed) {
    }

    private static final List<Spec> SPECS = List.of(
            new Spec("junk", "Scavenged Junk", Material.ROTTEN_FLESH, AiItemFactory.Quality.COMMON, 22, 14),
            new Spec("supplies", "Bulk Supplies", Material.BREAD, AiItemFactory.Quality.COMMON, 16, 12),
            new Spec("tools", "Used Tools", Material.IRON_PICKAXE, AiItemFactory.Quality.UNCOMMON, 16, 10),
            new Spec("armor", "Worn Armour", Material.IRON_CHESTPLATE, AiItemFactory.Quality.UNCOMMON, 14, 10),
            new Spec("enchanted_tools", "Enchanted Tools", Material.DIAMOND_PICKAXE, AiItemFactory.Quality.RARE, 12, 8),
            new Spec("enchanted_armor", "Enchanted Armour", Material.DIAMOND_CHESTPLATE, AiItemFactory.Quality.RARE, 11, 8),
            new Spec("weapons", "Weapons", Material.TRIDENT, AiItemFactory.Quality.EPIC, 8, 6),
            new Spec("crates", "Supply Crates", Material.SHULKER_BOX, AiItemFactory.Quality.RARE, 9, 7),
            new Spec("books", "Enchanted Books", Material.ENCHANTED_BOOK, AiItemFactory.Quality.EPIC, 7, 5),
            new Spec("treasures", "Rare Treasures", Material.TOTEM_OF_UNDYING, AiItemFactory.Quality.LEGENDARY, 5, 4),
            new Spec("special", "Special Relics", Material.NETHERITE_PICKAXE, AiItemFactory.Quality.LEGENDARY, 3, 1)
    );

    private static final String[] SELLERS = {
            "MinerMia", "CobbleKing", "PixelPyro", "EnchanterElf", "DiamondDiver",
            "RedstoneRita", "SkyBlockSam", "NetherNate", "TotemTom", "EnderEve",
            "FarmboyFinn", "LuckyLuke", "VoidWalker", "GlowSquidGuy", "ShulkerShell",
            "TradeTycoon", "AFKAndy", "SpawnerSteve", "IronGolem77", "BedrockBella",
            "CreeperCarl", "AxeCrafter", "MossyMason", "PearlPirate", "FrostFox",
            "EmberEcho", "CaveCartographer", "BeaconBen", "PotionPenny", "RailRider",
            "MobGrinderMax", "SilkTouchSue", "FortuneFred", "LapisLuna", "QuartzQueen",
            "ObsidianOllie", "BambooBri", "CopperCody", "AmethystAmy", "DeepslateDana"
    };

    private static final int INPUTS = 6;
    private static final int HIDDEN = 9;
    private static final int OUTPUTS = 3;
    private static final int BASE_CAPACITY_TOTAL = 85;

    private final EconomyPlugin plugin;
    private final AuctionHouse house;
    private final File file;
    private final Map<String, Stock> stocks = new LinkedHashMap<>();

    private Random rng = new Random(1337);
    private NeuralNetwork brain = new NeuralNetwork(1337, INPUTS, HIDDEN, OUTPUTS);
    private boolean enabled = true;
    private int targetListings = 36;
    private int maxNewPerTick = 5;
    private long durationMillis = 48L * 3_600_000L;
    private long restockMillis = 12L * 60_000L;
    private long seed = 1337;
    private double heat = 0.35;
    private boolean specialRelicsEnabled = true;
    private int maxSpecialRelics = 24;
    private int specialMinted = 0;
    private double keepInventoryHelmetChance = 0.02;
    private double stockScale = 1.0;

    private static final class Stock {
        final Spec spec;
        int stock;
        int capacity;
        long nextRestock;
        double demand = 0.45;
        double priceIndex = 1.0;
        long sold;
        long listed;

        Stock(Spec spec, int capacity) {
            this.spec = spec;
            this.capacity = capacity;
            this.stock = capacity;
        }
    }

    public AuctionAi(EconomyPlugin plugin, AuctionHouse house) {
        this.plugin = plugin;
        this.house = house;
        this.file = new File(plugin.getDataFolder(), "ai-market.yml");
    }

    public void load() {
        applySettings();
        rng = new Random(seed);
        for (Spec spec : SPECS) stocks.put(spec.id(), new Stock(spec, capacityFor(spec)));

        boolean fresh = !file.exists();
        if (!fresh) read();
        long now = System.currentTimeMillis();
        if (fresh) {
            long restock = restockMillis;
            for (int i = 0; i < SPECS.size(); i++) {
                Stock stock = stocks.get(SPECS.get(i).id());
                stock.nextRestock = now + restock + (long) (rng.nextDouble() * restock);
            }
            if (targetListings <= BASE_CAPACITY_TOTAL) {
                for (int index : new int[]{4, 9}) {
                    Stock stock = stocks.get(SPECS.get(index).id());
                    stock.stock = 1;
                    stock.demand = 0.70;
                    stock.nextRestock = now + restock / 2 + (long) (rng.nextDouble() * restock);
                }
            }
        }
        ensureStockTimers(now);
        if (fresh && enabled) seedInitial(now);
        fill(now, targetListings);
        save();
        plugin.getLogger().info("AI auction market ready: " + house.syntheticCount() + " synthetic listings.");
    }

    public void reload() {
        applySettings();
        fill(System.currentTimeMillis());
        save();
    }

    private void seedInitial(long now) {
        for (Spec spec : SPECS) {
            if (spec.id().equals("special")
                    && (!specialRelicsEnabled || specialMinted >= maxSpecialRelics)) continue;
            Stock stock = stocks.get(spec.id());
            if (stock.stock > 0 && house.syntheticCount(spec.id()) == 0) generate(stock, now);
        }
    }

    private void applySettings() {
        ConfigurationSection cfg = plugin.getConfig();
        enabled = cfg.getBoolean("auction.ai.enabled", true);
        targetListings = Math.max(1, Math.min(5000, cfg.getInt("auction.ai.target-listings", 1000)));
        maxNewPerTick = Math.max(1, Math.min(500, cfg.getInt("auction.ai.max-new-per-tick", 50)));
        durationMillis = Math.max(1, cfg.getLong("auction.ai.duration-hours", 48)) * 3_600_000L;
        restockMillis = Math.max(1, cfg.getLong("auction.ai.restock-minutes", 12)) * 60_000L;
        seed = cfg.getLong("auction.ai.seed", 1337L);
        specialRelicsEnabled = cfg.getBoolean("auction.ai.special-relics-enabled", true);
        maxSpecialRelics = Math.max(0, cfg.getInt("auction.ai.max-special-relics", 24));
        keepInventoryHelmetChance = Math.max(0, Math.min(1,
                cfg.getDouble("auction.ai.keep-inventory-helmet-chance", 0.02)));
        stockScale = Math.max(1.0, (double) targetListings / BASE_CAPACITY_TOTAL);
        resizeStocks();
    }

    private int capacityFor(Spec spec) {
        return Math.max(1, (int) Math.ceil(spec.capacity() * stockScale));
    }

    private void resizeStocks() {
        for (Stock stock : stocks.values()) {
            int capacity = capacityFor(stock.spec);
            if (capacity == stock.capacity) continue;
            if (capacity > stock.capacity) {
                stock.stock = Math.min(capacity, stock.stock + (capacity - stock.capacity));
            } else {
                stock.stock = Math.min(capacity, stock.stock);
            }
            stock.capacity = capacity;
        }
    }

    public void tick() {
        if (!enabled) return;
        long now = System.currentTimeMillis();
        boolean changed = false;
        for (Stock stock : stocks.values()) {
            if (stock.stock < stock.capacity && now >= stock.nextRestock) {
                int add = 1 + rng.nextInt(Math.max(1, stock.capacity / 3));
                stock.stock = Math.min(stock.capacity, stock.stock + add);
                stock.nextRestock = now + jitter(restockMillis);
                changed = true;
            }
        }
        int created = fill(now);
        heat = Math.max(0.08, heat * 0.985);
        if (changed || created > 0) save();
    }

    private int fill(long now) {
        return fill(now, maxNewPerTick);
    }

    private int fill(long now, int createdLimit) {
        if (!enabled) return 0;
        int active = house.syntheticCount();
        int created = 0;
        int attempts = 0;
        int limit = SPECS.size() * 8;
        while (active < targetListings && created < createdLimit && attempts < limit) {
            Stock stock = pickStock();
            if (stock == null) break;
            if (house.syntheticCount(stock.spec.id()) >= stock.capacity) {
                attempts++;
                continue;
            }
            generate(stock, now);
            active++;
            created++;
            attempts = 0;
        }
        return created;
    }

    private Stock pickStock() {
        double total = 0;
        List<Stock> candidates = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        for (Stock stock : stocks.values()) {
            if (stock.stock <= 0) continue;
            if (stock.spec.id().equals("special")
                    && (!specialRelicsEnabled || specialMinted >= maxSpecialRelics)) continue;
            double weight = stock.spec.weight() * (0.55 + stock.demand * 1.45);
            if (weight <= 0) continue;
            total += weight;
            candidates.add(stock);
            weights.add(weight);
        }
        if (candidates.isEmpty()) return null;
        double roll = rng.nextDouble() * total;
        for (int i = 0; i < candidates.size(); i++) {
            roll -= weights.get(i);
            if (roll <= 0) return candidates.get(i);
        }
        return candidates.get(candidates.size() - 1);
    }

    private void generate(Stock stock, long now) {
        AiItemFactory.Quality quality = stock.spec.quality();
        if (rng.nextDouble() < Math.max(0, stock.demand - 0.55) * 0.45) {
            quality = AiItemFactory.Quality.byRank(quality.rank() + 1);
        }
        var item = AiItemFactory.generate(rng, stock.spec.id(), quality, keepInventoryHelmetChance);
        boolean relic = SpecialItems.isSpecial(item);
        long value = AiItemFactory.value(item);
        double[] input = features(stock, quality, now);
        double[] output = brain.forward(input);
        double factor = 0.72 + output[0] * 1.05;
        double noise = 0.94 + rng.nextDouble() * 0.12;
        long price = roundPrice(Math.max(50, Math.round(value * factor * stock.priceIndex * noise)));
        if (relic) price = roundPrice(Math.round(price * 1.10));
        price = Math.min(price, house.maxPrice());

        String seller = SELLERS[rng.nextInt(SELLERS.length)];
        UUID sellerId = UUID.nameUUIDFromBytes(("EconomyMarket:" + seller).getBytes(StandardCharsets.UTF_8));
        house.addSynthetic(sellerId, seller, item, price, stock.spec.id(), input, durationMillis);
        stock.stock--;
        stock.listed++;
        stock.priceIndex = Math.min(1.30, stock.priceIndex + 0.004);
        heat = Math.min(1.0, heat + 0.01);
        if (relic) {
            specialMinted++;
            plugin.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD
                    + "A special relic appeared in the auction house: " + ChatColor.RESET
                    + AuctionHouse.prettyName(item));
        }
    }

    private double[] features(Stock stock, AiItemFactory.Quality quality, long now) {
        double stockRatio = stock.capacity <= 0 ? 0 : (double) stock.stock / stock.capacity;
        double day = (now % 86_400_000L) / 86_400_000.0;
        double hour = Math.sin(day * Math.PI * 2) * 0.5 + 0.5;
        return new double[]{
                stockRatio,
                stock.demand,
                quality.rank() / 4.0,
                hour,
                heat,
                1.0 - stockRatio
        };
    }

    public void onSold(AuctionHouse.Listing listing) {
        Stock stock = stockOf(listing);
        if (stock != null) {
            stock.sold++;
            stock.demand = NeuralNetwork.clamp01(stock.demand + 0.12);
            stock.priceIndex = Math.min(1.35, stock.priceIndex + 0.01);
        }
        heat = NeuralNetwork.clamp01(heat + 0.06);
        train(listing, true, 0.08);
        save();
    }

    public void onExpired(AuctionHouse.Listing listing) {
        Stock stock = stockOf(listing);
        if (stock != null) {
            stock.stock = Math.min(stock.capacity, stock.stock + 1);
            stock.demand = Math.max(0.05, stock.demand - 0.10);
        }
        heat = Math.max(0.05, heat - 0.015);
        train(listing, false, 0.05);
        save();
    }

    private Stock stockOf(AuctionHouse.Listing listing) {
        if (listing.archetype == null) return null;
        return stocks.get(listing.archetype.toLowerCase(Locale.ROOT));
    }

    private void train(AuctionHouse.Listing listing, boolean sold, double rate) {
        if (listing.aiInput == null || listing.aiInput.length != INPUTS) return;
        long value = Math.max(1, AiItemFactory.value(listing.item));
        double priceFactor = NeuralNetwork.clamp01(((double) listing.price / value - 0.72) / 1.05);
        Stock stock = stockOf(listing);
        double demand = stock == null ? 0.5 : stock.demand;
        brain.train(listing.aiInput, new double[]{priceFactor, sold ? 1.0 : 0.0, demand}, rate);
    }

    public List<Status> statuses() {
        List<Status> list = new ArrayList<>();
        for (Spec spec : SPECS) {
            Stock stock = stocks.get(spec.id());
            list.add(new Status(spec.id(), spec.label(), spec.icon(), stock.stock, stock.capacity,
                    stock.nextRestock, stock.demand, stock.priceIndex,
                    house.syntheticCount(spec.id()), stock.sold, stock.listed));
        }
        return list;
    }

    public int activeListings() {
        return house.syntheticCount();
    }

    public int targetListings() {
        return targetListings;
    }

    public boolean enabled() {
        return enabled;
    }

    public double heat() {
        return heat;
    }

    public int trainedSamples() {
        return brain.trained();
    }

    public boolean specialRelicsEnabled() {
        return specialRelicsEnabled;
    }

    public int maxSpecialRelics() {
        return maxSpecialRelics;
    }

    public int specialMinted() {
        return specialMinted;
    }

    public long totalSold() {
        long total = 0;
        for (Stock stock : stocks.values()) total += stock.sold;
        return total;
    }

    private void ensureStockTimers(long now) {
        for (Stock stock : stocks.values()) {
            if (stock.nextRestock <= 0) stock.nextRestock = now + jitter(restockMillis);
        }
    }

    private long jitter(long base) {
        return (long) (base * (0.65 + rng.nextDouble() * 0.8));
    }

    private static long roundPrice(long price) {
        long unit;
        if (price >= 1_000_000) unit = 10_000;
        else if (price >= 100_000) unit = 1_000;
        else if (price >= 10_000) unit = 500;
        else if (price >= 1_000) unit = 100;
        else unit = 10;
        return Math.max(unit, (price / unit) * unit);
    }

    private void read() {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection brainSection = cfg.getConfigurationSection("brain");
        if (brainSection != null) {
            String layersRaw = brainSection.getString("layers", INPUTS + "," + HIDDEN + "," + OUTPUTS);
            int[] layers = parseLayers(layersRaw);
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
            for (Spec spec : SPECS) {
                ConfigurationSection s = stockSection.getConfigurationSection(spec.id());
                if (s == null) continue;
                Stock stock = stocks.get(spec.id());
                int savedCapacity = s.contains("capacity") ? s.getInt("capacity", stock.capacity) : spec.capacity();
                stock.stock = Math.max(0, Math.min(stock.capacity, s.getInt("stock", stock.capacity)));
                if (stock.capacity > savedCapacity) {
                    stock.stock = Math.min(stock.capacity, stock.stock + (stock.capacity - savedCapacity));
                }
                stock.demand = NeuralNetwork.clamp01(s.getDouble("demand", stock.demand));
                stock.priceIndex = Math.max(0.7, Math.min(1.4, s.getDouble("price-index", stock.priceIndex)));
                stock.nextRestock = s.getLong("next-restock", 0);
                stock.sold = s.getLong("sold", 0);
                stock.listed = s.getLong("listed", 0);
            }
        }
        heat = NeuralNetwork.clamp01(cfg.getDouble("heat", heat));
        specialMinted = Math.max(0, cfg.getInt("special-minted", 0));
    }

    public void save() {
        YamlConfiguration cfg = new YamlConfiguration();
        cfg.set("brain.layers", brain.layerSpec());
        List<Double> weights = new ArrayList<>();
        for (double value : brain.toFlat()) weights.add(value);
        cfg.set("brain.weights", weights);
        cfg.set("brain.trained", brain.trained());
        cfg.set("heat", heat);
        cfg.set("special-minted", specialMinted);
        for (Stock stock : stocks.values()) {
            String base = "stocks." + stock.spec.id();
            cfg.set(base + ".stock", stock.stock);
            cfg.set(base + ".capacity", stock.capacity);
            cfg.set(base + ".demand", stock.demand);
            cfg.set(base + ".price-index", stock.priceIndex);
            cfg.set(base + ".next-restock", stock.nextRestock);
            cfg.set(base + ".sold", stock.sold);
            cfg.set(base + ".listed", stock.listed);
        }
        File tmp = new File(file.getParentFile(), "ai-market.yml.tmp");
        try {
            Files.createDirectories(file.getParentFile().toPath());
            Files.writeString(tmp.toPath(), cfg.saveToString(), StandardCharsets.UTF_8);
            Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save ai-market.yml", ex);
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
