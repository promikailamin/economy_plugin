package minecraft.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Sell chest: an empty chest the player drops items into. Closing it sells everything the
 * shop buys and hands the rest back.
 */
public final class SellGui extends EcoGui {

    private static final int DROP_SLOTS = 45; // slots 0-44 are free, row 5 is the control bar
    private static final int PREVIEW = 48;

    private boolean settled = false;

    public static void open(Player player) {
        new SellGui(player).open();
    }

    private SellGui(Player player) {
        super(player, 6, "§eSell Chest §8(drop items, then close)");
        inv.setItem(BACK, item(Material.ARROW, "§c« Back", "§7Return to previous screen", "§8Items placed are sold on close"));
        inv.setItem(HEAD, balanceHead(player));
        inv.setItem(CLOSE, item(Material.BARRIER, "§cClose & Sell", "§7Closing sells everything placed"));
        refreshPreview();
        fillEmpty(inv, DROP_SLOTS, 54, Material.GRAY_STAINED_GLASS_PANE);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
            e.setCancelled(true);
            return;
        }
        int raw = e.getRawSlot();
        if (raw >= DROP_SLOTS && raw < inv.getSize()) { // control bar
            e.setCancelled(true);
            handle(e, raw);
            return;
        }
        schedulePreview(); // click inside the drop area or own inventory: allowed
    }

    @Override
    public void onDrag(InventoryDragEvent e) {
        for (int raw : e.getRawSlots()) {
            if (raw >= DROP_SLOTS && raw < inv.getSize()) {
                e.setCancelled(true);
                return;
            }
        }
        schedulePreview();
    }

    @Override
    protected void handle(InventoryClickEvent e, int slot) {
        switch (slot) {
            case BACK -> back();
            case CLOSE -> close();
            default -> {
            }
        }
    }

    private void schedulePreview() {
        Bukkit.getScheduler().runTask(EconomyPlugin.get(), this::refreshPreview);
    }

    private void refreshPreview() {
        int count = 0;
        long value = 0;
        for (int i = 0; i < DROP_SLOTS; i++) {
            ItemStack s = inv.getItem(i);
            EconomyData.ShopEntry entry = Items.sellEntry(s);
            if (entry != null) {
                count += s.getAmount();
                value += entry.sell * s.getAmount();
            }
        }
        inv.setItem(PREVIEW, item(Material.GOLD_NUGGET, "§eSale preview",
                "§7Sellable items: §f" + count,
                "§7You will receive: §6" + EconomyData.formatMoney(value),
                "§8Close the chest to sell"));
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        if (settled) return;
        settled = true;

        int sold = 0;
        long earned = 0;
        boolean returned = false;
        for (int i = 0; i < DROP_SLOTS; i++) {
            ItemStack s = inv.getItem(i);
            if (s == null || s.getType().isAir()) continue;
            inv.setItem(i, null);

            EconomyData.ShopEntry entry = Items.sellEntry(s);
            if (entry != null) {
                sold += s.getAmount();
                earned += entry.sell * s.getAmount();
            } else {
                Items.give(player, s, s.getAmount()); // not sellable: give it back
                returned = true;
            }
        }

        if (earned > 0) EconomyData.deposit(player.getUniqueId(), earned);
        if (sold > 0) {
            player.sendMessage(ChatColor.GREEN + "Sold " + sold + " item" + (sold == 1 ? "" : "s") + " for "
                    + ChatColor.GOLD + EconomyData.formatMoney(earned) + ChatColor.GREEN + ".");
        }
        if (returned) {
            player.sendMessage(ChatColor.YELLOW + "Items the shop doesn't buy were returned to you.");
        }
    }
}
