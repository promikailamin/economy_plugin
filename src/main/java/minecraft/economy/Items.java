package minecraft.economy;

import org.bukkit.ChatColor;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Predicate;

/** Inventory / trade helpers shared by commands and GUIs. */
public final class Items {

    private Items() {
    }

    public record SaleResult(int items, long earned) {
    }

    /**
     * The price entry for a stack if it may be sold to the shop, otherwise null.
     * Containers that still hold items (shulker boxes, bundles) and Sell Axes are refused
     * so players can never destroy contents or sell the special tool by accident.
     */
    public static EconomyData.ShopEntry sellEntry(ItemStack stack) {
        if (stack == null || stack.getType().isAir()) return null;
        EconomyData.ShopEntry e = EconomyData.get(stack.getType().name());
        if (e == null || !e.sellable()) return null;
        if (SellAxe.isSellAxe(stack)) return null;

        ItemMeta meta = stack.getItemMeta();
        if (meta instanceof BlockStateMeta bsm && bsm.hasBlockState()
                && bsm.getBlockState() instanceof Container c && !c.getInventory().isEmpty()) {
            return null;
        }
        if (meta instanceof BundleMeta bundle && bundle.hasItems()) return null;
        return e;
    }

    /** True if {@code amount} more items of this kind fit in the player's main inventory. */
    public static boolean canFit(Player player, ItemStack proto, int amount) {
        int max = proto.getMaxStackSize();
        long room = 0;
        for (ItemStack s : player.getInventory().getStorageContents()) {
            if (s == null || s.getType().isAir()) room += max;
            else if (s.isSimilar(proto)) room += Math.max(0, max - s.getAmount());
            if (room >= amount) return true;
        }
        return room >= amount;
    }

    /** Gives items, splitting into stacks; anything that does not fit is dropped at the player's feet. */
    public static void give(Player player, ItemStack proto, int amount) {
        int max = proto.getMaxStackSize();
        int left = amount;
        while (left > 0) {
            ItemStack part = proto.clone();
            part.setAmount(Math.min(max, left));
            left -= part.getAmount();
            for (ItemStack overflow : player.getInventory().addItem(part).values()) {
                player.getWorld().dropItem(player.getLocation(), overflow);
            }
        }
    }

    /** Buys {@code amount} of an item for the player. Sends feedback; returns true on success. */
    public static boolean buy(Player player, EconomyData.ShopEntry e, int amount) {
        if (!e.buyable) {
            player.sendMessage(ChatColor.RED + e.name + " is not sold by the shop.");
            return false;
        }
        long cost = e.buy * amount;
        ItemStack proto = new ItemStack(e.material);
        if (!canFit(player, proto, amount)) {
            player.sendMessage(ChatColor.RED + "Your inventory is full!");
            return false;
        }
        if (!EconomyData.withdraw(player.getUniqueId(), cost)) {
            player.sendMessage(ChatColor.RED + "Not enough money! Need " + EconomyData.formatMoney(cost)
                    + ", have " + EconomyData.formatMoney(EconomyData.getBalance(player)) + ".");
            return false;
        }
        give(player, proto, amount);
        player.sendMessage(ChatColor.GREEN + "Purchased " + amount + "x " + e.name + " for "
                + EconomyData.formatMoney(cost) + ".");
        return true;
    }

    /** Sells every sellable stack in the main inventory that matches the filter (up to {@code limit} items). */
    public static SaleResult sellFromInventory(Player player, Predicate<ItemStack> filter, int limit) {
        PlayerInventory inv = player.getInventory();
        int items = 0;
        long earned = 0;
        for (int i = 0; i < 36 && items < limit; i++) {
            ItemStack s = inv.getItem(i);
            EconomyData.ShopEntry e = sellEntry(s);
            if (e == null || !filter.test(s)) continue;

            int take = Math.min(s.getAmount(), limit - items);
            earned += e.sell * take;
            items += take;
            if (take >= s.getAmount()) inv.setItem(i, null);
            else s.setAmount(s.getAmount() - take);
        }
        if (earned > 0) EconomyData.deposit(player.getUniqueId(), earned);
        return new SaleResult(items, earned);
    }
}
