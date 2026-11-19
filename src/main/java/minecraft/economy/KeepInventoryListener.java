package minecraft.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Implements the Keep Inventory Helmet relic.
 *
 * <p>The helmet protects the whole inventory on death, but experience is still lost and the
 * helmet itself shatters as soon as the player respawns.</p>
 */
public final class KeepInventoryListener implements Listener {

    private final NamespacedKey pendingSerial;

    public KeepInventoryListener(EconomyPlugin plugin) {
        this.pendingSerial = new NamespacedKey(plugin, "keep_inventory_helmet_serial");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ItemStack helmet = player.getInventory().getHelmet();
        if (!SpecialItems.isKeepInventoryHelmet(helmet)) return;

        event.setKeepInventory(true);
        event.getDrops().clear();
        event.setKeepLevel(false);
        event.setNewExp(0);
        event.setNewLevel(0);
        event.setNewTotalExp(0);
        event.setShouldDropExperience(true);

        player.getPersistentDataContainer().set(pendingSerial, PersistentDataType.INTEGER,
                SpecialItems.serial(helmet));
        player.sendMessage(ChatColor.LIGHT_PURPLE
                + "Your Helm of Preservation protected your inventory, but your experience was lost.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent event) {
        breakHelmet(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        // Covers players who quit while on the death screen and reconnect later.
        Bukkit.getScheduler().runTask(EconomyPlugin.get(), () -> breakHelmet(event.getPlayer()));
    }

    private void breakHelmet(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        Integer serial = data.get(pendingSerial, PersistentDataType.INTEGER);
        if (serial == null) return;
        data.remove(pendingSerial);

        PlayerInventory inventory = player.getInventory();
        boolean broken = false;

        ItemStack helmet = inventory.getHelmet();
        if (matches(helmet, serial)) {
            inventory.setHelmet(null);
            broken = true;
        }
        if (!broken) {
            ItemStack[] contents = inventory.getContents();
            for (int i = 0; i < contents.length; i++) {
                if (!matches(contents[i], serial)) continue;
                inventory.setItem(i, null);
                broken = true;
                break;
            }
        }
        if (broken) {
            player.sendMessage(ChatColor.LIGHT_PURPLE
                    + "Your Helm of Preservation shattered after saving your inventory.");
        }
    }

    private static boolean matches(ItemStack item, int serial) {
        if (!SpecialItems.isKeepInventoryHelmet(item)) return false;
        return serial <= 0 || SpecialItems.serial(item) == serial;
    }
}
