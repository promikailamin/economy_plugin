package minecraft.economy;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (player.isSneaking()) return;

        ItemStack tool = player.getInventory().getItemInMainHand();
        SpecialItems.Kind kind = SpecialItems.kind(tool);
        if (kind == null) return;

        Block origin = event.getBlock();
        List<Block> extra = switch (kind) {
            case AREA_MINER -> origin.isPreferredTool(tool) ? areaBlocks(player, tool, origin) : List.of();
            case VEIN_MINER -> veinBlocks(tool, origin);
            case TREE_MINER -> treeBlocks(tool, origin);
            default -> List.of();
        };
        if (extra.isEmpty()) return;

        int broken = 0;
        for (Block block : extra) {
            if (!canBreak(block)) continue;
            if (block.breakNaturally(tool)) broken++;
        }
        if (broken > 0) damageTool(player, tool, broken);
    }

    private static List<Block> areaBlocks(Player player, ItemStack tool, Block origin) {
        int size = SpecialItems.areaSize(tool);
        if (size < 3) return List.of();
        Vector direction = player.getLocation().getDirection();
        int min = -(size / 2);
        int max = min + size - 1;
        int[] side = Math.abs(direction.getX()) >= Math.abs(direction.getZ())
                ? new int[]{0, 0, 1} : new int[]{1, 0, 0};

        List<Block> blocks = new ArrayList<>();
        if (Math.abs(direction.getY()) >= 0.7) {
            for (int x = min; x <= max; x++) {
                for (int z = min; z <= max; z++) {
                    if (x == 0 && z == 0) continue;
                    Block block = origin.getRelative(x, 0, z);
                    if (block.isPreferredTool(tool)) blocks.add(block);
                }
            }
        } else {
            for (int a = min; a <= max; a++) {
                for (int b = min; b <= max; b++) {
                    if (a == 0 && b == 0) continue;
                    Block block = origin.getRelative(side[0] * a, b, side[2] * a);
                    if (block.isPreferredTool(tool)) blocks.add(block);
                }
            }
        }
        return blocks;
    }

    private static List<Block> veinBlocks(ItemStack tool, Block origin) {
        Material target = origin.getType();
        if (!isOre(target)) return List.of();

        List<Block> blocks = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        ArrayDeque<Block> queue = new ArrayDeque<>();
        queue.add(origin);
        visited.add(origin.getBlockKey());

        while (!queue.isEmpty() && blocks.size() < 64) {
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

    private static List<Block> treeBlocks(ItemStack tool, Block origin) {
        Material target = origin.getType();
        if (!isLog(target)) return List.of();

        List<Block> blocks = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        ArrayDeque<Block> queue = new ArrayDeque<>();
        queue.add(origin);
        visited.add(origin.getBlockKey());

        while (!queue.isEmpty() && blocks.size() < 128) {
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
