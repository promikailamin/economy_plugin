package minecraft.economy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/** Single dispatcher that forwards inventory events to the {@link EcoGui} that owns the top inventory. */
public final class GuiListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder(false) instanceof EcoGui gui) {
            gui.onClick(e);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDrag(InventoryDragEvent e) {
        if (e.getView().getTopInventory().getHolder(false) instanceof EcoGui gui) {
            gui.onDrag(e);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getView().getTopInventory().getHolder(false) instanceof EcoGui gui) {
            gui.onClose(e);
            // Moving from one economy screen to the next keeps the "Back" history.
            if (e.getReason() != InventoryCloseEvent.Reason.OPEN_NEW && e.getPlayer() instanceof Player p) {
                GuiHistoryManager.get().clear(p);
            }
        }
    }
}
