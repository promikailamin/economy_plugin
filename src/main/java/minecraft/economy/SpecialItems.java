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
        AREA_MINER, VEIN_MINER, TREE_MINER, HIGH_ENCHANT_BOOK, HIGH_ENCHANT_GEAR, ETERNAL_ARMOR,
        KEEP_INVENTORY_HELMET
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
            Enchantment.FORTUNE, Enchantment.SILK_TOUCH, Enchantment.UNBREAKING,
            Enchantment.MENDING, Enchantment.LOOTING, Enchantment.POWER,
            Enchantment.INFINITY, Enchantment.FEATHER_FALLING, Enchantment.THORNS,
            Enchantment.RESPIRATION, Enchantment.SOUL_SPEED, Enchantment.SWIFT_SNEAK,
            Enchantment.DENSITY, Enchantment.BREACH, Enchantment.WIND_BURST
    };

    private static final List<PotionEffectType> HELMET_GOOD = List.of(
            PotionEffectType.NIGHT_VISION, PotionEffectType.WATER_BREATHING,
            PotionEffectType.CONDUIT_POWER, PotionEffectType.HASTE);
    private static final List<PotionEffectType> HELMET_BAD = List.of(
            PotionEffectType.MINING_FATIGUE, PotionEffectType.BLINDNESS,
            PotionEffectType.DARKNESS, PotionEffectType.NAUSEA);
    private static final List<PotionEffectType> CHEST_GOOD = List.of(
            PotionEffectType.RESISTANCE, PotionEffectType.REGENERATION,
            PotionEffectType.ABSORPTION, PotionEffectType.HEALTH_BOOST,
            PotionEffectType.FIRE_RESISTANCE);
    private static final List<PotionEffectType> CHEST_BAD = List.of(
            PotionEffectType.WEAKNESS, PotionEffectType.POISON,
            PotionEffectType.HUNGER, PotionEffectType.UNLUCK);
    private static final List<PotionEffectType> LEGGING_GOOD = List.of(
            PotionEffectType.RESISTANCE, PotionEffectType.REGENERATION,
            PotionEffectType.ABSORPTION, PotionEffectType.LUCK);
    private static final List<PotionEffectType> LEGGING_BAD = List.of(
            PotionEffectType.SLOWNESS, PotionEffectType.WEAKNESS,
            PotionEffectType.UNLUCK, PotionEffectType.HUNGER);
    private static final List<PotionEffectType> BOOT_GOOD = List.of(
            PotionEffectType.SPEED, PotionEffectType.JUMP_BOOST,
            PotionEffectType.SLOW_FALLING, PotionEffectType.DOLPHINS_GRACE);
    private static final List<PotionEffectType> BOOT_BAD = List.of(
            PotionEffectType.SLOWNESS, PotionEffectType.LEVITATION,
            PotionEffectType.WEAKNESS, PotionEffectType.UNLUCK);

    private static final Set<PotionEffectType> BAD_EFFECTS = new HashSet<>();

    static {
        BAD_EFFECTS.addAll(HELMET_BAD);
        BAD_EFFECTS.addAll(CHEST_BAD);
        BAD_EFFECTS.addAll(LEGGING_BAD);
        BAD_EFFECTS.addAll(BOOT_BAD);
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

    public static boolean isKeepInventoryHelmet(ItemStack item) {
        return kind(item) == Kind.KEEP_INVENTORY_HELMET;
    }

    public static ItemStack random(Random rng) {
        return random(rng, 0.02);
    }

    public static ItemStack random(Random rng, double keepInventoryHelmetChance) {
        if (keepInventoryHelmetChance > 0 && rng.nextDouble() < keepInventoryHelmetChance) {
            return keepInventoryHelmet(rng);
        }
        int roll = rng.nextInt(100);
        if (roll < 18) return areaMiner(rng, 3, false);
        if (roll < 30) return areaMiner(rng, 3, true);
        if (roll < 42) return areaMiner(rng, 4, false);
        if (roll < 50) return areaMiner(rng, 4, true);
        if (roll < 58) return areaMiner(rng, 5, false);
        if (roll < 63) return areaMiner(rng, 5, true);
        if (roll < 72) return veinMiner(rng);
        if (roll < 80) return treeMiner(rng);
        if (roll < 87) return highEnchantBook(rng);
        if (roll < 94) return highEnchantGear(rng);
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
        lore.add("§7Ability: §eMines a §f" + size + "×" + size + "§e area");
        lore.add("§7The plane follows the direction you face.");
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

    public static ItemStack highEnchantBook(Random rng) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta raw = item.getItemMeta();
        if (!(raw instanceof EnchantmentStorageMeta meta)) return item;

        Enchantment enchantment = BOOK_ENCHANTS[rng.nextInt(BOOK_ENCHANTS.length)];
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
        boolean armor = rng.nextBoolean();
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
        lore.add("§7Enchantments: §d10-15 levels");
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
        lore.add("§7The helmet shatters when you respawn.");
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
        List<PotionEffectType> pool = effectPool(slot, good);
        PotionEffectType effect = pool.get(rng.nextInt(pool.size()));
        int level = 1 + rng.nextInt(4);

        meta.setUnbreakable(true);
        if (good) meta.addEnchant(Enchantment.UNBREAKING, 10, true);
        long value = 10_000_000L + level * 3_000_000L + rng.nextInt(4_000_000);

        List<String> lore = new ArrayList<>();
        lore.add("§7Eternal: §fnever loses durability");
        lore.add("§7While worn: " + effectLabel(effect) + " §f" + level);
        lore.add("§7Only one effect can exist on a piece.");
        lore.add("§7The effect is bound to the " + slotName(slot) + " slot.");
        lore.add("§8-------------------------");
        lore.add("§8Legendary relic • one of a limited supply");
        lore.add("§5Relic value: §d" + Money.format(value));

        String id = "eternal_" + material.name().toLowerCase(Locale.ROOT) + "_"
                + effect.getName() + "_" + level;
        mark(meta, Kind.ETERNAL_ARMOR, id, value, rng);
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(key(EFFECT), PersistentDataType.STRING, effect.getName());
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
            PersistentDataContainer pdc = piece.getItemMeta().getPersistentDataContainer();
            String rawEffect = pdc.get(key(EFFECT), PersistentDataType.STRING);
            Integer rawLevel = pdc.get(key(EFFECT_LEVEL), PersistentDataType.INTEGER);
            if (rawEffect == null || rawLevel == null) continue;

            PotionEffectType type = effectByName(rawEffect);
            if (type == null) continue;

            int amplifier = Math.max(0, Math.min(3, rawLevel - 1));
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
            if (type.getName().equalsIgnoreCase(raw)) return type;
        }
        return null;
    }

    private static List<PotionEffectType> effectPool(ArmorSlot slot, boolean good) {
        return switch (slot) {
            case HELMET -> good ? HELMET_GOOD : HELMET_BAD;
            case CHESTPLATE -> good ? CHEST_GOOD : CHEST_BAD;
            case LEGGINGS -> good ? LEGGING_GOOD : LEGGING_BAD;
            case BOOTS -> good ? BOOT_GOOD : BOOT_BAD;
        };
    }

    private static String slotName(ArmorSlot slot) {
        return switch (slot) {
            case HELMET -> "helmet";
            case CHESTPLATE -> "chestplate";
            case LEGGINGS -> "leggings";
            case BOOTS -> "boots";
        };
    }

    private static void applyHighEnchants(Random rng, ItemMeta meta, boolean armor) {
        Enchantment[] pool = armor ? ARMOR_ENCHANTS : TOOL_ENCHANTS;
        int count = 2 + rng.nextInt(3);
        Set<Integer> groups = new HashSet<>();
        for (int i = 0; i < count * 6 && meta.getEnchants().size() < count; i++) {
            Enchantment enchantment = pool[rng.nextInt(pool.length)];
            int group = CONFLICTS.getOrDefault(enchantment, 0);
            if (group != 0 && !groups.add(group)) continue;
            meta.addEnchant(enchantment, 10 + rng.nextInt(6), true);
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
        String label = pretty(type.getName());
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
