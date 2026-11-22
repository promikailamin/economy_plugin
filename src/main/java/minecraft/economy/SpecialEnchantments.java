package minecraft.economy;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Special enchantments that are found as enchanted books in the auction house and
 * applied to tools and armour with an anvil.
 *
 * <p>A special enchantment is stored in the item's persistent data. An item can hold
 * only one special enchantment at a time, but the same enchantment can be upgraded by
 * combining it with another book of the same type. Effect enchantments stack additively
 * across all worn armour pieces, exactly like the issue requested (Regeneration II on a
 * chestplate plus Regeneration III on leggings gives Regeneration V).</p>
 */
public final class SpecialEnchantments {

    public enum Family {
        AREA, VEIN, TREE, EFFECT
    }

    public static final String AREA = "area_miner";
    public static final String VEIN = "vein_miner";
    public static final String TREE = "tree_miner";
    public static final String EFFECT_PREFIX = "effect_";

    public static final int MAX_EFFECT_LEVEL = 10;

    private static final String ABILITY = "special_ability";
    private static final String LEVEL = "special_ability_level";
    private static final String LORE_PREFIX = "§d✦ ";

    private SpecialEnchantments() {
    }

    private static NamespacedKey key(String name) {
        return new NamespacedKey(EconomyPlugin.get(), name);
    }

    // ------------------------------------------------------------------ queries

    public static boolean isBook(ItemStack item) {
        return SpecialItems.kind(item) == SpecialItems.Kind.ABILITY_BOOK
                && ability(item) != null;
    }

    public static String ability(ItemStack item) {
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) return null;
        String raw = item.getItemMeta().getPersistentDataContainer()
                .get(key(ABILITY), PersistentDataType.STRING);
        return raw == null || raw.isBlank() ? null : raw;
    }

    public static int level(ItemStack item) {
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) return 0;
        Integer value = item.getItemMeta().getPersistentDataContainer()
                .get(key(LEVEL), PersistentDataType.INTEGER);
        return value == null ? 0 : value;
    }

    /** The special ability on an item, including the abilities of older relic items. */
    public static String effectiveAbility(ItemStack item) {
        String stored = ability(item);
        if (stored != null) return stored;

        SpecialItems.Kind kind = SpecialItems.kind(item);
        if (kind == null) return null;
        return switch (kind) {
            case AREA_MINER -> AREA;
            case VEIN_MINER -> VEIN;
            case TREE_MINER -> TREE;
            case EFFECT_SWORD, ETERNAL_ARMOR -> {
                PotionEffectType type = SpecialItems.effect(item);
                yield type == null ? null : effectAbility(type);
            }
            default -> null;
        };
    }

    /** The level of the special ability, including the abilities of older relic items. */
    public static int effectiveLevel(ItemStack item) {
        if (ability(item) != null) return Math.max(1, level(item));

        SpecialItems.Kind kind = SpecialItems.kind(item);
        if (kind == null) return 0;
        return switch (kind) {
            case AREA_MINER -> Math.max(1, Math.min(3, SpecialItems.areaSize(item) - 2));
            case VEIN_MINER -> 2;
            case TREE_MINER -> 2;
            case EFFECT_SWORD, ETERNAL_ARMOR -> Math.max(0, SpecialItems.effectLevel(item));
            default -> 0;
        };
    }

    public static Family family(String ability) {
        if (ability == null) return null;
        if (ability.equals(AREA)) return Family.AREA;
        if (ability.equals(VEIN)) return Family.VEIN;
        if (ability.equals(TREE)) return Family.TREE;
        if (ability.startsWith(EFFECT_PREFIX)) return Family.EFFECT;
        return null;
    }

    public static boolean isEffect(String ability) {
        return family(ability) == Family.EFFECT;
    }

    public static PotionEffectType effectType(String ability) {
        if (!isEffect(ability)) return null;
        return SpecialItems.effectByName(ability.substring(EFFECT_PREFIX.length()));
    }

    public static String effectAbility(PotionEffectType type) {
        return type == null ? null : EFFECT_PREFIX + SpecialItems.effectId(type);
    }

    public static int maxLevel(String ability) {
        Family family = family(ability);
        if (family == null) return 0;
        return family == Family.EFFECT ? MAX_EFFECT_LEVEL : 3;
    }

    public static int areaSize(int level) {
        return Math.max(3, Math.min(5, 2 + Math.max(1, level)));
    }

    public static int veinLimit(int level) {
        return switch (Math.max(1, Math.min(3, level))) {
            case 1 -> 32;
            case 2 -> 64;
            default -> 128;
        };
    }

    public static int treeLimit(int level) {
        return switch (Math.max(1, Math.min(3, level))) {
            case 1 -> 64;
            case 2 -> 128;
            default -> 192;
        };
    }

    public static String label(String ability) {
        Family family = family(ability);
        if (family == null) return "Unknown";
        return switch (family) {
            case AREA -> "Area Miner";
            case VEIN -> "Vein Miner";
            case TREE -> "Tree Feller";
            case EFFECT -> {
                PotionEffectType type = effectType(ability);
                yield type == null ? "Effect" : SpecialItems.pretty(SpecialItems.effectId(type));
            }
        };
    }

    public static String coloredLabel(String ability) {
        Family family = family(ability);
        if (family == Family.EFFECT) {
            PotionEffectType type = effectType(ability);
            if (type != null) return SpecialItems.effectLabel(type);
        }
        return "§d" + label(ability);
    }

    // ------------------------------------------------------------------ books

    public static ItemStack randomBook(Random rng) {
        int roll = rng.nextInt(100);
        if (roll < 30) return book(AREA, 1 + rng.nextInt(3), rng);
        if (roll < 50) return book(VEIN, 1 + rng.nextInt(3), rng);
        if (roll < 65) return book(TREE, 1 + rng.nextInt(3), rng);

        List<PotionEffectType> effects = SpecialItems.allEffects();
        PotionEffectType type = effects.get(rng.nextInt(effects.size()));
        return book(effectAbility(type), 1 + rng.nextInt(3), rng);
    }

    public static ItemStack book(String ability, int level) {
        return book(ability, level, new Random());
    }

    public static ItemStack book(String ability, int level, Random rng) {
        if (family(ability) == null) return new ItemStack(Material.ENCHANTED_BOOK);
        int capped = Math.max(1, Math.min(maxLevel(ability), level));

        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        long value = bookValue(ability, capped);
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(key(ABILITY), PersistentDataType.STRING, ability);
        pdc.set(key(LEVEL), PersistentDataType.INTEGER, capped);

        List<String> lore = new ArrayList<>();
        lore.add("§7Special enchantment: " + coloredLabel(ability) + " §f" + capped);
        lore.addAll(description(ability, capped));
        lore.add("§7Apply in an anvil to a valid item.");
        lore.add("§7Two books of the same level combine into the next level.");
        lore.add("§7An item can hold only one special enchantment.");
        lore.add("§8-------------------------");
        lore.add("§8Special enchantment book");
        lore.add("§5Book value: §d" + Money.format(value));

        meta.setDisplayName("§d§lBook of " + label(ability) + " §f" + capped);
        meta.setLore(lore);
        SpecialItems.mark(meta, SpecialItems.Kind.ABILITY_BOOK,
                "ability_book_" + ability + "_" + capped, value, rng);
        item.setItemMeta(meta);
        return item;
    }

    private static long bookValue(String ability, int level) {
        return switch (family(ability)) {
            case AREA -> 2_000_000L + level * 3_000_000L;
            case VEIN -> 3_500_000L + level * 1_500_000L;
            case TREE -> 2_500_000L + level * 1_000_000L;
            case EFFECT -> 5_000_000L + level * 2_000_000L;
            default -> 1_000_000L;
        };
    }

    private static List<String> description(String ability, int level) {
        return switch (family(ability)) {
            case AREA -> {
                int size = areaSize(level);
                yield List.of("§7Mines a §f" + size + "×" + size + "×" + size
                        + "§7 cube centred on the block you break.",
                        "§7Sneak to disable area mining.");
            }
            case VEIN -> List.of("§7Breaks up to §f" + veinLimit(level)
                    + "§7 connected ore blocks.", "§7Sneak to disable vein mining.");
            case TREE -> List.of("§7Fells up to §f" + treeLimit(level)
                    + "§7 connected logs.", "§7Sneak to disable tree felling.");
            case EFFECT -> List.of("§7While worn: " + coloredLabel(ability) + " §f" + level,
                    "§7On a weapon: inflicts " + coloredLabel(ability) + " §f" + level + "§7 on hit.",
                    "§7The same effect stacks across worn armour pieces.");
            default -> List.of();
        };
    }

    // ------------------------------------------------------------------ anvil logic

    /** True when the book can be applied to the target item. */
    public static boolean canApply(ItemStack book, ItemStack target) {
        if (!isBook(book) || target == null || target.getType().isAir()) return false;
        if (target.getType() == Material.ENCHANTED_BOOK) return false;

        String ability = ability(book);
        if (!validTarget(ability, target.getType())) return false;

        String existing = effectiveAbility(target);
        return existing == null || existing.equals(ability);
    }

    private static boolean validTarget(String ability, Material material) {
        Family family = family(ability);
        if (family == null) return false;
        return switch (family) {
            case AREA -> isPickaxe(material) || isShovel(material);
            case VEIN -> isPickaxe(material);
            case TREE -> isAxe(material);
            case EFFECT -> isWeapon(material) || isArmor(material) || material == Material.ELYTRA;
        };
    }

    /** Applies a special enchantment book to an item, upgrading the same enchantment if present. */
    public static ItemStack apply(ItemStack book, ItemStack target) {
        if (!canApply(book, target)) return null;

        String ability = ability(book);
        int bookLevel = Math.max(1, level(book));
        String existing = effectiveAbility(target);
        int newLevel;
        if (existing == null) {
            newLevel = bookLevel;
        } else {
            int current = Math.max(1, effectiveLevel(target));
            newLevel = current == bookLevel ? current + 1 : Math.max(current, bookLevel);
        }
        newLevel = Math.max(1, Math.min(maxLevel(ability), newLevel));

        ItemStack result = target.clone();
        result.setAmount(1);
        ItemMeta meta = result.getItemMeta();
        if (meta == null) return null;
        writeAbility(meta, ability, newLevel);
        addLore(meta, ability, newLevel);
        result.setItemMeta(meta);
        return result;
    }

    /** Combines two books of the same special enchantment. Equal levels gain one level. */
    public static ItemStack combineBooks(ItemStack first, ItemStack second) {
        if (!isBook(first) || !isBook(second)) return null;
        String ability = ability(first);
        if (!ability.equals(ability(second))) return null;

        int firstLevel = Math.max(1, level(first));
        int secondLevel = Math.max(1, level(second));
        int merged = firstLevel == secondLevel
                ? firstLevel + 1
                : Math.max(firstLevel, secondLevel);
        merged = Math.max(1, Math.min(maxLevel(ability), merged));
        return book(ability, merged);
    }

    private static void writeAbility(ItemMeta meta, String ability, int level) {
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(key(ABILITY), PersistentDataType.STRING, ability);
        pdc.set(key(LEVEL), PersistentDataType.INTEGER, level);
        meta.setEnchantmentGlintOverride(true);
    }

    private static void addLore(ItemMeta meta, String ability, int level) {
        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        lore.removeIf(line -> line != null && line.startsWith(LORE_PREFIX));
        lore.add(LORE_PREFIX + coloredLabel(ability) + " §f" + level);
        meta.setLore(lore);
    }

    private static boolean isPickaxe(Material material) {
        return material.name().endsWith("_PICKAXE");
    }

    private static boolean isShovel(Material material) {
        return material.name().endsWith("_SHOVEL");
    }

    private static boolean isAxe(Material material) {
        return material.name().endsWith("_AXE");
    }

    private static boolean isWeapon(Material material) {
        String name = material.name();
        return name.endsWith("_SWORD") || name.endsWith("_AXE")
                || material == Material.TRIDENT || material == Material.MACE;
    }

    private static boolean isArmor(Material material) {
        String name = material.name();
        return name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS")
                || material == Material.TURTLE_HELMET || material == Material.ELYTRA;
    }
}
