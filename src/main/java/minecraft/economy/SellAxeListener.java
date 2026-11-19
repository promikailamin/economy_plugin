package minecraft.economy;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

/**
 * Blocks broken with a Sell Axe have their sellable drops sold instantly.
 * Drops the shop doesn't buy still drop on the ground as normal.
 */
public final class SellAxeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        ItemStack tool = player.getInventory().getItemInMainHand();
        if (!SellAxe.isAxe(tool) || !SellAxe.isSellAxe(tool)) return;

        Block block = event.getBlock();
        // Never swallow the contents of chests, shulker boxes, etc.
        if (block.getState() instanceof Container) return;

        Collection<ItemStack> drops = block.getDrops(tool, player);
        if (drops.isEmpty()) return;

        long earned = 0;
        int sold = 0;
        java.util.List<ItemStack> keep = new java.util.ArrayList<>();
        for (ItemStack drop : drops) {
            if (drop == null || drop.getAmount() <= 0) continue;
            EconomyData.ShopEntry entry = Items.sellEntry(drop);
            if (entry == null) {
                keep.add(drop);
            } else {
                earned += entry.sell * drop.getAmount();
                sold += drop.getAmount();
            }
        }
        if (sold == 0) return; // nothing sellable: leave vanilla behaviour untouched

        event.setDropItems(false);
        for (ItemStack k : keep) {
            block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), k);
        }
        EconomyData.deposit(player.getUniqueId(), earned);
        player.sendActionBar(ChatColor.GREEN + "Auto-sold " + sold + " item" + (sold == 1 ? "" : "s") + " for "
                + EconomyData.formatMoney(earned));
    }
}
