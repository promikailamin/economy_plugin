package minecraft.economy;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SpecialToolListener implements Listener {

    private static final int[][] NEIGHBORS = {
            {1, 0, 0}, {-1, 0, 0},
            {0, 1, 0}, {0, -1, 0},
            {0, 0, 1}, {0, 0, -1}
    };

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof LivingEntity victim)) return;

        ItemStack weapon = player.getInventory().getItemInMainHand();
        String ability = SpecialEnchantments.effectiveAbility(weapon);
        if (SpecialEnchantments.family(ability) != SpecialEnchantments.Family.EFFECT) return;

        PotionEffectType type = SpecialEnchantments.effectType(ability);
        int level = SpecialEnchantments.effectiveLevel(weapon);
        if (type == null || level <= 0) return;

        int amplifier = Math.max(0, Math.min(9, level - 1));
        PotionEffect current = victim.getPotionEffect(type);
        if (current != null && current.getAmplifier() > amplifier) return;
        victim.addPotionEffect(new PotionEffect(type, 200, amplifier, true, true, true), true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (player.isSneaking()) return;

        ItemStack tool = player.getInventory().getItemInMainHand();
        String ability = SpecialEnchantments.effectiveAbility(tool);
        SpecialEnchantments.Family family = SpecialEnchantments.family(ability);
        if (family == null) return;

        int level = SpecialEnchantments.effectiveLevel(tool);
        Block origin = event.getBlock();
        List<Block> extra = switch (family) {
            case AREA -> origin.isPreferredTool(tool)
                    ? areaBlocks(tool, origin, SpecialEnchantments.areaSize(level))
                    : List.of();
            case VEIN -> veinBlocks(tool, origin, SpecialEnchantments.veinLimit(level));
            case TREE -> treeBlocks(tool, origin, SpecialEnchantments.treeLimit(level));
            case EFFECT -> List.of();
        };
        if (extra.isEmpty()) return;

        int broken = 0;
        for (Block block : extra) {
            if (!canBreak(block)) continue;
            if (block.breakNaturally(tool)) broken++;
        }
        if (broken > 0) damageTool(player, tool, broken);
    }

    /**
     * Relative offsets of every block in a {@code size x size x size} cube centred on the
     * block the player broke. The origin block itself is omitted because the break event
     * already handles it. Even sizes place the extra layer on the negative side so the
     * broken block is always part of the cube.
     */
    public static List<int[]> areaOffsets(int size) {
        size = Math.max(3, Math.min(5, size));
        int min = -(size / 2);
        int max = min + size - 1;
        List<int[]> offsets = new ArrayList<>(size * size * size - 1);
        for (int x = min; x <= max; x++) {
            for (int y = min; y <= max; y++) {
                for (int z = min; z <= max; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    offsets.add(new int[]{x, y, z});
                }
            }
        }
        return offsets;
    }

    private static List<Block> areaBlocks(ItemStack tool, Block origin, int size) {
        if (size < 3) return List.of();

        List<Block> blocks = new ArrayList<>();
        for (int[] offset : areaOffsets(size)) {
            Block block = origin.getRelative(offset[0], offset[1], offset[2]);
            if (block.isPreferredTool(tool)) blocks.add(block);
        }
        return blocks;
    }

    private static List<Block> veinBlocks(ItemStack tool, Block origin, int limit) {
        Material target = origin.getType();
        if (!isOre(target)) return List.of();

        List<Block> blocks = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        ArrayDeque<Block> queue = new ArrayDeque<>();
        queue.add(origin);
        visited.add(origin.getBlockKey());

        while (!queue.isEmpty() && blocks.size() < limit) {
            Block current = queue.poll();
            for (int[] offset : NEIGHBORS) {
                Block next = current.getRelative(offset[0], offset[1], offset[2]);
                if (!visited.add(next.getBlockKey())) continue;
                if (next.getType() != target || !canBreak(next)) continue;
                if (!next.isPreferredTool(tool)) continue;
                blocks.add(next);
                queue.add(next);
            }
        }
        return blocks;
    }

    private static List<Block> treeBlocks(ItemStack tool, Block origin, int limit) {
        Material target = origin.getType();
        if (!isLog(target)) return List.of();

        List<Block> blocks = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        ArrayDeque<Block> queue = new ArrayDeque<>();
        queue.add(origin);
        visited.add(origin.getBlockKey());

        while (!queue.isEmpty() && blocks.size() < limit) {
            Block current = queue.poll();
            for (int[] offset : NEIGHBORS) {
                Block next = current.getRelative(offset[0], offset[1], offset[2]);
                if (!visited.add(next.getBlockKey())) continue;
                if (next.getType() != target || !canBreak(next)) continue;
                if (!next.isPreferredTool(tool)) continue;
                blocks.add(next);
                queue.add(next);
            }
        }
        return blocks;
    }

    private static boolean canBreak(Block block) {
        Material type = block.getType();
        if (type.isAir() || type.getHardness() < 0) return false;
        return !(block.getState() instanceof Container);
    }

    private static boolean isOre(Material type) {
        String name = type.name();
        return name.endsWith("_ORE") || type == Material.ANCIENT_DEBRIS;
    }

    private static boolean isLog(Material type) {
        String name = type.name();
        return name.endsWith("_LOG") || name.endsWith("_WOOD")
                || name.endsWith("_STEM") || name.endsWith("_HYPHAE");
    }

    private static void damageTool(Player player, ItemStack tool, int amount) {
        ItemStack damaged = tool.damage(amount, player);
        player.getInventory().setItemInMainHand(damaged == null || damaged.getType().isAir() ? null : damaged);
    }
}
