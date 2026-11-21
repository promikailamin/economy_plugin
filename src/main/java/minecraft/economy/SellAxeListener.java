package minecraft.economy;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Breaking a chest with a Sell Axe cancels the break, keeps the chest in place and sells
 * everything inside it. A double chest is sold as one combined inventory. Blocks other
 * than chests break normally.
 */
public final class SellAxeListener implements Listener {

    public record SaleResult(int sold, long earned) {
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        ItemStack tool = player.getInventory().getItemInMainHand();
        if (!SellAxe.isSellAxe(tool)) return;

        Block block = event.getBlock();
        if (!isChest(block.getType())) return;

        BlockState state = block.getState();
        if (!(state instanceof Chest chest)) return;

        // Keep the chest itself; only its contents are sold.
        event.setCancelled(true);

        // For a double chest getInventory() returns the combined inventory of both halves.
        SaleResult result = sellChestContents(chest.getInventory());
        if (result.sold() == 0) {
            player.sendActionBar(ChatColor.YELLOW + "No sellable items in this chest.");
            return;
        }

        EconomyData.deposit(player.getUniqueId(), result.earned());
        player.sendActionBar(ChatColor.GREEN + "Auto-sold " + result.sold() + " item"
                + (result.sold() == 1 ? "" : "s") + " for " + EconomyData.formatMoney(result.earned()));
    }

    /**
     * Sells every sellable stack in {@code inventory}, leaving items the shop does not buy
     * untouched. Returns how many items were sold and how much money they are worth.
     */
    public static SaleResult sellChestContents(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();
        long earned = 0;
        int sold = 0;
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null || item.getType().isAir() || item.getAmount() <= 0) continue;
            EconomyData.ShopEntry entry = Items.sellEntry(item);
            if (entry == null) continue; // items the shop doesn't buy stay in the chest
            earned += entry.sell * item.getAmount();
            sold += item.getAmount();
            inventory.setItem(i, null);
        }
        return new SaleResult(sold, earned);
    }

    private static boolean isChest(Material type) {
        return type == Material.CHEST || type == Material.TRAPPED_CHEST;
    }
}
