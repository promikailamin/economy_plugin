package minecraft.economy;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class SpecialItems {

    public enum Kind {
        AREA_MINER, VEIN_MINER, TREE_MINER, EFFECT_SWORD, HIGH_ENCHANT_BOOK, HIGH_ENCHANT_GEAR,
        ETERNAL_ARMOR, KEEP_INVENTORY_HELMET
    }

    private enum ArmorSlot {
        HELMET, CHESTPLATE, LEGGINGS, BOOTS
    }

    private static final String FLAG = "special_item";
    private static final String ID = "special_id";
    private static final String KIND = "special_kind";
    private static final String VALUE = "special_value";
    private static final String AREA = "special_area";
    private static final String EFFECT = "special_effect";
    private static final String EFFECT_LEVEL = "special_effect_level";
    private static final String SERIAL = "special_serial";

    private static final Map<Enchantment, Integer> CONFLICTS = Map.ofEntries(
            Map.entry(Enchantment.PROTECTION, 1),
            Map.entry(Enchantment.FIRE_PROTECTION, 1),
            Map.entry(Enchantment.BLAST_PROTECTION, 1),
            Map.entry(Enchantment.PROJECTILE_PROTECTION, 1),
            Map.entry(Enchantment.SHARPNESS, 2),
            Map.entry(Enchantment.SMITE, 2),
            Map.entry(Enchantment.BANE_OF_ARTHROPODS, 2),
            Map.entry(Enchantment.SILK_TOUCH, 3),
            Map.entry(Enchantment.FORTUNE, 3)
    );

    private static final Enchantment[] TOOL_ENCHANTS = {
            Enchantment.EFFICIENCY, Enchantment.FORTUNE, Enchantment.SILK_TOUCH,
            Enchantment.UNBREAKING, Enchantment.MENDING
    };
    private static final Enchantment[] ARMOR_ENCHANTS = {
            Enchantment.PROTECTION, Enchantment.FIRE_PROTECTION, Enchantment.BLAST_PROTECTION,
            Enchantment.PROJECTILE_PROTECTION, Enchantment.THORNS, Enchantment.UNBREAKING,
            Enchantment.MENDING, Enchantment.FEATHER_FALLING, Enchantment.RESPIRATION,
            Enchantment.SWIFT_SNEAK, Enchantment.SOUL_SPEED
    };
    private static final Enchantment[] BOOK_ENCHANTS = {
            Enchantment.SHARPNESS, Enchantment.PROTECTION, Enchantment.EFFICIENCY,
            Enchantment.FORTUNE, Enchantment.UNBREAKING, Enchantment.LOOTING,
            Enchantment.POWER, Enchantment.FEATHER_FALLING, Enchantment.THORNS,
            Enchantment.RESPIRATION, Enchantment.SOUL_SPEED, Enchantment.SWIFT_SNEAK,
            Enchantment.DENSITY, Enchantment.BREACH, Enchantment.WIND_BURST
    };

    private static final Set<String> BAD_EFFECT_NAMES = Set.of(
            "bad_omen", "blindness", "darkness", "glowing", "hunger", "infested",
            "levitation", "mining_fatigue", "nausea", "oozing", "poison", "raid_omen",
            "slowness", "trial_omen", "unluck", "weakness", "weaving", "wither",
            "wind_charged");

    private static final List<PotionEffectType> ALL_EFFECTS = new ArrayList<>();
    private static final List<PotionEffectType> GOOD_EFFECTS = new ArrayList<>();
    private static final List<PotionEffectType> BAD_EFFECTS = new ArrayList<>();

    static {
        for (PotionEffectType type : PotionEffectType.values()) {
            if (type == null || type.isInstant()) continue;
            String name = effectId(type);
            if (name.equals("empty")) continue;
            ALL_EFFECTS.add(type);
            if (BAD_EFFECT_NAMES.contains(name)) BAD_EFFECTS.add(type);
            else GOOD_EFFECTS.add(type);
        }
        if (GOOD_EFFECTS.isEmpty()) GOOD_EFFECTS.addAll(ALL_EFFECTS);
        if (BAD_EFFECTS.isEmpty()) BAD_EFFECTS.addAll(ALL_EFFECTS);
    }

    private static String effectId(PotionEffectType type) {
        String name = type.getName().toLowerCase(Locale.ROOT);
        int colon = name.indexOf(':');
        return colon >= 0 ? name.substring(colon + 1) : name;
    }

    private SpecialItems() {
    }

    private static NamespacedKey key(String name) {
        return new NamespacedKey(EconomyPlugin.get(), name);
    }

    public static boolean isSpecial(ItemStack item) {
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) return false;
        Byte flag = item.getItemMeta().getPersistentDataContainer().get(key(FLAG), PersistentDataType.BYTE);
        return flag != null && flag == 1;
    }

    public static Kind kind(ItemStack item) {
        if (!isSpecial(item)) return null;
        String raw = item.getItemMeta().getPersistentDataContainer().get(key(KIND), PersistentDataType.STRING);
        if (raw == null) return null;
        try {
            return Kind.valueOf(raw);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static String id(ItemStack item) {
        if (!isSpecial(item)) return null;
        return item.getItemMeta().getPersistentDataContainer().get(key(ID), PersistentDataType.STRING);
    }

    public static long value(ItemStack item) {
        if (!isSpecial(item)) return 0;
        Long value = item.getItemMeta().getPersistentDataContainer().get(key(VALUE), PersistentDataType.LONG);
        return value == null ? 0 : value;
    }

    public static int areaSize(ItemStack item) {
        if (!isSpecial(item)) return 0;
        Integer size = item.getItemMeta().getPersistentDataContainer().get(key(AREA), PersistentDataType.INTEGER);
        return size == null ? 0 : size;
    }

    public static int serial(ItemStack item) {
        if (!isSpecial(item)) return 0;
        Integer serial = item.getItemMeta().getPersistentDataContainer().get(key(SERIAL), PersistentDataType.INTEGER);
        return serial == null ? 0 : serial;
    }

    /** The potion effect bound to an effect sword or eternal armour piece, or {@code null}. */
    public static PotionEffectType effect(ItemStack item) {
        if (!isSpecial(item)) return null;
        String raw = item.getItemMeta().getPersistentDataContainer().get(key(EFFECT), PersistentDataType.STRING);
        return effectByName(raw);
    }

    /** The bound potion effect level (1-4), or {@code 0} when the item has no effect. */
    public static int effectLevel(ItemStack item) {
        if (!isSpecial(item)) return 0;
        Integer level = item.getItemMeta().getPersistentDataContainer().get(key(EFFECT_LEVEL), PersistentDataType.INTEGER);
        return level == null ? 0 : level;
    }

    public static boolean isKeepInventoryHelmet(ItemStack item) {
        return kind(item) == Kind.KEEP_INVENTORY_HELMET;
    }

    /**
     * True when a relic has a single-level enchantment (Mending, Infinity, Silk Touch, ...)
     * forced above its vanilla maximum. Those levels make no sense and should never be
     * generated or remain listed in the auction house.
     */
    public static boolean hasInvalidSingleLevelEnchant(ItemStack item) {
        Kind kind = kind(item);
        if (kind != Kind.HIGH_ENCHANT_BOOK && kind != Kind.HIGH_ENCHANT_GEAR) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        if (meta instanceof EnchantmentStorageMeta storage) {
            for (Map.Entry<Enchantment, Integer> entry : storage.getStoredEnchants().entrySet()) {
                if (entry.getKey().getMaxLevel() <= 1 && entry.getValue() > entry.getKey().getMaxLevel()) {
                    return true;
                }
            }
        }
        for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
            if (entry.getKey().getMaxLevel() <= 1 && entry.getValue() > entry.getKey().getMaxLevel()) {
                return true;
            }
        }
        return false;
    }

    /**
     * The AI market bucket a relic belongs to, or {@code null} for ordinary items.
     */
    public static String relicCategory(ItemStack item) {
        Kind kind = kind(item);
        if (kind == null) return null;
        return switch (kind) {
            case ETERNAL_ARMOR -> "effect_armor";
            case EFFECT_SWORD -> "effect_sword";
            case HIGH_ENCHANT_GEAR -> isArmor(item.getType()) ? "enchant_armor" : "super_tool";
            case HIGH_ENCHANT_BOOK -> "enchant_book";
            case AREA_MINER, VEIN_MINER, TREE_MINER -> "super_tool";
            case KEEP_INVENTORY_HELMET -> "keep_helmet";
        };
    }

    private static boolean isArmor(Material material) {
        String name = material.name();
        return name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS")
                || name.equals("TURTLE_HELMET");
    }

    /** A random ability tool: area miner, vein miner or tree feller. */
    public static ItemStack superTool(Random rng) {
        return switch (rng.nextInt(5)) {
            case 0 -> areaMiner(rng, 3, false);
            case 1 -> areaMiner(rng, 4, false);
            case 2 -> areaMiner(rng, 5, false);
            case 3 -> veinMiner(rng);
            default -> treeMiner(rng);
        };
    }

    public static ItemStack random(Random rng) {
        return random(rng, 0.02);
    }

    public static ItemStack random(Random rng, double keepInventoryHelmetChance) {
        if (keepInventoryHelmetChance > 0 && rng.nextDouble() < keepInventoryHelmetChance) {
            return keepInventoryHelmet(rng);
        }
        int roll = rng.nextInt(100);
        if (roll < 17) return areaMiner(rng, 3, false);
        if (roll < 28) return areaMiner(rng, 3, true);
        if (roll < 39) return areaMiner(rng, 4, false);
        if (roll < 46) return areaMiner(rng, 4, true);
        if (roll < 54) return areaMiner(rng, 5, false);
        if (roll < 59) return areaMiner(rng, 5, true);
        if (roll < 68) return veinMiner(rng);
        if (roll < 76) return treeMiner(rng);
        if (roll < 82) return highEnchantBook(rng);
        if (roll < 88) return highEnchantGear(rng);
        if (roll < 94) return effectSword(rng);
        return eternalArmor(rng);
    }

    public static ItemStack areaMiner(Random rng, int size, boolean shovel) {
        size = Math.max(3, Math.min(5, size));
        Material material = shovel ? Material.NETHERITE_SHOVEL : Material.NETHERITE_PICKAXE;
        String label = shovel ? "Excavator" : "Prospector";
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.addEnchant(Enchantment.EFFICIENCY, 5 + rng.nextInt(6), true);
        meta.addEnchant(Enchantment.UNBREAKING, 5 + rng.nextInt(6), true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        if (!shovel && rng.nextBoolean()) {
            meta.addEnchant(Enchantment.FORTUNE, 3 + rng.nextInt(4), true);
        } else if (!shovel) {
            meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        }

        long value = switch (size) {
            case 3 -> 2_500_000L;
            case 4 -> 6_000_000L;
            default -> 15_000_000L;
        };
        if (shovel) value = Math.round(value * 0.8);

        List<String> lore = new ArrayList<>();
        lore.add("§7Ability: §eMines a §f" + size + "×" + size + "×" + size + "§e cube");
        lore.add("§7Centred on the block you break.");
        lore.add("§7Sneak to disable area mining.");
        lore.add("§8-------------------------");
        lore.add("§8Legendary relic • one of a limited supply");
        lore.add("§5Relic value: §d" + Money.format(value));

        mark(meta, Kind.AREA_MINER, "area_" + size + (shovel ? "_shovel" : "_pickaxe"), value, rng);
        meta.getPersistentDataContainer().set(key(AREA), PersistentDataType.INTEGER, size);
        meta.setDisplayName("§6§l" + size + "×" + size + " " + label);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack veinMiner(Random rng) {
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.addEnchant(Enchantment.EFFICIENCY, 6 + rng.nextInt(6), true);
        meta.addEnchant(Enchantment.UNBREAKING, 6 + rng.nextInt(5), true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addEnchant(rng.nextBoolean() ? Enchantment.FORTUNE : Enchantment.SILK_TOUCH,
                rng.nextBoolean() ? 3 + rng.nextInt(4) : 1, true);

        List<String> lore = new ArrayList<>();
        lore.add("§7Ability: §aBreaks an entire ore vein");
        lore.add("§7Finds up to §f64§7 connected blocks.");
        lore.add("§7Sneak to disable vein mining.");
        lore.add("§8-------------------------");
        lore.add("§8Legendary relic • one of a limited supply");
        lore.add("§5Relic value: §d" + Money.format(8_000_000L));

        mark(meta, Kind.VEIN_MINER, "vein_pickaxe", 8_000_000L, rng);
        meta.setDisplayName("§a§lVein Seeker");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack treeMiner(Random rng) {
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.addEnchant(Enchantment.EFFICIENCY, 6 + rng.nextInt(6), true);
        meta.addEnchant(Enchantment.UNBREAKING, 6 + rng.nextInt(5), true);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        List<String> lore = new ArrayList<>();
        lore.add("§7Ability: §2Fells an entire tree");
        lore.add("§7Finds up to §f128§7 connected logs.");
        lore.add("§7Sneak to disable tree mining.");
        lore.add("§8-------------------------");
        lore.add("§8Legendary relic • one of a limited supply");
        lore.add("§5Relic value: §d" + Money.format(5_000_000L));

        mark(meta, Kind.TREE_MINER, "tree_axe", 5_000_000L, rng);
        meta.setDisplayName("§2§lLumberjack's Relic");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack effectSword(Random rng) {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        PotionEffectType effect = ALL_EFFECTS.get(rng.nextInt(ALL_EFFECTS.size()));
        int level = 1 + rng.nextInt(4);

        meta.addEnchant(Enchantment.SHARPNESS, 6 + rng.nextInt(6), true);
        meta.addEnchant(Enchantment.UNBREAKING, 6 + rng.nextInt(5), true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addEnchant(Enchantment.LOOTING, 3 + rng.nextInt(3), true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);

        long value = 12_000_000L + level * 4_000_000L + rng.nextInt(6_000_000);
        List<String> lore = new ArrayList<>();
        lore.add("§7On hit: " + effectLabel(effect) + " §f" + level);
        lore.add("§7Strikes inflict the sword's bound effect.");
        lore.add("§7The effect is bound to the blade.");
        lore.add("§8-------------------------");
        lore.add("§8Legendary relic • one of a limited supply");
        lore.add("§5Relic value: §d" + Money.format(value));

        String effectKey = effectId(effect);
        mark(meta, Kind.EFFECT_SWORD, "sword_" + effectKey + "_" + level, value, rng);
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(key(EFFECT), PersistentDataType.STRING, effectKey);
        pdc.set(key(EFFECT_LEVEL), PersistentDataType.INTEGER, level);
        meta.setDisplayName("§c§lBlade of " + effectLabel(effect));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack highEnchantBook(Random rng) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta raw = item.getItemMeta();
        if (!(raw instanceof EnchantmentStorageMeta meta)) return item;

        Enchantment enchantment;
        do {
            enchantment = BOOK_ENCHANTS[rng.nextInt(BOOK_ENCHANTS.length)];
        } while (enchantment.getMaxLevel() <= 1);
        int level = 10 + rng.nextInt(6);
        meta.addStoredEnchant(enchantment, level, true);

        long value = 1_500_000L + level * 250_000L;
        List<String> lore = new ArrayList<>();
        lore.add("§7Stored enchantment: §d" + pretty(enchantment.getName()) + " " + level);
        lore.add("§7This level is far beyond the vanilla cap.");
        lore.add("§8-------------------------");
        lore.add("§8Legendary relic • one of a limited supply");
        lore.add("§5Relic value: §d" + Money.format(value));

        mark(meta, Kind.HIGH_ENCHANT_BOOK, "book_" + enchantment.getName() + "_" + level, value, rng);
        meta.setDisplayName("§5§lAncient Tome of " + pretty(enchantment.getName()));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack highEnchantGear(Random rng) {
        return highEnchantGear(rng, rng.nextBoolean());
    }

    public static ItemStack highEnchantGear(Random rng, boolean armor) {
        Material[] pool = armor
                ? new Material[]{Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE,
                        Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS}
                : new Material[]{Material.NETHERITE_SWORD, Material.NETHERITE_PICKAXE,
                        Material.NETHERITE_AXE, Material.NETHERITE_SHOVEL};
        Material material = pool[rng.nextInt(pool.length)];
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        applyHighEnchants(rng, meta, armor);
        long value = 4_000_000L + rng.nextInt(8_000_000);

        List<String> lore = new ArrayList<>();
        lore.add("§7Enchantments: §d10-15 levels §7(single-level stay 1)");
        lore.add("§7Forged with power no anvil can create.");
        lore.add("§8-------------------------");
        lore.add("§8Legendary relic • one of a limited supply");
        lore.add("§5Relic value: §d" + Money.format(value));

        String id = "gear_" + material.name().toLowerCase(Locale.ROOT) + "_" + value;
        mark(meta, Kind.HIGH_ENCHANT_GEAR, id, value, rng);
        meta.setDisplayName("§5§lGodforged " + pretty(material.name()));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack keepInventoryHelmet(Random rng) {
        ItemStack item = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.addEnchant(Enchantment.PROTECTION, 4, true);
        meta.addEnchant(Enchantment.UNBREAKING, 5, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addEnchant(Enchantment.RESPIRATION, 3, true);
        meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);

        long value = 500_000_000L + rng.nextInt(250_000_001);
        List<String> lore = new ArrayList<>();
        lore.add("§7Passive: §aKeeps your entire inventory on death");
        lore.add("§7Experience is still lost like a normal death.");
        lore.add("§7Loses §c25%§7 durability when you respawn.");
        lore.add("§7Shatters if less than §c25%§7 durability remains.");
        lore.add("§8-------------------------");
        lore.add("§8Mythic relic • extremely rare");
        lore.add("§5Relic value: §d" + Money.format(value));

        mark(meta, Kind.KEEP_INVENTORY_HELMET, "keep_inventory_helmet", value, rng);
        meta.setDisplayName("§d§lHelm of Preservation");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack eternalArmor(Random rng) {
        ArmorSlot slot = ArmorSlot.values()[rng.nextInt(ArmorSlot.values().length)];
        Material material = switch (slot) {
            case HELMET -> Material.NETHERITE_HELMET;
            case CHESTPLATE -> Material.NETHERITE_CHESTPLATE;
            case LEGGINGS -> Material.NETHERITE_LEGGINGS;
            case BOOTS -> Material.NETHERITE_BOOTS;
        };
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        boolean good = rng.nextDouble() < 0.72;
        List<PotionEffectType> pool = effectPool(good);
        PotionEffectType effect = pool.get(rng.nextInt(pool.size()));
        int level = 1 + rng.nextInt(4);

        meta.setUnbreakable(true);
        if (good) meta.addEnchant(Enchantment.UNBREAKING, 10, true);
        long value = 10_000_000L + level * 3_000_000L + rng.nextInt(4_000_000);

        List<String> lore = new ArrayList<>();
        lore.add("§7Eternal: §fnever loses durability");
        lore.add("§7While worn: " + effectLabel(effect) + " §f" + level);
        lore.add("§7Only one effect can exist on a piece.");
        lore.add("§7The effect can be any potion effect.");
        lore.add("§8-------------------------");
        lore.add("§8Legendary relic • one of a limited supply");
        lore.add("§5Relic value: §d" + Money.format(value));

        String effectKey = effectId(effect);
        String id = "eternal_" + material.name().toLowerCase(Locale.ROOT) + "_"
                + effectKey + "_" + level;
        mark(meta, Kind.ETERNAL_ARMOR, id, value, rng);
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(key(EFFECT), PersistentDataType.STRING, effectKey);
        pdc.set(key(EFFECT_LEVEL), PersistentDataType.INTEGER, level);
        meta.setDisplayName((good ? "§b§lEternal " : "§4§lCursed ")
                + pretty(material.name()) + " §7of " + effectLabel(effect));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static void tickArmor(Player player) {
        for (ItemStack piece : player.getInventory().getArmorContents()) {
            if (kind(piece) != Kind.ETERNAL_ARMOR) continue;
            PotionEffectType type = effect(piece);
            int level = effectLevel(piece);
            if (type == null || level <= 0) continue;

            int amplifier = Math.max(0, Math.min(3, level - 1));
            PotionEffect current = player.getPotionEffect(type);
            if (current != null && current.getAmplifier() >= amplifier && current.getDuration() > 60) continue;
            player.addPotionEffect(new PotionEffect(type, 120, amplifier, true, false, true), true);
        }
    }

    private static PotionEffectType effectByName(String raw) {
        if (raw == null) return null;
        PotionEffectType direct = PotionEffectType.getByName(raw);
        if (direct != null) return direct;
        for (PotionEffectType type : PotionEffectType.values()) {
            if (type.getName().equalsIgnoreCase(raw) || effectId(type).equalsIgnoreCase(raw)) return type;
        }
        return null;
    }

    private static List<PotionEffectType> effectPool(boolean good) {
        return good ? GOOD_EFFECTS : BAD_EFFECTS;
    }

    private static void applyHighEnchants(Random rng, ItemMeta meta, boolean armor) {
        Enchantment[] pool = armor ? ARMOR_ENCHANTS : TOOL_ENCHANTS;
        int count = 2 + rng.nextInt(3);
        Set<Integer> groups = new HashSet<>();
        for (int i = 0; i < count * 6 && meta.getEnchants().size() < count; i++) {
            Enchantment enchantment = pool[rng.nextInt(pool.length)];
            int group = CONFLICTS.getOrDefault(enchantment, 0);
            if (group != 0 && !groups.add(group)) continue;
            int level = enchantment.getMaxLevel() <= 1 ? 1 : 10 + rng.nextInt(6);
            meta.addEnchant(enchantment, level, true);
        }
    }

    private static void mark(ItemMeta meta, Kind kind, String id, long value, Random rng) {
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(key(FLAG), PersistentDataType.BYTE, (byte) 1);
        pdc.set(key(KIND), PersistentDataType.STRING, kind.name());
        pdc.set(key(ID), PersistentDataType.STRING, id);
        pdc.set(key(VALUE), PersistentDataType.LONG, value);
        pdc.set(key(SERIAL), PersistentDataType.INTEGER, 1 + rng.nextInt(999_999));
        meta.setEnchantmentGlintOverride(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ARMOR_TRIM);
    }

    private static String effectLabel(PotionEffectType type) {
        String label = pretty(effectId(type));
        return (BAD_EFFECTS.contains(type) ? "§c" : "§a") + label;
    }

    private static String pretty(String id) {
        StringBuilder sb = new StringBuilder();
        for (String part : id.toLowerCase(Locale.ROOT).split("_")) {
            if (part.isEmpty()) continue;
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return sb.toString();
    }
}
