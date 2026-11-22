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
import java.util.Collections;
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

    private static final String[] BUYERS = {
            "CollectorCora", "BidderBea", "GearGoblin", "VaultVera", "MerchantMilo",
            "AuctionAddict", "LootLarry", "DealDana", "BarterBen", "TraderTess",
            "HaggleHank", "CofferCleo", "StashSteve", "BidBotBella", "EmporiumEli",
            "PursePenny"
    };

    private static final int INPUTS = 6;
    private static final int HIDDEN = 9;
    private static final int OUTPUTS = 3;
    private static final int BASE_CAPACITY_TOTAL = 85;

    private static final String RELIC_EFFECT_ARMOR = "effect_armor";
    private static final String RELIC_EFFECT_SWORD = "effect_sword";
    private static final String RELIC_ENCHANT_ARMOR = "enchant_armor";
    private static final String RELIC_SUPER_TOOL = "super_tool";
    private static final String RELIC_ENCHANT_BOOK = "enchant_book";
    private static final String RELIC_KEEP_HELMET = "keep_helmet";

    private final EconomyPlugin plugin;
    private final AuctionHouse house;
    private final File file;
    private final Map<String, Stock> stocks = new LinkedHashMap<>();
    private final Map<String, Integer> relicTargets = new LinkedHashMap<>();

    private Random rng = new Random(1337);
    private NeuralNetwork brain = new NeuralNetwork(1337, INPUTS, HIDDEN, OUTPUTS);
    private boolean enabled = true;
    private int targetListings = 36;
    private int maxNewPerTick = 5;
    private long durationMinMillis = 3_600_000L;
    private long durationMaxMillis = 5L * 3_600_000L;
    private long restockMillis = 12L * 60_000L;
    private long seed = 1337;
    private double heat = 0.35;
    private boolean specialRelicsEnabled = true;
    private int maxSpecialRelics = 200;
    private int specialMinted = 0;
    private double keepInventoryHelmetChance = 0.02;
    private int effectArmorMin = 10;
    private int effectArmorMax = 30;
    private int effectSwordMin = 10;
    private int effectSwordMax = 30;
    private int enchantArmorMin = 10;
    private int enchantArmorMax = 30;
    private int superToolMin = 10;
    private int superToolMax = 30;
    private int enchantBookMin = 20;
    private int enchantBookMax = 50;
    private int keepHelmetMin = 0;
    private int keepHelmetMax = 1;
    private boolean announceRelics = false;
    private double stockScale = 1.0;
    private boolean buyersEnabled = true;
    private double buyerChance = 0.03;
    private int buyerMaxPerTick = 2;
    private long buyerMinAgeMillis = 10L * 60_000L;
    private long buyerMinValue = 1_000L;
    private long buyerMaxPrice = 1_000_000_000L;
    private double buyerMaxValueRatio = 1.75;
    private long aiBought = 0;
    private long aiSpent = 0;

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
        refreshRelicTargets();
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
        normalizeMarket();
        fill(now, targetListings);
        announceRelics = true;
        save();
        plugin.getLogger().info("AI auction market ready: " + house.syntheticCount() + " synthetic listings.");
    }

    public void reload() {
        applySettings();
        refreshRelicTargets();
        normalizeMarket();
        fill(System.currentTimeMillis());
        save();
    }

    /** Enforces the configured market limits even when the saved data predates them. */
    private void normalizeMarket() {
        int trimmed = house.trimSyntheticListings(targetListings);
        int duplicateHelmets = house.enforceSingleKeepInventoryHelmet();
        int invalidEnchants = house.removeInvalidSingleLevelEnchantListings();
        if (trimmed > 0 || duplicateHelmets > 0 || invalidEnchants > 0) {
            house.save();
            plugin.getLogger().info("Normalised auction market: removed " + trimmed
                    + " excess AI listings, " + duplicateHelmets
                    + " duplicate Keep Inventory Helmet listing(s) and " + invalidEnchants
                    + " impossible single-level enchant listing(s).");
        }
    }

    private void seedInitial(long now) {
        for (Spec spec : SPECS) {
            if (spec.id().equals("special")) continue;
            Stock stock = stocks.get(spec.id());
            if (stock.stock > 0 && house.syntheticCount(spec.id()) == 0) generate(stock, now);
        }
    }

    private void applySettings() {
        ConfigurationSection cfg = plugin.getConfig();
        enabled = cfg.getBoolean("auction.ai.enabled", true);
        targetListings = Math.max(1, Math.min(1000, cfg.getInt("auction.ai.target-listings", 1000)));
        maxNewPerTick = Math.max(1, Math.min(500, cfg.getInt("auction.ai.max-new-per-tick", 50)));
        long minHours = cfg.getLong("auction.ai.duration-hours-min", 1);
        long maxHours = cfg.getLong("auction.ai.duration-hours-max", 5);
        durationMinMillis = Math.max(1, Math.min(minHours, maxHours)) * 3_600_000L;
        durationMaxMillis = Math.max(1, Math.max(minHours, maxHours)) * 3_600_000L;
        restockMillis = Math.max(1, cfg.getLong("auction.ai.restock-minutes", 12)) * 60_000L;
        seed = cfg.getLong("auction.ai.seed", 1337L);
        specialRelicsEnabled = cfg.getBoolean("auction.ai.special-relics-enabled", true);
        maxSpecialRelics = Math.max(0, cfg.getInt("auction.ai.max-special-relics", 200));
        keepInventoryHelmetChance = Math.max(0, Math.min(1,
                cfg.getDouble("auction.ai.keep-inventory-helmet-chance", 0.02)));
        effectArmorMin = Math.max(0, cfg.getInt("auction.ai.special-counts.effect-armor-min", 10));
        effectArmorMax = Math.max(effectArmorMin, cfg.getInt("auction.ai.special-counts.effect-armor-max", 30));
        effectSwordMin = Math.max(0, cfg.getInt("auction.ai.special-counts.effect-sword-min", 10));
        effectSwordMax = Math.max(effectSwordMin, cfg.getInt("auction.ai.special-counts.effect-sword-max", 30));
        enchantArmorMin = Math.max(0, cfg.getInt("auction.ai.special-counts.enchant-armor-min", 10));
        enchantArmorMax = Math.max(enchantArmorMin, cfg.getInt("auction.ai.special-counts.enchant-armor-max", 30));
        superToolMin = Math.max(0, cfg.getInt("auction.ai.special-counts.super-tools-min", 10));
        superToolMax = Math.max(superToolMin, cfg.getInt("auction.ai.special-counts.super-tools-max", 30));
        enchantBookMin = Math.max(0, cfg.getInt("auction.ai.special-counts.enchant-books-min", 20));
        enchantBookMax = Math.max(enchantBookMin, cfg.getInt("auction.ai.special-counts.enchant-books-max", 50));
        keepHelmetMin = Math.max(0, Math.min(1, cfg.getInt("auction.ai.special-counts.keep-helmet-min", 0)));
        keepHelmetMax = Math.max(keepHelmetMin,
                Math.min(1, cfg.getInt("auction.ai.special-counts.keep-helmet-max", 1)));
        buyersEnabled = cfg.getBoolean("auction.ai.buyers.enabled", true);
        buyerChance = Math.max(0, Math.min(1,
                cfg.getDouble("auction.ai.buyers.chance-per-listing-per-tick", 0.03)));
        buyerMaxPerTick = Math.max(1, Math.min(50,
                cfg.getInt("auction.ai.buyers.max-per-tick", 2)));
        buyerMinAgeMillis = Math.max(0, cfg.getLong("auction.ai.buyers.min-age-minutes", 10)) * 60_000L;
        buyerMinValue = Math.max(0, cfg.getLong("auction.ai.buyers.min-value", 1_000L));
        buyerMaxPrice = Math.max(1, cfg.getLong("auction.ai.buyers.max-price", 1_000_000_000L));
        buyerMaxValueRatio = Math.max(1.0,
                cfg.getDouble("auction.ai.buyers.max-value-ratio", 1.75));
        stockScale = Math.max(1.0, (double) targetListings / BASE_CAPACITY_TOTAL);
        resizeStocks();
    }

    private int capacityFor(Spec spec) {
        if (spec.id().equals("special")) return Math.max(1, maxSpecialRelics);
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
        int bought = buyPlayerListings(now);
        heat = Math.max(0.08, heat * 0.985);
        if (changed || created > 0 || bought > 0) save();
    }

    /** Runs the AI buyer step immediately. Used by the market tick and integration tests. */
    public int tickBuyers() {
        int bought = buyPlayerListings(System.currentTimeMillis());
        if (bought > 0) save();
        return bought;
    }

    private int buyPlayerListings(long now) {
        if (!enabled || !buyersEnabled || buyerChance <= 0) return 0;

        List<AuctionHouse.Listing> candidates = new ArrayList<>();
        for (AuctionHouse.Listing listing : house.all()) {
            if (!listing.synthetic) candidates.add(listing);
        }
        if (candidates.isEmpty()) return 0;

        Collections.shuffle(candidates, rng);
        int bought = 0;
        for (AuctionHouse.Listing listing : candidates) {
            if (bought >= buyerMaxPerTick) break;
            if (now - listing.created < buyerMinAgeMillis) continue;

            long value = Math.max(1L, AiItemFactory.value(listing.item));
            if (value < buyerMinValue) continue;
            if (listing.price <= 0 || listing.price > buyerMaxPrice) continue;

            double ratio = (double) listing.price / value;
            if (ratio > buyerMaxValueRatio) continue;
            if (rng.nextDouble() >= buyChance(ratio, listing, now)) continue;

            String buyer = BUYERS[rng.nextInt(BUYERS.length)];
            if (house.sellToAi(listing.id, buyer)) {
                bought++;
                aiBought++;
                aiSpent += listing.price;
                heat = NeuralNetwork.clamp01(heat + 0.02);
            }
        }
        return bought;
    }

    private double buyChance(double priceToValue, AuctionHouse.Listing listing, long now) {
        double chance = buyerChance;
        if (priceToValue <= 0.60) chance *= 2.0;
        else if (priceToValue <= 0.85) chance *= 1.5;
        else if (priceToValue <= 1.10) chance *= 1.0;
        else if (priceToValue <= 1.40) chance *= 0.60;
        else chance *= 0.25;

        if (SpecialItems.isSpecial(listing.item)) chance *= 1.25;
        double ageHours = Math.max(0, now - listing.created) / 3_600_000.0;
        chance *= 1.0 + Math.min(1.5, ageHours * 0.15);
        return NeuralNetwork.clamp01(chance);
    }

    private int fill(long now) {
        return fill(now, maxNewPerTick);
    }

    private int fill(long now, int createdLimit) {
        if (!enabled) return 0;
        int active = house.syntheticCount();
        int created = 0;

        while (active < targetListings && created < createdLimit) {
            String category = nextRelicCategory();
            if (category == null) break;
            generateRelic(category, now);
            active++;
            created++;
        }

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
            if (stock.spec.id().equals("special")) continue;
            if (stock.stock <= 0) continue;
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

    private void refreshRelicTargets() {
        relicTargets.clear();
        if (!specialRelicsEnabled) return;
        relicTargets.put(RELIC_EFFECT_ARMOR, randomInRange(effectArmorMin, effectArmorMax));
        relicTargets.put(RELIC_EFFECT_SWORD, randomInRange(effectSwordMin, effectSwordMax));
        relicTargets.put(RELIC_ENCHANT_ARMOR, randomInRange(enchantArmorMin, enchantArmorMax));
        relicTargets.put(RELIC_SUPER_TOOL, randomInRange(superToolMin, superToolMax));
        relicTargets.put(RELIC_ENCHANT_BOOK, randomInRange(enchantBookMin, enchantBookMax));
        int helmet = keepHelmetMin >= keepHelmetMax
                ? keepHelmetMin
                : (rng.nextDouble() < keepInventoryHelmetChance ? keepHelmetMax : keepHelmetMin);
        relicTargets.put(RELIC_KEEP_HELMET, helmet);
    }

    private int randomInRange(int min, int max) {
        if (max <= min) return min;
        return min + rng.nextInt(max - min + 1);
    }

    private Map<String, Integer> relicCounts() {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (AuctionHouse.Listing listing : house.all()) {
            if (SpecialItems.isKeepInventoryHelmet(listing.item)) {
                counts.merge(RELIC_KEEP_HELMET, 1, Integer::sum);
            }
            if (!listing.synthetic) continue;
            String category = SpecialItems.relicCategory(listing.item);
            if (category != null && !category.equals(RELIC_KEEP_HELMET)) {
                counts.merge(category, 1, Integer::sum);
            }
        }
        return counts;
    }

    private String nextRelicCategory() {
        if (!specialRelicsEnabled) return null;
        Map<String, Integer> counts = relicCounts();
        int active = 0;
        for (int count : counts.values()) active += count;

        int minTotal = effectArmorMin + effectSwordMin + enchantArmorMin + superToolMin
                + enchantBookMin + keepHelmetMin;
        int cap = Math.max(maxSpecialRelics, minTotal);
        boolean belowMin = counts.getOrDefault(RELIC_EFFECT_ARMOR, 0) < effectArmorMin
                || counts.getOrDefault(RELIC_EFFECT_SWORD, 0) < effectSwordMin
                || counts.getOrDefault(RELIC_ENCHANT_ARMOR, 0) < enchantArmorMin
                || counts.getOrDefault(RELIC_SUPER_TOOL, 0) < superToolMin
                || counts.getOrDefault(RELIC_ENCHANT_BOOK, 0) < enchantBookMin
                || counts.getOrDefault(RELIC_KEEP_HELMET, 0) < keepHelmetMin;
        if (active >= cap && !belowMin) return null;

        for (String category : List.of(RELIC_EFFECT_ARMOR, RELIC_EFFECT_SWORD, RELIC_ENCHANT_ARMOR,
                RELIC_SUPER_TOOL, RELIC_ENCHANT_BOOK, RELIC_KEEP_HELMET)) {
            int target = relicTargets.getOrDefault(category, 0);
            if (counts.getOrDefault(category, 0) < target) return category;
        }
        return null;
    }

    private void generateRelic(String category, long now) {
        var item = switch (category) {
            case RELIC_EFFECT_ARMOR -> SpecialItems.eternalArmor(rng);
            case RELIC_EFFECT_SWORD -> SpecialItems.effectSword(rng);
            case RELIC_ENCHANT_ARMOR -> SpecialItems.highEnchantGear(rng, true);
            case RELIC_SUPER_TOOL -> SpecialItems.superTool(rng);
            case RELIC_ENCHANT_BOOK -> SpecialItems.highEnchantBook(rng);
            case RELIC_KEEP_HELMET -> SpecialItems.keepInventoryHelmet(rng);
            default -> null;
        };
        if (item == null || item.getType().isAir()) return;

        Stock special = stocks.get("special");
        double[] input = features(special, AiItemFactory.Quality.LEGENDARY, now);
        double[] output = brain.forward(input);
        double factor = 0.72 + output[0] * 1.05;
        double noise = 0.94 + rng.nextDouble() * 0.12;
        double index = special == null ? 1.0 : special.priceIndex;
        long value = AiItemFactory.value(item);
        long price = roundPrice(Math.max(50, Math.round(value * factor * index * noise)));
        price = roundPrice(Math.round(price * 1.10));
        price = Math.min(price, house.maxPrice());

        String seller = SELLERS[rng.nextInt(SELLERS.length)];
        UUID sellerId = UUID.nameUUIDFromBytes(("EconomyMarket:" + seller).getBytes(StandardCharsets.UTF_8));
        house.addSynthetic(sellerId, seller, item, price, "special", input, randomDuration());
        if (special != null) special.listed++;
        specialMinted++;
        heat = Math.min(1.0, heat + 0.02);
        if (announceRelics) {
            plugin.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD
                    + "A special relic appeared in the auction house: " + ChatColor.RESET
                    + AuctionHouse.prettyName(item));
        }
    }

    private long randomDuration() {
        if (durationMaxMillis <= durationMinMillis) return durationMinMillis;
        long span = durationMaxMillis - durationMinMillis;
        return durationMinMillis + (long) (rng.nextDouble() * span);
    }

    private void generate(Stock stock, long now) {
        AiItemFactory.Quality quality = stock.spec.quality();
        if (rng.nextDouble() < Math.max(0, stock.demand - 0.55) * 0.45) {
            quality = AiItemFactory.Quality.byRank(quality.rank() + 1);
        }
        var item = AiItemFactory.generate(rng, stock.spec.id(), quality, keepInventoryHelmetChance);
        long value = AiItemFactory.value(item);
        double[] input = features(stock, quality, now);
        double[] output = brain.forward(input);
        double factor = 0.72 + output[0] * 1.05;
        double noise = 0.94 + rng.nextDouble() * 0.12;
        long price = roundPrice(Math.max(50, Math.round(value * factor * stock.priceIndex * noise)));
        price = Math.min(price, house.maxPrice());

        String seller = SELLERS[rng.nextInt(SELLERS.length)];
        UUID sellerId = UUID.nameUUIDFromBytes(("EconomyMarket:" + seller).getBytes(StandardCharsets.UTF_8));
        house.addSynthetic(sellerId, seller, item, price, stock.spec.id(), input, randomDuration());
        stock.stock--;
        stock.listed++;
        stock.priceIndex = Math.min(1.30, stock.priceIndex + 0.004);
        heat = Math.min(1.0, heat + 0.01);
    }

    private double[] features(Stock stock, AiItemFactory.Quality quality, long now) {
        double stockRatio = stock == null || stock.capacity <= 0 ? 0.5 : (double) stock.stock / stock.capacity;
        double demand = stock == null ? 0.5 : stock.demand;
        double day = (now % 86_400_000L) / 86_400_000.0;
        double hour = Math.sin(day * Math.PI * 2) * 0.5 + 0.5;
        return new double[]{
                stockRatio,
                demand,
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
            int active = house.syntheticCount(spec.id());
            int shownStock = spec.id().equals("special")
                    ? Math.max(0, maxSpecialRelics - active)
                    : stock.stock;
            list.add(new Status(spec.id(), spec.label(), spec.icon(), shownStock, stock.capacity,
                    stock.nextRestock, stock.demand, stock.priceIndex,
                    active, stock.sold, stock.listed));
        }
        return list;
    }

    public int activeListings() {
        return house.syntheticCount();
    }

    public Map<String, Integer> activeRelicCounts() {
        return relicCounts();
    }

    public int activeSpecialCount() {
        int total = 0;
        for (int count : relicCounts().values()) total += count;
        return total;
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

    public boolean buyersEnabled() {
        return buyersEnabled;
    }

    public long totalBoughtByAi() {
        return aiBought;
    }

    public long totalSpentByAi() {
        return aiSpent;
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
        aiBought = Math.max(0, cfg.getLong("buyers.bought", 0));
        aiSpent = Math.max(0, cfg.getLong("buyers.spent", 0));
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
        cfg.set("buyers.bought", aiBought);
        cfg.set("buyers.spent", aiSpent);
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
