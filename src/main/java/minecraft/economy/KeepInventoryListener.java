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
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Implements the Keep Inventory Helmet relic.
 *
 * <p>The helmet protects the whole inventory on death, but experience is still lost. After
 * respawning the helmet loses 25% of its maximum durability; it only shatters when that would
 * leave less than 25% durability remaining.</p>
 */
public final class KeepInventoryListener implements Listener {

    private static final double DURABILITY_LOSS = 0.25;
    private static final double MIN_DURABILITY = 0.25;

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
        ItemStack helmet = inventory.getHelmet();
        if (matches(helmet, serial)) {
            damageHelmet(player, helmet, -1);
            return;
        }
        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            if (!matches(contents[i], serial)) continue;
            damageHelmet(player, contents[i], i);
            break;
        }
    }

    private void damageHelmet(Player player, ItemStack helmet, int slot) {
        PlayerInventory inventory = player.getInventory();
        short max = helmet.getType().getMaxDurability();
        if (!(helmet.getItemMeta() instanceof Damageable damageable) || max <= 0) {
            return;
        }

        int loss = Math.max(1, (int) Math.ceil(max * DURABILITY_LOSS));
        int nextDamage = damageable.getDamage() + loss;
        int minRemaining = (int) Math.ceil(max * MIN_DURABILITY);

        if (nextDamage >= max || max - nextDamage < minRemaining) {
            if (slot < 0) inventory.setHelmet(null);
            else inventory.setItem(slot, null);
            player.sendMessage(ChatColor.LIGHT_PURPLE
                    + "Your Helm of Preservation shattered after saving your inventory.");
            return;
        }

        damageable.setDamage(nextDamage);
        helmet.setItemMeta(damageable);
        if (slot < 0) inventory.setHelmet(helmet);
        else inventory.setItem(slot, helmet);

        int remaining = (int) Math.round((max - nextDamage) * 100.0 / max);
        player.sendMessage(ChatColor.LIGHT_PURPLE
                + "Your Helm of Preservation lost 25% durability ("
                + ChatColor.YELLOW + remaining + "%" + ChatColor.LIGHT_PURPLE + " remaining).");
    }

    private static boolean matches(ItemStack item, int serial) {
        if (!SpecialItems.isKeepInventoryHelmet(item)) return false;
        return serial <= 0 || SpecialItems.serial(item) == serial;
    }
}
