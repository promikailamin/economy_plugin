package minecraft.economy;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * The "Sell Axe I" tool.
 *
 * <p>Custom enchantments cannot be registered at runtime on modern Paper (they're
 * data-driven), so the Sell Axe is marked with persistent item data instead. It shows an
 * enchant glint and a lore line, survives anvils/renaming, and needs no resource pack.</p>
 */
public final class SellAxe {

    public static final String LORE = "§6Sell Axe I";

    private SellAxe() {
    }

    private static NamespacedKey key() {
        return new NamespacedKey(EconomyPlugin.get(), "sell_axe");
    }

    public static boolean isAxe(ItemStack item) {
        return item != null && item.getType().name().endsWith("_AXE");
    }

    public static boolean isSellAxe(ItemStack item) {
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) return false;
        Byte flag = item.getItemMeta().getPersistentDataContainer().get(key(), PersistentDataType.BYTE);
        return flag != null && flag == 1;
    }

    /** Marks an axe as a Sell Axe. Returns false if the item is not an axe. */
    public static boolean apply(ItemStack item) {
        if (!isAxe(item)) return false;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key(), PersistentDataType.BYTE, (byte) 1);
        meta.setEnchantmentGlintOverride(true);
        List<String> lore = meta.getLore() == null ? new ArrayList<>() : new ArrayList<>(meta.getLore());
        if (!lore.contains(LORE)) lore.add(0, LORE);
        lore.add("§7Broken blocks are sold automatically.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return true;
    }

    public static ItemStack create(Material axe) {
        ItemStack stack = new ItemStack(axe);
        apply(stack);
        return stack;
    }
}
