package minecraft.economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/** Main shop menu, category browser and the buy-confirmation screen. */
public final class ShopGui {

    public static final String[] CATEGORIES = {
            "blocks", "tools", "armor", "food", "ores",
            "redstone", "nether", "end", "misc", "rare"
    };

    private static final Material[] CATEGORY_ICONS = {
            Material.BRICKS, Material.IRON_PICKAXE, Material.IRON_CHESTPLATE, Material.GOLDEN_APPLE,
            Material.DIAMOND_ORE, Material.REDSTONE, Material.NETHER_BRICK, Material.ENDER_EYE,
            Material.COMPASS, Material.DRAGON_EGG
    };

    private static final int[] CATEGORY_SLOTS = {0, 1, 2, 3, 5, 6, 7, 8, 9, 10};

    private ShopGui() {
    }

    public static void openMainMenu(Player player) {
        new MainMenu(player).open();
    }

    public static void openCategoryPage(Player player, String category, int page) {
        String cat = category == null ? "all" : category.toLowerCase(Locale.ROOT);
        if (!cat.equals("all") && !List.of(CATEGORIES).contains(cat)) {
            player.sendMessage(ChatColor.RED + "Unknown category '" + category + "'. Try: all, "
                    + String.join(", ", CATEGORIES));
            openMainMenu(player);
            return;
        }
        CategoryView.create(player, cat, page).open();
    }

    public static void confirmBuy(Player player, String itemId, int amount) {
        EconomyData.ShopEntry entry = EconomyData.get(itemId);
        if (entry == null) {
            player.sendMessage(ChatColor.RED + "Item not found in shop.");
            return;
        }
        if (!entry.buyable) {
            player.sendMessage(ChatColor.RED + entry.name + " is not sold by the shop.");
            return;
        }
        new ConfirmBuy(player, entry, Math.max(1, Math.min(64, amount))).open();
    }

    static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    // ================================================================== main menu

    private static final class MainMenu extends EcoGui {
        MainMenu(Player player) {
            super(player, 6, "§6Economy Shop");

            for (int i = 0; i < CATEGORIES.length; i++) {
                String cat = CATEGORIES[i];
                inv.setItem(CATEGORY_SLOTS[i], item(CATEGORY_ICONS[i], "§e" + capitalize(cat),
                        "§7Browse §e" + cat + " §7items", "§aClick to browse"));
            }
            inv.setItem(4, item(Material.COMPASS, "§6All Items",
                    "§7Browse all shop items", "§aClick to browse"));

            inv.setItem(45, item(Material.ENDER_EYE, "§b§lAuction House",
                    "§7Buy and sell items with other players", "§aClick to open"));
            inv.setItem(46, item(Material.GOLD_INGOT, "§a§lBuy Catalog",
                    "§7Browse the catalog by item family", "§aClick to open"));
            inv.setItem(47, item(Material.HOPPER, "§e§lSell Chest",
                    "§7Drop items in, close the chest", "§7and they are sold!", "§aClick to open"));
            inv.setItem(HEAD, balanceHead(player));
            inv.setItem(CLOSE, item(Material.BARRIER, "§cClose", "§7Close this menu"));
            fillEmpty(inv, 0, 54, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Override
        protected void handle(InventoryClickEvent e, int slot) {
            GuiHistoryManager.GuiState me = new GuiHistoryManager.GuiState("main");
            if (slot == 4) {
                navigate(me, () -> openCategoryPage(player, "all", 1));
                return;
            }
            for (int i = 0; i < CATEGORY_SLOTS.length; i++) {
                if (CATEGORY_SLOTS[i] == slot) {
                    String cat = CATEGORIES[i];
                    navigate(me, () -> openCategoryPage(player, cat, 1));
                    return;
                }
            }
            switch (slot) {
                case 45 -> navigate(me, () -> AuctionGui.open(player, 1));
                case 46 -> navigate(me, () -> BuyGui.open(player, "all", 1));
                case 47 -> navigate(me, () -> SellGui.open(player));
                case CLOSE -> close();
                default -> {
                }
            }
        }
    }

    // ================================================================== category browser

    private static final class CategoryView extends EcoGui {
        private static final int PER_PAGE = 45;
        private final String category;
        private final int page;
        private final int maxPages;
        private final List<EconomyData.ShopEntry> pageEntries;

        static CategoryView create(Player player, String category, int requestedPage) {
            List<EconomyData.ShopEntry> entries = EconomyData.all().stream()
                    .filter(e -> category.equals("all") || e.category.equals(category))
                    .sorted(Comparator.comparing(e -> e.name))
                    .collect(Collectors.toList());
            int maxPages = Math.max(1, (entries.size() + PER_PAGE - 1) / PER_PAGE);
            int page = Math.max(1, Math.min(requestedPage, maxPages));
            int start = (page - 1) * PER_PAGE;
            List<EconomyData.ShopEntry> slice =
                    new ArrayList<>(entries.subList(start, Math.min(start + PER_PAGE, entries.size())));
            String title = "§6Shop: §e" + (category.equals("all") ? "All" : capitalize(category))
                    + " §8(" + page + "/" + maxPages + ")";
            return new CategoryView(player, title, category, page, maxPages, slice);
        }

        private CategoryView(Player player, String title, String category, int page, int maxPages,
                             List<EconomyData.ShopEntry> pageEntries) {
            super(player, 6, title);
            this.category = category;
            this.page = page;
            this.maxPages = maxPages;
            this.pageEntries = pageEntries;

            for (int i = 0; i < pageEntries.size(); i++) {
                inv.setItem(i, shopItem(pageEntries.get(i), true));
            }
            inv.setItem(BACK, item(Material.ARROW, "§c« Back", "§7Return to previous screen"));
            if (page > 1) inv.setItem(46, item(Material.YELLOW_STAINED_GLASS_PANE, "§e« Prev Page", "§7Page " + (page - 1)));
            inv.setItem(47, item(Material.GOLD_INGOT, "§a§lBuy Catalog", "§7Catalog grouped by item family"));
            inv.setItem(HEAD, balanceHead(player));
            inv.setItem(50, item(Material.HOPPER, "§e§lSell Chest", "§7Drop items in, close to sell"));
            if (page < maxPages) inv.setItem(52, item(Material.LIME_STAINED_GLASS_PANE, "§aNext Page »", "§7Page " + (page + 1)));
            inv.setItem(CLOSE, item(Material.BARRIER, "§cClose", "§7Close this menu"));
            fillEmpty(inv, 45, 54, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Override
        protected void handle(InventoryClickEvent e, int slot) {
            GuiHistoryManager.GuiState me = new GuiHistoryManager.GuiState("category", category, page);
            switch (slot) {
                case BACK -> back();
                case 46 -> { if (page > 1) CategoryView.create(player, category, page - 1).open(); }
                case 47 -> navigate(me, () -> BuyGui.open(player, "all", 1));
                case 50 -> navigate(me, () -> SellGui.open(player));
                case 52 -> { if (page < maxPages) CategoryView.create(player, category, page + 1).open(); }
                case CLOSE -> close();
                default -> {
                    if (slot >= 0 && slot < pageEntries.size()) {
                        EconomyData.ShopEntry entry = pageEntries.get(slot);
                        if (entry.buyable) navigate(me, () -> confirmBuy(player, entry.id, 1));
                        else player.sendMessage(ChatColor.RED + entry.name + " is not sold by the shop.");
                    }
                }
            }
        }
    }

    // ================================================================== buy confirmation

    private static final class ConfirmBuy extends EcoGui {
        private final EconomyData.ShopEntry entry;
        private final int amount;

        ConfirmBuy(Player player, EconomyData.ShopEntry entry, int amount) {
            super(player, 3, "§6Buy: " + entry.name);
            this.entry = entry;
            this.amount = amount;
            long cost = entry.buy * amount;

            ItemStack preview = new ItemStack(entry.material, Math.min(amount, entry.material.getMaxStackSize()));
            ItemMeta meta = preview.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§f" + entry.name + " §7x" + amount);
                meta.setLore(List.of("§7Total cost: §6" + EconomyData.formatMoney(cost),
                        "§7Price each: §6" + EconomyData.formatMoney(entry.buy)));
                preview.setItemMeta(meta);
            }
            inv.setItem(13, preview);

            inv.setItem(9, item(Material.RED_STAINED_GLASS_PANE, "§c-64", "§7Reduce amount by 64"));
            inv.setItem(10, item(Material.RED_STAINED_GLASS_PANE, "§c-8", "§7Reduce amount by 8"));
            inv.setItem(11, item(Material.RED_STAINED_GLASS_PANE, "§c-1", "§7Reduce amount by 1"));
            inv.setItem(15, item(Material.LIME_STAINED_GLASS_PANE, "§a+1", "§7Increase amount by 1"));
            inv.setItem(16, item(Material.LIME_STAINED_GLASS_PANE, "§a+8", "§7Increase amount by 8"));
            inv.setItem(17, item(Material.LIME_STAINED_GLASS_PANE, "§a+64", "§7Increase amount by 64"));

            inv.setItem(2, balanceHead(player));
            inv.setItem(22, item(Material.EMERALD_BLOCK,
                    "§a✔ Buy " + amount + "x for " + EconomyData.formatMoney(cost), "§7Click to confirm purchase"));
            inv.setItem(24, item(Material.REDSTONE_BLOCK, "§cCancel", "§7Go back"));
            fillEmpty(inv, 0, 27, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Override
        protected void handle(InventoryClickEvent e, int slot) {
            switch (slot) {
                case 22 -> {
                    Items.buy(player, entry, amount);
                    confirmBuy(player, entry.id, amount); // refresh balance
                }
                case 24 -> back();
                case 9 -> confirmBuy(player, entry.id, amount - 64);
                case 10 -> confirmBuy(player, entry.id, amount - 8);
                case 11 -> confirmBuy(player, entry.id, amount - 1);
                case 15 -> confirmBuy(player, entry.id, amount + 1);
                case 16 -> confirmBuy(player, entry.id, amount + 8);
                case 17 -> confirmBuy(player, entry.id, amount + 64);
                default -> {
                }
            }
        }
    }
}
