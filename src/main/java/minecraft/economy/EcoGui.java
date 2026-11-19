package minecraft.economy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for every economy GUI. Each GUI is the {@link InventoryHolder} of its own
 * inventory, so {@link GuiListener} can recognise it reliably (no title parsing) and
 * cancel clicks so players can never take the decoration items out.
 */
public abstract class EcoGui implements InventoryHolder {

    public static final int BACK = 45;
    public static final int HEAD = 49;
    public static final int CLOSE = 53;

    protected final Player player;
    protected final Inventory inv;

    @SuppressWarnings("deprecation")
    protected EcoGui(Player player, int rows, String title) {
        this.player = player;
        this.inv = Bukkit.createInventory(this, rows * 9, title);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void open() {
        player.openInventory(inv);
    }

    /** Default: cancel everything, route clicks on this GUI's own slots to {@link #handle}. */
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getClickedInventory() == inv) {
            handle(e, e.getSlot());
        }
    }

    protected abstract void handle(InventoryClickEvent e, int slot);

    public void onDrag(InventoryDragEvent e) {
        e.setCancelled(true);
    }

    public void onClose(InventoryCloseEvent e) {
    }

    // ------------------------------------------------------------------ navigation

    protected void navigate(GuiHistoryManager.GuiState from, Runnable open) {
        GuiHistoryManager.get().push(player, from);
        open.run();
    }

    protected void back() {
        GuiHistoryManager.GuiState prev = GuiHistoryManager.get().pop(player);
        if (prev != null) {
            GuiHistoryManager.restore(player, prev);
        } else {
            ShopGui.openMainMenu(player);
        }
    }

    protected void close() {
        player.closeInventory();
    }

    protected void refreshHead() {
        inv.setItem(HEAD, balanceHead(player));
    }

    // ------------------------------------------------------------------ item helpers

    public static ItemStack item(Material material, String name, String... lore) {
        return item(material, name, List.of(lore));
    }

    public static ItemStack item(Material material, String name, List<String> lore) {
        ItemStack stack = new ItemStack(material, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(new ArrayList<>(lore));
            meta.addItemFlags(ItemFlag.values());
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static ItemStack pane(Material material) {
        return item(material, " ");
    }

    public static void fillEmpty(Inventory inv, int from, int to, Material pane) {
        for (int i = from; i < to && i < inv.getSize(); i++) {
            ItemStack cur = inv.getItem(i);
            if (cur == null || cur.getType() == Material.AIR) inv.setItem(i, pane(pane));
        }
    }

    /** Shop item as shown in browse screens, with price lore. */
    public static ItemStack shopItem(EconomyData.ShopEntry e, boolean withActions) {
        ItemStack stack = new ItemStack(e.material, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§f" + e.name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Category: §e" + e.category);
            if (e.buyable) lore.add("§aBuy price: §6" + EconomyData.formatMoney(e.buy));
            else lore.add("§cNot sold by the shop");
            if (e.sellable()) lore.add("§eSell price: §6" + EconomyData.formatMoney(e.sell));
            if (!e.note.isEmpty()) lore.add("§8" + e.note);
            if (withActions && e.buyable) {
                lore.add("§8-------------------------");
                lore.add("§a▶ Left-Click: §fBuy 1x");
                lore.add("§d▶ Right-Click: §fSelect quantity");
            }
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.values());
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static ItemStack balanceHead(Player player) {
        long balance = EconomyData.getBalance(player);
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta raw = stack.getItemMeta();
        if (raw instanceof SkullMeta meta) {
            meta.setOwningPlayer(player);
            List<String> lore = new ArrayList<>();
            if (balance < 0) {
                meta.setDisplayName("§c§l" + player.getName() + " §7| §cIn Debt");
                lore.add("§cBalance: §4" + EconomyData.formatMoney(balance));
                lore.add("§7-------------------------");
                lore.add("§7Status: §cDebt outstanding");
                lore.add("§7Any money you earn pays it off first.");
            } else {
                meta.setDisplayName("§6§l" + player.getName() + " §7| §eProfile");
                lore.add("§7Balance: §6" + EconomyData.formatMoney(balance));
                lore.add("§8(" + Money.exact(balance) + ")");
                lore.add("§7-------------------------");
                lore.add("§aAccount active");
            }
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.values());
            stack.setItemMeta(meta);
        }
        return stack;
    }
}
