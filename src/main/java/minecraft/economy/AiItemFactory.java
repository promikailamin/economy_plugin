package minecraft.economy;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class AiItemFactory {

    public enum Quality {
        COMMON, UNCOMMON, RARE, EPIC, LEGENDARY;

        public int rank() {
            return ordinal();
        }

        public static Quality byRank(int rank) {
            Quality[] values = values();
            return values[Math.max(0, Math.min(values.length - 1, rank))];
        }
    }

    private static final Material[] SWORDS = {
            Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD,
            Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD
    };
    private static final Material[] PICKAXES = {
            Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE
    };
    private static final Material[] AXES = {
            Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE,
            Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE
    };
    private static final Material[] SHOVELS = {
            Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL,
            Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL
    };
    private static final Material[] HOES = {
            Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE,
            Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE
    };
    private static final Material[] HELMETS = {
            Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET,
            Material.GOLDEN_HELMET, Material.DIAMOND_HELMET, Material.NETHERITE_HELMET,
            Material.TURTLE_HELMET
    };
    private static final Material[] CHESTPLATES = {
            Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE,
            Material.GOLDEN_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE
    };
    private static final Material[] LEGGINGS = {
            Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS,
            Material.GOLDEN_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS
    };
    private static final Material[] BOOTS = {
            Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS,
            Material.GOLDEN_BOOTS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS
    };
    private static final Material[] WEAPONS = {
            Material.BOW, Material.CROSSBOW, Material.TRIDENT, Material.SHIELD, Material.MACE
    };
    private static final Material[] TREASURES = {
            Material.TOTEM_OF_UNDYING, Material.ELYTRA, Material.ENCHANTED_GOLDEN_APPLE,
            Material.NETHERITE_INGOT, Material.ANCIENT_DEBRIS, Material.EXPERIENCE_BOTTLE,
            Material.DRAGON_BREATH, Material.NETHER_STAR
    };
    private static final Material[] SUPPLIES = {
            Material.COAL, Material.ARROW, Material.IRON_INGOT, Material.GOLD_INGOT,
            Material.DIAMOND, Material.EMERALD, Material.ENDER_PEARL, Material.EXPERIENCE_BOTTLE,
            Material.GOLDEN_APPLE, Material.GOLDEN_CARROT, Material.COOKED_BEEF, Material.BREAD,
            Material.OAK_LOG, Material.IRON_BLOCK, Material.DIAMOND_BLOCK,
            Material.REDSTONE, Material.LAPIS_LAZULI, Material.QUARTZ
    };
    private static final Material[] SHULKERS = {
            Material.SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX,
            Material.CYAN_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.PURPLE_SHULKER_BOX,
            Material.MAGENTA_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.RED_SHULKER_BOX,
            Material.ORANGE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.LIME_SHULKER_BOX,
            Material.GREEN_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.GRAY_SHULKER_BOX,
            Material.BLACK_SHULKER_BOX
    };
    private static final Material[] TOP_UP = {
            Material.COAL, Material.ARROW, Material.IRON_INGOT, Material.GOLD_INGOT,
            Material.EMERALD, Material.DIAMOND, Material.NETHERITE_INGOT
    };

    private static final Enchantment[] TOOL_ENCHANTS = {
            Enchantment.EFFICIENCY, Enchantment.SILK_TOUCH, Enchantment.FORTUNE,
            Enchantment.UNBREAKING, Enchantment.MENDING, Enchantment.VANISHING_CURSE
    };
    private static final Enchantment[] SWORD_ENCHANTS = {
            Enchantment.SHARPNESS, Enchantment.SMITE, Enchantment.BANE_OF_ARTHROPODS,
            Enchantment.KNOCKBACK, Enchantment.FIRE_ASPECT, Enchantment.LOOTING,
            Enchantment.SWEEPING_EDGE, Enchantment.UNBREAKING, Enchantment.MENDING,
            Enchantment.VANISHING_CURSE
    };
    private static final Enchantment[] ARMOR_ENCHANTS = {
            Enchantment.PROTECTION, Enchantment.FIRE_PROTECTION, Enchantment.BLAST_PROTECTION,
            Enchantment.PROJECTILE_PROTECTION, Enchantment.THORNS, Enchantment.UNBREAKING,
            Enchantment.MENDING, Enchantment.BINDING_CURSE, Enchantment.VANISHING_CURSE
    };
    private static final Enchantment[] HELMET_ENCHANTS = {
            Enchantment.RESPIRATION, Enchantment.AQUA_AFFINITY
    };
    private static final Enchantment[] BOOT_ENCHANTS = {
            Enchantment.FEATHER_FALLING, Enchantment.DEPTH_STRIDER, Enchantment.FROST_WALKER,
            Enchantment.SOUL_SPEED
    };
    private static final Enchantment[] LEGGING_ENCHANTS = {
            Enchantment.SWIFT_SNEAK
    };
    private static final Enchantment[] BOW_ENCHANTS = {
            Enchantment.POWER, Enchantment.PUNCH, Enchantment.FLAME, Enchantment.INFINITY,
            Enchantment.UNBREAKING, Enchantment.MENDING, Enchantment.VANISHING_CURSE
    };
    private static final Enchantment[] CROSSBOW_ENCHANTS = {
            Enchantment.MULTISHOT, Enchantment.QUICK_CHARGE, Enchantment.PIERCING,
            Enchantment.UNBREAKING, Enchantment.MENDING, Enchantment.VANISHING_CURSE
    };
    private static final Enchantment[] TRIDENT_ENCHANTS = {
            Enchantment.LOYALTY, Enchantment.IMPALING, Enchantment.RIPTIDE,
            Enchantment.CHANNELING, Enchantment.UNBREAKING, Enchantment.MENDING
    };
    private static final Enchantment[] MACE_ENCHANTS = {
            Enchantment.DENSITY, Enchantment.BREACH, Enchantment.WIND_BURST,
            Enchantment.SMITE, Enchantment.FIRE_ASPECT, Enchantment.UNBREAKING,
            Enchantment.MENDING
    };
    private static final Enchantment[] ELYTRA_ENCHANTS = {
            Enchantment.UNBREAKING, Enchantment.MENDING, Enchantment.VANISHING_CURSE
    };
    private static final Enchantment[] BOOK_ENCHANTS = {
            Enchantment.SHARPNESS, Enchantment.PROTECTION, Enchantment.EFFICIENCY,
            Enchantment.FORTUNE, Enchantment.SILK_TOUCH, Enchantment.UNBREAKING,
            Enchantment.MENDING, Enchantment.LOOTING, Enchantment.POWER,
            Enchantment.INFINITY, Enchantment.FEATHER_FALLING, Enchantment.THORNS,
            Enchantment.RESPIRATION, Enchantment.SOUL_SPEED, Enchantment.SWIFT_SNEAK,
            Enchantment.DENSITY, Enchantment.BREACH, Enchantment.WIND_BURST
    };

    private static final Map<Enchantment, Integer> CONFLICTS = Map.ofEntries(
            Map.entry(Enchantment.PROTECTION, 1),
            Map.entry(Enchantment.FIRE_PROTECTION, 1),
            Map.entry(Enchantment.BLAST_PROTECTION, 1),
            Map.entry(Enchantment.PROJECTILE_PROTECTION, 1),
            Map.entry(Enchantment.SHARPNESS, 2),
            Map.entry(Enchantment.SMITE, 2),
            Map.entry(Enchantment.BANE_OF_ARTHROPODS, 2),
            Map.entry(Enchantment.SILK_TOUCH, 3),
            Map.entry(Enchantment.FORTUNE, 3),
            Map.entry(Enchantment.LOYALTY, 4),
            Map.entry(Enchantment.RIPTIDE, 4),
            Map.entry(Enchantment.CHANNELING, 5),
            Map.entry(Enchantment.DEPTH_STRIDER, 6),
            Map.entry(Enchantment.FROST_WALKER, 6)
    );

    private static final String[] NAME_PREFIXES = {
            "Rusty", "Sturdy", "Gleaming", "Cracked", "Ancient", "Lucky", "Silent",
            "Furious", "Worn", "Blessed", "Scavenger's", "Miner's", "Raider's",
            "Nightfall", "Ember", "Frostbound", "Void", "Storm"
    };

    private AiItemFactory() {
    }

    public static ItemStack generate(Random rng, String archetype, Quality quality) {
        return generate(rng, archetype, quality, 0.02);
    }

    public static ItemStack generate(Random rng, String archetype, Quality quality,
                                     double keepInventoryHelmetChance) {
        String id = archetype == null ? "random" : archetype.toLowerCase(Locale.ROOT);
        return switch (id) {
            case "junk" -> randomItem(rng, quality);
            case "tools" -> tool(rng, quality, false);
            case "enchanted_tools" -> tool(rng, quality, true);
            case "armor" -> armor(rng, quality, false);
            case "enchanted_armor" -> armor(rng, quality, true);
            case "weapons" -> weapon(rng, quality);
            case "crates" -> supplyCrate(rng, quality);
            case "treasures" -> treasure(rng, quality);
            case "books" -> enchantedBook(rng, quality);
            case "supplies" -> supplies(rng, quality);
            case "special" -> SpecialItems.random(rng, keepInventoryHelmetChance);
            default -> randomItem(rng, quality);
        };
    }

    public static ItemStack randomItem(Random rng, Quality quality) {
        double roll = rng.nextDouble();
        if (roll < 0.30) return tool(rng, quality, quality.rank() >= 2 && rng.nextDouble() < 0.45);
        if (roll < 0.49) return armor(rng, quality, quality.rank() >= 2 && rng.nextDouble() < 0.40);
        if (roll < 0.59) return weapon(rng, quality);
        if (roll < 0.70) return supplies(rng, quality);
        if (roll < 0.78) return enchantedBook(rng, quality);
        if (roll < 0.86 && quality.rank() >= 1) return supplyCrate(rng, quality);
        if (roll < 0.94 && quality.rank() >= 1) return treasure(rng, quality);
        return supplies(rng, quality);
    }

    public static ItemStack tool(Random rng, Quality quality, boolean forceEnchanted) {
        int kind = rng.nextInt(5);
        Material material = switch (kind) {
            case 0 -> tiered(rng, quality, SWORDS);
            case 1 -> tiered(rng, quality, PICKAXES);
            case 2 -> tiered(rng, quality, AXES);
            case 3 -> tiered(rng, quality, SHOVELS);
            default -> tiered(rng, quality, HOES);
        };
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        Enchantment[] pool = kind == 0 ? SWORD_ENCHANTS : TOOL_ENCHANTS;
        applyEnchants(rng, meta, quality, forceEnchanted, pool);
        wear(rng, item, meta, quality);
        maybeName(rng, meta, item.getType(), quality);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack armor(Random rng, Quality quality, boolean forceEnchanted) {
        int slot = rng.nextInt(4);
        Material material = switch (slot) {
            case 0 -> tiered(rng, quality, HELMETS);
            case 1 -> tiered(rng, quality, CHESTPLATES);
            case 2 -> tiered(rng, quality, LEGGINGS);
            default -> tiered(rng, quality, BOOTS);
        };
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        List<Enchantment> pool = new ArrayList<>(List.of(ARMOR_ENCHANTS));
        switch (slot) {
            case 0 -> pool.addAll(List.of(HELMET_ENCHANTS));
            case 2 -> pool.addAll(List.of(LEGGING_ENCHANTS));
            case 3 -> pool.addAll(List.of(BOOT_ENCHANTS));
            default -> {
            }
        }
        applyEnchants(rng, meta, quality, forceEnchanted, pool.toArray(new Enchantment[0]));
        wear(rng, item, meta, quality);
        maybeName(rng, meta, item.getType(), quality);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack weapon(Random rng, Quality quality) {
        Material material = tieredWeapon(rng, quality);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        Enchantment[] pool = switch (material) {
            case BOW -> BOW_ENCHANTS;
            case CROSSBOW -> CROSSBOW_ENCHANTS;
            case TRIDENT -> TRIDENT_ENCHANTS;
            case MACE -> MACE_ENCHANTS;
            case ELYTRA -> ELYTRA_ENCHANTS;
            default -> SWORD_ENCHANTS;
        };
        applyEnchants(rng, meta, quality, quality.rank() >= 3 && rng.nextDouble() < 0.7, pool);
        wear(rng, item, meta, quality);
        maybeName(rng, meta, material, quality);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack treasure(Random rng, Quality quality) {
        Material material = TREASURES[rng.nextInt(TREASURES.length)];
        if (quality.rank() < 2 && material == Material.ELYTRA) material = Material.GOLDEN_APPLE;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        if (material == Material.ELYTRA) {
            applyEnchants(rng, meta, quality, rng.nextDouble() < 0.8, ELYTRA_ENCHANTS);
        } else if (material == Material.NETHERITE_INGOT || material == Material.ANCIENT_DEBRIS) {
            item.setAmount(1 + rng.nextInt(quality.rank() >= 3 ? 4 : 2));
        } else if (material == Material.EXPERIENCE_BOTTLE) {
            item.setAmount(4 + rng.nextInt(20));
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack supplies(Random rng, Quality quality) {
        Material material = SUPPLIES[rng.nextInt(SUPPLIES.length)];
        ItemStack item = new ItemStack(material);
        int max = Math.max(1, material.getMaxStackSize());
        int base = switch (material) {
            case COAL, REDSTONE, LAPIS_LAZULI, QUARTZ -> 16;
            case ARROW -> 24;
            case GOLDEN_CARROT, COOKED_BEEF, BREAD -> 16;
            default -> 4;
        };
        int amount = Math.min(max, base + rng.nextInt(base + 1));
        item.setAmount(Math.max(1, amount));
        return item;
    }

    public static ItemStack enchantedBook(Random rng, Quality quality) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta raw = item.getItemMeta();
        if (!(raw instanceof EnchantmentStorageMeta meta)) return item;
        int count = 1 + rng.nextInt(Math.max(1, quality.rank() + 2));
        Set<Integer> groups = new HashSet<>();
        for (int i = 0; i < count * 4 && meta.getStoredEnchants().size() < count; i++) {
            Enchantment enchantment = BOOK_ENCHANTS[rng.nextInt(BOOK_ENCHANTS.length)];
            int group = CONFLICTS.getOrDefault(enchantment, 0);
            if (group != 0 && !groups.add(group)) continue;
            int level = 1 + rng.nextInt(Math.min(enchantment.getMaxLevel(), quality.rank() + 2));
            meta.addStoredEnchant(enchantment, level, true);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack supplyCrate(Random rng, Quality quality) {
        int theme = rng.nextInt(5);
        Material shulker = SHULKERS[rng.nextInt(SHULKERS.length)];
        ItemStack item = new ItemStack(shulker);
        ItemMeta raw = item.getItemMeta();
        if (!(raw instanceof BlockStateMeta meta)) return item;
        BlockState state = meta.getBlockState();
        if (!(state instanceof ShulkerBox box)) return item;

        int entries = switch (theme) {
            case 0, 1 -> 3 + rng.nextInt(4);
            case 2 -> 3 + rng.nextInt(5);
            case 3 -> 5 + rng.nextInt(7);
            default -> 2 + rng.nextInt(4);
        };
        for (int i = 0; i < entries; i++) {
            ItemStack content;
            if (theme == 0) {
                content = tool(rng, quality, quality.rank() >= 2 && rng.nextDouble() < 0.6);
            } else if (theme == 1) {
                content = armor(rng, quality, quality.rank() >= 2 && rng.nextDouble() < 0.6);
            } else if (theme == 2) {
                content = treasure(rng, quality);
            } else if (theme == 3) {
                content = supplies(rng, quality);
            } else {
                content = randomItem(rng, quality);
            }
            if (content.getType().name().endsWith("SHULKER_BOX")) continue;
            int slot = rng.nextInt(27);
            if (box.getInventory().getItem(slot) == null) box.getInventory().setItem(slot, content);
        }
        meta.setBlockState(box);
        meta.setDisplayName(crateName(rng, theme));
        meta.setLore(List.of(
                "§7A sealed cache from a travelling trader.",
                "§8Contents are a surprise.",
                "§7Theme: §e" + themeName(theme)
        ));
        item.setItemMeta(meta);
        return item;
    }

    public static List<ItemStack> lootBundle(Random rng, Quality quality, long targetValue, boolean jackpot) {
        List<ItemStack> items = new ArrayList<>();
        long target = Math.max(100, targetValue);
        long min = Math.max(1, Math.round(target * 0.80));
        long max = Math.max(min, Math.round(target * 1.35));
        long total = 0;

        int attempts = 0;
        while (items.size() < 5 && attempts++ < 80) {
            ItemStack candidate = randomItem(rng, quality);
            long value = value(candidate);
            if (value <= 0) continue;
            if (candidate.getMaxStackSize() > 1 && value > max) {
                long unit = Math.max(1, value / candidate.getAmount());
                int amount = (int) Math.max(1, Math.min(candidate.getAmount(), max / unit));
                candidate.setAmount(amount);
                value = value(candidate);
            }
            if (value > max * 1.5) continue;
            if (!items.isEmpty() && total + value > max) continue;
            items.add(candidate);
            total += value;
            if (total >= min) break;
        }

        if (items.isEmpty()) {
            ItemStack fallback = topUp(rng, target);
            items.add(fallback);
            total = value(fallback);
        }

        int topUps = 0;
        while (total < min && topUps++ < 3) {
            ItemStack topUp = topUp(rng, min - total);
            long value = value(topUp);
            if (value <= 0 || total + value > max) break;
            items.add(topUp);
            total += value;
        }
        return items;
    }

    private static ItemStack topUp(Random rng, long remaining) {
        Material best = null;
        long bestUnit = Long.MAX_VALUE;
        Material cheapest = Material.COAL;
        long cheapestUnit = Long.MAX_VALUE;
        for (Material material : TOP_UP) {
            long unit = value(new ItemStack(material));
            if (unit <= 0) continue;
            if (unit < cheapestUnit) {
                cheapest = material;
                cheapestUnit = unit;
            }
            if (unit <= remaining && unit < bestUnit) {
                best = material;
                bestUnit = unit;
            }
        }
        if (best == null) {
            best = cheapest;
            bestUnit = Math.max(1, cheapestUnit);
        }
        int amount = (int) Math.max(1, Math.min(best.getMaxStackSize(), remaining / bestUnit));
        return new ItemStack(best, amount);
    }

    private static Material tiered(Random rng, Quality quality, Material[] options) {
        int rank = quality.rank();
        int low = Math.max(0, Math.min(options.length - 1, rank - 1));
        int high = Math.max(low, Math.min(options.length - 1, rank + 1));
        if (rng.nextDouble() < 0.25) low = Math.max(0, low - 1);
        int index = low + rng.nextInt(Math.max(1, high - low + 1));
        return options[Math.min(options.length - 1, index)];
    }

    private static Material tieredWeapon(Random rng, Quality quality) {
        if (quality.rank() >= 3) {
            return switch (rng.nextInt(5)) {
                case 0 -> Material.BOW;
                case 1 -> Material.CROSSBOW;
                case 2 -> Material.TRIDENT;
                case 3 -> Material.MACE;
                default -> Material.SHIELD;
            };
        }
        if (quality.rank() >= 2) {
            return switch (rng.nextInt(4)) {
                case 0 -> Material.BOW;
                case 1 -> Material.CROSSBOW;
                case 2 -> Material.SHIELD;
                default -> Material.TRIDENT;
            };
        }
        return rng.nextBoolean() ? Material.BOW : Material.SHIELD;
    }

    private static void applyEnchants(Random rng, ItemMeta meta, Quality quality, boolean force,
                                      Enchantment... pool) {
        if (pool.length == 0) return;
        int maxLevel = Math.max(1, quality.rank() + 1);
        int count;
        if (force) {
            count = Math.max(2, quality.rank() + 1);
        } else {
            double chance = switch (quality) {
                case COMMON -> 0.55;
                case UNCOMMON -> 0.75;
                case RARE -> 0.90;
                default -> 1.0;
            };
            if (rng.nextDouble() > chance) return;
            count = 1 + rng.nextInt(quality.rank() + 2);
        }
        count = Math.min(6, count);

        Set<Integer> groups = new HashSet<>();
        for (int i = 0; i < count * 5 && meta.getEnchants().size() < count; i++) {
            Enchantment enchantment = pool[rng.nextInt(pool.length)];
            if (enchantment.isCursed() && rng.nextDouble() < 0.75) continue;
            int group = CONFLICTS.getOrDefault(enchantment, 0);
            if (group != 0 && !groups.add(group)) continue;
            int cap = Math.min(enchantment.getMaxLevel(), maxLevel);
            int level = 1 + rng.nextInt(Math.max(1, cap));
            meta.addEnchant(enchantment, level, true);
        }
    }

    private static void wear(Random rng, ItemStack item, ItemMeta meta, Quality quality) {
        if (!(meta instanceof Damageable damageable)) return;
        short max = item.getType().getMaxDurability();
        if (max <= 0) return;
        double roll = rng.nextDouble();
        double missing;
        double pristine = quality.rank() >= 3 ? 0.22 : 0.10;
        if (roll < pristine) missing = 0;
        else if (roll < 0.55) missing = 0.03 + rng.nextDouble() * 0.27;
        else if (roll < 0.86) missing = 0.30 + rng.nextDouble() * 0.35;
        else missing = 0.65 + rng.nextDouble() * 0.32;
        damageable.setDamage(Math.max(0, Math.min(max - 1, (int) (max * missing))));
    }

    private static void maybeName(Random rng, ItemMeta meta, Material material, Quality quality) {
        double chance = quality.rank() >= 3 ? 0.25 : 0.12;
        if (rng.nextDouble() >= chance) return;
        String base = pretty(material.name());
        String prefix = NAME_PREFIXES[rng.nextInt(NAME_PREFIXES.length)];
        meta.setDisplayName("§f" + prefix + " " + base);
    }

    private static String crateName(Random rng, int theme) {
        String[] themes = switch (theme) {
            case 0 -> new String[]{"§6Miner's Supply Crate", "§6Prospector's Crate", "§6Tool Cache"};
            case 1 -> new String[]{"§bArmory Crate", "§bKnight's Cache", "§bBattle Box"};
            case 2 -> new String[]{"§dRelic Crate", "§dTreasure Box", "§dLucky Cache"};
            case 3 -> new String[]{"§aTrader's Crate", "§aSupply Bundle", "§aCamp Stash"};
            default -> new String[]{"§eWanderer's Crate", "§eMystery Box", "§eTravelling Cache"};
        };
        return themes[rng.nextInt(themes.length)];
    }

    private static String themeName(int theme) {
        return switch (theme) {
            case 0 -> "tools";
            case 1 -> "armour";
            case 2 -> "relics";
            case 3 -> "supplies";
            default -> "mixed";
        };
    }

    public static long value(ItemStack item) {
        return value(item, 0);
    }

    private static long value(ItemStack item, int depth) {
        if (item == null || item.getType().isAir()) return 0;
        long relicValue = SpecialItems.value(item);
        if (relicValue > 0) return relicValue * Math.max(1, item.getAmount());
        Material type = item.getType();
        EconomyData.ShopEntry entry = EconomyData.get(type.name());
        long base;
        if (entry == null) {
            base = Math.max(100, type.getMaxDurability());
        } else {
            base = Math.max(entry.sell, entry.buy / 2);
        }
        if (base <= 0) base = Math.max(100, type.getMaxDurability());
        if (base <= 0) base = 250;

        double multiplier = 1.0;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (meta instanceof Damageable damageable && type.getMaxDurability() > 0) {
                double remain = 1.0 - (double) damageable.getDamage() / type.getMaxDurability();
                multiplier *= 0.30 + 0.70 * NeuralNetwork.clamp01(remain);
            }
            for (Map.Entry<Enchantment, Integer> enchant : meta.getEnchants().entrySet()) {
                multiplier += enchant.getValue() * (enchant.getKey().isCursed() ? -0.04 : 0.10);
            }
            if (meta instanceof EnchantmentStorageMeta storage) {
                for (Map.Entry<Enchantment, Integer> enchant : storage.getStoredEnchants().entrySet()) {
                    multiplier += enchant.getValue() * (enchant.getKey().isCursed() ? -0.04 : 0.13);
                }
            }
            if (meta.hasDisplayName()) multiplier *= 1.04;
        }
        multiplier = Math.max(0.20, multiplier);
        long total = Math.round(base * multiplier) * Math.max(1, item.getAmount());

        if (depth < 2 && meta instanceof BlockStateMeta blockState
                && blockState.getBlockState() instanceof Container container) {
            for (ItemStack content : container.getInventory().getContents()) {
                total += value(content, depth + 1);
            }
        }
        if (depth < 2 && meta instanceof BundleMeta bundle && bundle.hasItems()) {
            for (ItemStack content : bundle.getItems()) total += value(content, depth + 1);
        }
        return Math.max(1, total);
    }

    public static String pretty(String id) {
        StringBuilder sb = new StringBuilder();
        for (String part : id.toLowerCase(Locale.ROOT).split("_")) {
            if (part.isEmpty()) continue;
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return sb.toString();
    }
}
