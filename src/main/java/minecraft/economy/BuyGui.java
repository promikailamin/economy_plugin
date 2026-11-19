package minecraft.economy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** Buy catalog (tabbed by item family) and search results screen. */
public final class BuyGui {

    private static final int CATALOG_PER_PAGE = 36;
    private static final int SEARCH_PER_PAGE = 45;

    public static final String[] TABS = {
            "all", "wood", "stone", "ores", "tools", "armor", "food", "redstone", "misc"
    };

    public static final String[] TAB_NAMES = {
            "All Items", "Wood & Logs", "Stone & Rocks", "Ores & Metals",
            "Tools & Weapons", "Armor Gear", "Food & Crops", "Redstone", "Misc Supplies"
    };

    private BuyGui() {
    }

    public static void open(Player player, String rawCategory, int page) {
        String category = rawCategory == null || rawCategory.isBlank() ? "all" : rawCategory.toLowerCase(Locale.ROOT);
        Catalog.create(player, category, page).open();
    }

    public static void openSearch(Player player, String query) {
        openSearch(player, query, 1);
    }

    public static void openSearch(Player player, String query, int page) {
        Search.create(player, query, page).open();
    }

    // ================================================================== shared click logic

    /** Left click buys one directly, right click opens the quantity screen. */
    private static void clickItem(EcoGui gui, Player player, EconomyData.ShopEntry entry,
                                  InventoryClickEvent e, GuiHistoryManager.GuiState me) {
        if (e.getClick().isRightClick()) {
            GuiHistoryManager.get().push(player, me);
            ShopGui.confirmBuy(player, entry.id, 1);
        } else if (Items.buy(player, entry, 1)) {
            gui.getInventory().setItem(EcoGui.HEAD, EcoGui.balanceHead(player));
        }
    }

    // ================================================================== catalog

    private static final class Catalog extends EcoGui {
        private final String category;
        private final int page;
        private final int maxPages;
        private final List<EconomyData.ShopEntry> pageEntries;

        static Catalog create(Player player, String category, int requestedPage) {
            List<EconomyData.ShopEntry> list = new ArrayList<>();
            for (EconomyData.ShopEntry entry : EconomyData.all()) {
                if (entry.buyable && matchesCategory(entry, category)) list.add(entry);
            }
            list.sort(Comparator.comparing(e -> e.name));

            int maxPages = Math.max(1, (list.size() + CATALOG_PER_PAGE - 1) / CATALOG_PER_PAGE);
            int page = Math.max(1, Math.min(requestedPage, maxPages));
            int start = (page - 1) * CATALOG_PER_PAGE;
            List<EconomyData.ShopEntry> slice =
                    new ArrayList<>(list.subList(start, Math.min(start + CATALOG_PER_PAGE, list.size())));
            String title = "§6Buy Catalog §8| §e" + tabName(category) + " §8(" + page + "/" + maxPages + ")";
            return new Catalog(player, title, category, page, maxPages, slice);
        }

        private Catalog(Player player, String title, String category, int page, int maxPages,
                        List<EconomyData.ShopEntry> pageEntries) {
            super(player, 6, title);
            this.category = category;
            this.page = page;
            this.maxPages = maxPages;
            this.pageEntries = pageEntries;

            for (int i = 0; i < TABS.length; i++) {
                boolean active = TABS[i].equals(category);
                inv.setItem(i, item(tabIcon(TABS[i]),
                        (active ? "§a§l▶ " : "§e") + TAB_NAMES[i],
                        active ? "§aCurrently viewing this category" : "§7Click to browse " + TAB_NAMES[i]));
            }
            for (int i = 0; i < pageEntries.size(); i++) {
                inv.setItem(9 + i, shopItem(pageEntries.get(i), true));
            }
            fillEmpty(inv, 9, 45, Material.BLACK_STAINED_GLASS_PANE);

            inv.setItem(BACK, item(Material.ARROW, "§6« Back", "§7Return to the previous screen"));
            inv.setItem(47, item(Material.HOPPER, "§e§lSell Chest", "§7Drop items in, close to sell"));
            if (page > 1) inv.setItem(48, item(Material.YELLOW_STAINED_GLASS_PANE, "§e« Page " + (page - 1), "§7Previous page"));
            inv.setItem(HEAD, balanceHead(player));
            if (page < maxPages) inv.setItem(50, item(Material.LIME_STAINED_GLASS_PANE, "§aPage " + (page + 1) + " »", "§7Next page"));
            inv.setItem(51, item(Material.ENDER_EYE, "§b§lAuction House", "§7Buy from other players"));
            inv.setItem(CLOSE, item(Material.BARRIER, "§c§lClose", "§7Exit the catalog"));
            fillEmpty(inv, 45, 54, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Override
        protected void handle(InventoryClickEvent e, int slot) {
            GuiHistoryManager.GuiState me = new GuiHistoryManager.GuiState("buy_catalog", category, page);
            if (slot < TABS.length) {
                BuyGui.open(player, TABS[slot], 1); // switching tab stays on the same level: no history entry
                return;
            }
            if (slot >= 9 && slot < 45) {
                int idx = slot - 9;
                if (idx < pageEntries.size()) clickItem(this, player, pageEntries.get(idx), e, me);
                return;
            }
            switch (slot) {
                case BACK -> back();
                case 47 -> navigate(me, () -> SellGui.open(player));
                case 48 -> { if (page > 1) BuyGui.open(player, category, page - 1); }
                case 50 -> { if (page < maxPages) BuyGui.open(player, category, page + 1); }
                case 51 -> navigate(me, () -> AuctionGui.open(player, 1));
                case CLOSE -> close();
                default -> {
                }
            }
        }
    }

    // ================================================================== search

    private static final class Search extends EcoGui {
        private final String query;
        private final int page;
        private final int maxPages;
        private final List<EconomyData.ShopEntry> pageEntries;

        static Search create(Player player, String query, int requestedPage) {
            List<EconomyData.ShopEntry> matches = EconomyData.searchBuyable(query);
            int maxPages = Math.max(1, (matches.size() + SEARCH_PER_PAGE - 1) / SEARCH_PER_PAGE);
            int page = Math.max(1, Math.min(requestedPage, maxPages));
            int start = (page - 1) * SEARCH_PER_PAGE;
            List<EconomyData.ShopEntry> slice =
                    new ArrayList<>(matches.subList(start, Math.min(start + SEARCH_PER_PAGE, matches.size())));
            String title = "§6Search: §e" + query + " §8(" + matches.size() + ")";
            if (title.length() > 60) title = title.substring(0, 60);
            return new Search(player, title, query, page, maxPages, slice);
        }

        private Search(Player player, String title, String query, int page, int maxPages,
                       List<EconomyData.ShopEntry> pageEntries) {
            super(player, 6, title);
            this.query = query;
            this.page = page;
            this.maxPages = maxPages;
            this.pageEntries = pageEntries;

            for (int i = 0; i < pageEntries.size(); i++) {
                inv.setItem(i, shopItem(pageEntries.get(i), true));
            }
            fillEmpty(inv, 0, 45, Material.GRAY_STAINED_GLASS_PANE);
            inv.setItem(BACK, item(Material.ARROW, "§c« Back", "§7Return to previous screen"));
            if (page > 1) inv.setItem(48, item(Material.YELLOW_STAINED_GLASS_PANE, "§e« Page " + (page - 1), "§7Previous page"));
            inv.setItem(HEAD, balanceHead(player));
            if (page < maxPages) inv.setItem(50, item(Material.LIME_STAINED_GLASS_PANE, "§aPage " + (page + 1) + " »", "§7Next page"));
            inv.setItem(CLOSE, item(Material.BARRIER, "§cClose", "§7Close this menu"));
            fillEmpty(inv, 45, 54, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Override
        protected void handle(InventoryClickEvent e, int slot) {
            GuiHistoryManager.GuiState me = new GuiHistoryManager.GuiState("buy_search", query, page);
            if (slot < 45) {
                if (slot < pageEntries.size()) clickItem(this, player, pageEntries.get(slot), e, me);
                return;
            }
            switch (slot) {
                case BACK -> back();
                case 48 -> { if (page > 1) openSearch(player, query, page - 1); }
                case 50 -> { if (page < maxPages) openSearch(player, query, page + 1); }
                case CLOSE -> close();
                default -> {
                }
            }
        }
    }

    // ================================================================== tabs

    private static String tabName(String tab) {
        for (int i = 0; i < TABS.length; i++) {
            if (TABS[i].equalsIgnoreCase(tab)) return TAB_NAMES[i];
        }
        return "All Items";
    }

    private static Material tabIcon(String tab) {
        return switch (tab) {
            case "wood" -> Material.OAK_LOG;
            case "stone" -> Material.DEEPSLATE;
            case "ores" -> Material.DIAMOND_ORE;
            case "tools" -> Material.NETHERITE_SWORD;
            case "armor" -> Material.NETHERITE_CHESTPLATE;
            case "food" -> Material.GOLDEN_CARROT;
            case "redstone" -> Material.REPEATER;
            case "misc" -> Material.BEACON;
            default -> Material.COMPASS;
        };
    }

    private static boolean matchesCategory(EconomyData.ShopEntry entry, String category) {
        if ("all".equals(category)) return true;
        String id = entry.id;
        String cat = entry.category;
        return switch (category) {
            case "wood" -> id.contains("oak") || id.contains("spruce") || id.contains("birch") ||
                    id.contains("jungle") || id.contains("acacia") || id.contains("mangrove") ||
                    id.contains("cherry") || id.contains("bamboo") || id.contains("crimson") ||
                    id.contains("warped") || id.contains("wood") || id.contains("log") || id.contains("planks");
            case "stone" -> id.contains("stone") || id.contains("granite") || id.contains("diorite") ||
                    id.contains("andesite") || id.contains("calcite") || id.contains("tuff") ||
                    id.contains("deepslate") || id.contains("sandstone") || id.contains("prismarine") ||
                    id.contains("blackstone") || id.contains("basalt") || id.contains("brick");
            case "ores" -> id.contains("coal") || id.contains("copper") || id.contains("iron") ||
                    id.contains("gold") || id.contains("lapis") || id.contains("diamond") ||
                    id.contains("emerald") || id.contains("netherite") || id.contains("ore") || id.contains("raw_");
            case "tools" -> id.endsWith("_sword") || id.endsWith("_pickaxe") || id.endsWith("_axe") ||
                    id.endsWith("_shovel") || id.endsWith("_hoe") || id.equals("bow") || id.equals("crossbow") ||
                    id.equals("shield") || id.equals("trident") || id.equals("mace");
            case "armor" -> id.endsWith("_helmet") || id.endsWith("_chestplate") ||
                    id.endsWith("_leggings") || id.endsWith("_boots");
            case "food" -> id.contains("apple") || id.contains("bread") || id.contains("meat") ||
                    id.contains("beef") || id.contains("pork") || id.contains("chicken") ||
                    id.contains("fish") || id.contains("carrot") || id.contains("potato") ||
                    id.contains("berry") || id.contains("melon");
            case "redstone" -> id.contains("redstone") || id.contains("piston") || id.contains("observer") ||
                    id.contains("repeater") || id.contains("comparator") || id.contains("dispenser") ||
                    id.contains("dropper") || id.contains("hopper") || id.contains("target");
            case "misc" -> "misc".equals(cat) || "rare".equals(cat) || "end".equals(cat) || "nether".equals(cat);
            default -> cat.equalsIgnoreCase(category);
        };
    }
}
