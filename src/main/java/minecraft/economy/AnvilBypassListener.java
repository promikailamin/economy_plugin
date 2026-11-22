package minecraft.economy;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Removes the vanilla anvil limits:
 *
 * <ul>
 *   <li>Enchantment levels above their vanilla maximum (including 10-15 books) can be applied.</li>
 *   <li>Two equal levels combine into the next level, even beyond the vanilla cap
 *       (Protection 4 + Protection 4 = Protection 5).</li>
 *   <li>Conflicting enchantments can coexist (Infinity + Mending, Sharpness + Smite, ...).</li>
 *   <li>The "Too Expensive" repair cost never blocks a custom merge.</li>
 *   <li>Special enchantment books can be applied to tools and armour; combining two
 *       books of the same special enchantment raises its level by one.</li>
 * </ul>
 *
 * <p>The result is previewed with {@link PrepareAnvilEvent} and the actual take is handled in
 * {@link InventoryClickEvent} so vanilla can never re-apply its caps or conflict rules.</p>
 */
public final class AnvilBypassListener implements Listener {

    private static final int RESULT_SLOT = 2;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory inventory = event.getInventory();
        ItemStack merged = merge(inventory.getItem(0), inventory.getItem(1));
        if (merged == null) return;

        event.setResult(merged);
        event.getView().bypassEnchantmentLevelRestriction(true);
        event.getView().setMaximumRepairCost(Integer.MAX_VALUE);
        event.getView().setRepairCost(1);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getRawSlot() != RESULT_SLOT) return;
        if (!(event.getView().getTopInventory() instanceof AnvilInventory anvil)) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack merged = merge(anvil.getItem(0), anvil.getItem(1));
        if (merged == null) return;

        event.setCancelled(true);

        ClickType click = event.getClick();
        if (click == ClickType.SHIFT_LEFT || click == ClickType.SHIFT_RIGHT) {
            if (!Items.canFit(player, merged, merged.getAmount())) {
                player.sendMessage("§cYour inventory is full!");
                return;
            }
            Items.give(player, merged, merged.getAmount());
        } else if (click == ClickType.NUMBER_KEY) {
            int slot = event.getHotbarButton();
            if (slot < 0 || slot > 8) return;
            ItemStack current = player.getInventory().getItem(slot);
            if (current == null || current.getType().isAir()) {
                player.getInventory().setItem(slot, merged);
            } else if (current.isSimilar(merged)
                    && current.getAmount() + merged.getAmount() <= current.getMaxStackSize()) {
                current.setAmount(current.getAmount() + merged.getAmount());
                player.getInventory().setItem(slot, current);
            } else {
                return;
            }
        } else if (click == ClickType.SWAP_OFFHAND) {
            ItemStack off = player.getInventory().getItemInOffHand();
            if (off == null || off.getType().isAir()) {
                player.getInventory().setItemInOffHand(merged);
            } else if (off.isSimilar(merged)
                    && off.getAmount() + merged.getAmount() <= off.getMaxStackSize()) {
                off.setAmount(off.getAmount() + merged.getAmount());
                player.getInventory().setItemInOffHand(off);
            } else {
                return;
            }
        } else if (click == ClickType.DROP || click == ClickType.CONTROL_DROP) {
            player.getWorld().dropItemNaturally(player.getLocation(), merged);
        } else {
            ItemStack cursor = event.getCursor();
            if (cursor == null || cursor.getType().isAir()) {
                event.setCursor(merged);
            } else if (cursor.isSimilar(merged)
                    && cursor.getAmount() + merged.getAmount() <= cursor.getMaxStackSize()) {
                cursor.setAmount(cursor.getAmount() + merged.getAmount());
                event.setCursor(cursor);
            } else {
                return;
            }
        }

        consume(anvil, 0);
        consume(anvil, 1);
        anvil.setItem(RESULT_SLOT, null);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 0.8f, 1.0f);
        player.updateInventory();
    }

    private static void consume(AnvilInventory inventory, int slot) {
        ItemStack item = inventory.getItem(slot);
        if (item == null) return;
        if (item.getAmount() <= 1) {
            inventory.setItem(slot, null);
        } else {
            item.setAmount(item.getAmount() - 1);
            inventory.setItem(slot, item);
        }
    }

    /**
     * Builds the merged result, or returns {@code null} when the two inputs are not a valid
     * custom anvil combination. Equal enchantment levels increase by one; different levels
     * keep the higher level. No vanilla maximum, conflict or cost rules are applied.
     */
    public static ItemStack merge(ItemStack first, ItemStack second) {
        if (first == null || second == null) return null;
        if (first.getType().isAir() || second.getType().isAir()) return null;

        boolean firstSpecialBook = SpecialEnchantments.isBook(first);
        boolean secondSpecialBook = SpecialEnchantments.isBook(second);
        if (firstSpecialBook || secondSpecialBook) {
            if (firstSpecialBook && secondSpecialBook) {
                return SpecialEnchantments.combineBooks(first, second);
            }
            ItemStack book = firstSpecialBook ? first : second;
            ItemStack target = firstSpecialBook ? second : first;
            return SpecialEnchantments.apply(book, target);
        }

        if (!canMerge(first, second)) return null;

        ItemStack base = first.clone();
        base.setAmount(1);
        ItemMeta meta = base.getItemMeta();
        if (meta == null) return null;

        Map<Enchantment, Integer> merged = new LinkedHashMap<>();
        collect(merged, first);
        collect(merged, second);

        if (meta instanceof EnchantmentStorageMeta storage) {
            for (Map.Entry<Enchantment, Integer> entry : merged.entrySet()) {
                storage.addStoredEnchant(entry.getKey(), entry.getValue(), true);
            }
        } else {
            for (Map.Entry<Enchantment, Integer> entry : merged.entrySet()) {
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
            if (first.getType() == second.getType() && meta instanceof Damageable damageable) {
                int max = base.getType().getMaxDurability();
                if (max > 0) {
                    int damage = damageOf(first) + damageOf(second) - max;
                    damageable.setDamage(Math.max(0, Math.min(max - 1, damage)));
                }
            }
        }
        base.setItemMeta(meta);
        return base;
    }

    private static boolean canMerge(ItemStack first, ItemStack second) {
        if (first == null || second == null) return false;
        if (first.getType().isAir() || second.getType().isAir()) return false;

        boolean firstBook = first.getType() == Material.ENCHANTED_BOOK;
        boolean secondBook = second.getType() == Material.ENCHANTED_BOOK;
        if (firstBook) return secondBook && hasEnchants(second);
        if (secondBook) return hasEnchants(second);
        if (first.getType() != second.getType()) return false;
        return hasEnchants(first) || hasEnchants(second);
    }

    private static boolean hasEnchants(ItemStack item) {
        if (item == null || item.getType().isAir()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        if (!meta.getEnchants().isEmpty()) return true;
        return meta instanceof EnchantmentStorageMeta storage && !storage.getStoredEnchants().isEmpty();
    }

    private static void collect(Map<Enchantment, Integer> levels, ItemStack item) {
        if (item == null || item.getType().isAir()) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        Map<Enchantment, Integer> source = meta instanceof EnchantmentStorageMeta storage
                ? storage.getStoredEnchants()
                : meta.getEnchants();
        for (Map.Entry<Enchantment, Integer> entry : source.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();
            Integer existing = levels.get(enchantment);
            if (existing == null) {
                levels.put(enchantment, level);
            } else if (existing == level) {
                levels.put(enchantment, existing + 1);
            } else {
                levels.put(enchantment, Math.max(existing, level));
            }
        }
    }

    private static int damageOf(ItemStack item) {
        if (item == null) return 0;
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof Damageable damageable) return damageable.getDamage();
        return 0;
    }
}
