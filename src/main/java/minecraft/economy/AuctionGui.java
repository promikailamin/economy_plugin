package minecraft.economy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Auction House browser and purchase confirmation. */
public final class AuctionGui {

    private static final int PER_PAGE = 45;

    private AuctionGui() {
    }

    public static void open(Player player, int page) {
        Browse.create(player, page).open();
    }

    private static AuctionHouse house() {
        return EconomyPlugin.get().auctions();
    }

    private static void sortListings(List<AuctionHouse.Listing> listings, AuctionHouse.SortMode mode) {
        Comparator<AuctionHouse.Listing> newestFirst =
                (a, b) -> Long.compare(b.created, a.created);
        switch (mode) {
            case LOW_TO_HIGH -> listings.sort(Comparator
                    .comparingLong((AuctionHouse.Listing l) -> l.price)
                    .thenComparing(newestFirst));
            case HIGH_TO_LOW -> listings.sort(Comparator
                    .comparingLong((AuctionHouse.Listing l) -> l.price).reversed()
                    .thenComparing(newestFirst));
            default -> listings.sort(Comparator
                    .comparing((AuctionHouse.Listing l) -> sortGroup(l.item))
                    .thenComparingLong(l -> l.price)
                    .thenComparing(newestFirst));
        }
    }

    /** Groups equipment by family (all swords together) and everything else by item/category. */
    private static String sortGroup(ItemStack item) {
        String name = item.getType().name();
        if (name.endsWith("_SWORD")) return "01 Sword";
        if (name.endsWith("_PICKAXE")) return "02 Pickaxe";
        if (name.endsWith("_AXE")) return "03 Axe";
        if (name.endsWith("_SHOVEL")) return "04 Shovel";
        if (name.endsWith("_HOE")) return "05 Hoe";
        if (name.endsWith("_HELMET")) return "06 Helmet";
        if (name.endsWith("_CHESTPLATE")) return "07 Chestplate";
        if (name.endsWith("_LEGGINGS")) return "08 Leggings";
        if (name.endsWith("_BOOTS")) return "09 Boots";
        if (name.endsWith("_SHULKER_BOX")) return "10 Shulker Box";
        if (name.equals("ENCHANTED_BOOK")) return "11 Enchanted Book";
        EconomyData.ShopEntry entry = EconomyData.get(name);
        String category = entry == null ? "99" : entry.category;
        return "90 " + category + " " + name;
    }

    /** Copy of the listed item with auction details appended to its lore. */
    private static ItemStack display(AuctionHouse.Listing l, boolean own) {
        ItemStack stack = l.item.clone();
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore() == null ? new ArrayList<>() : new ArrayList<>(meta.getLore());
            lore.add("§8-------------------------");
            lore.add("§7Price: §6" + EconomyData.formatMoney(l.price));
            lore.add("§7Seller: §e" + l.sellerName);
            lore.add("§7Expires in: §f" + AuctionHouse.timeLeft(l.expires));
            lore.add(own ? "§c▶ Click to cancel your listing" : "§a▶ Click to buy");
            meta.setLore(lore);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    // ================================================================== browse

    private static final class Browse extends EcoGui {
        private final int page;
        private final int maxPages;
        private final List<AuctionHouse.Listing> pageListings;

        static Browse create(Player player, int requestedPage) {
            List<AuctionHouse.Listing> all = house().all();
            sortListings(all, EconomyData.auctionSort(player.getUniqueId()));
            int maxPages = Math.max(1, (all.size() + PER_PAGE - 1) / PER_PAGE);
            int page = Math.max(1, Math.min(requestedPage, maxPages));
            int start = (page - 1) * PER_PAGE;
            List<AuctionHouse.Listing> slice =
                    new ArrayList<>(all.subList(start, Math.min(start + PER_PAGE, all.size())));
            return new Browse(player, "§bAuction House §8(" + page + "/" + maxPages + ")", page, maxPages, slice);
        }

        private Browse(Player player, String title, int page, int maxPages, List<AuctionHouse.Listing> pageListings) {
            super(player, 6, title);
            this.page = page;
            this.maxPages = maxPages;
            this.pageListings = pageListings;

            for (int i = 0; i < pageListings.size(); i++) {
                AuctionHouse.Listing l = pageListings.get(i);
                inv.setItem(i, display(l, l.seller.equals(player.getUniqueId())));
            }
            inv.setItem(BACK, item(Material.ARROW, "§c« Back", "§7Return to previous screen"));
            if (page > 1) inv.setItem(46, item(Material.YELLOW_STAINED_GLASS_PANE, "§e« Prev Page", "§7Page " + (page - 1)));

            int claims = house().claimCount(player.getUniqueId());
            inv.setItem(47, item(Material.CHEST, "§6Collect items",
                    "§7Waiting for you: §f" + claims,
                    "§7(expired or cancelled listings)",
                    claims > 0 ? "§a▶ Click to collect" : "§8Nothing to collect"));
            inv.setItem(HEAD, balanceHead(player));
            inv.setItem(48, item(Material.ENDER_CHEST, "§d§lMystery Shop",
                    "§7Bet money on a random generated crate", "§7Worse or best - click to open"));
            inv.setItem(50, item(Material.BEACON, "§b§lSmart Market",
                    "§7AI stock, demand and restock status", "§7Click to inspect"));
            AuctionHouse.SortMode sort = EconomyData.auctionSort(player.getUniqueId());
            inv.setItem(51, item(Material.COMPARATOR, "§b§lFilter: §f" + sort.label(),
                    "§7" + sort.description(),
                    "§8Modes: Sorted, Low to High, High to Low",
                    "§a▶ Click to cycle filter mode",
                    "§7Your choice is saved for this account."));
            if (page < maxPages) inv.setItem(52, item(Material.LIME_STAINED_GLASS_PANE, "§aNext Page »", "§7Page " + (page + 1)));
            inv.setItem(CLOSE, item(Material.BARRIER, "§cClose", "§7Close this menu"));
            fillEmpty(inv, 45, 54, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Override
        protected void handle(InventoryClickEvent e, int slot) {
            GuiHistoryManager.GuiState me = new GuiHistoryManager.GuiState("auction", null, page);
            if (slot < PER_PAGE) {
                if (slot >= pageListings.size()) return;
                AuctionHouse.Listing l = pageListings.get(slot);
                if (l.seller.equals(player.getUniqueId())) {
                    house().cancel(player, l.id);
                    AuctionGui.open(player, page);
                } else {
                    navigate(me, () -> new Confirm(player, l).open());
                }
                return;
            }
            switch (slot) {
                case BACK -> back();
                case 46 -> { if (page > 1) AuctionGui.open(player, page - 1); }
                case 47 -> {
                    house().collect(player);
                    AuctionGui.open(player, page);
                }
                case 48 -> navigate(me, () -> MysteryGui.open(player));
                case 50 -> navigate(me, () -> AiMarketGui.open(player));
                case 51 -> {
                    AuctionHouse.SortMode next = EconomyData.auctionSort(player.getUniqueId()).next();
                    EconomyData.setAuctionSort(player.getUniqueId(), next);
                    EconomyData.save();
                    AuctionGui.open(player, 1);
                }
                case 52 -> { if (page < maxPages) AuctionGui.open(player, page + 1); }
                case CLOSE -> close();
                default -> {
                }
            }
        }
    }

    // ================================================================== confirm

    private static final class Confirm extends EcoGui {
        private final AuctionHouse.Listing listing;

        Confirm(Player player, AuctionHouse.Listing listing) {
            super(player, 3, "§bConfirm purchase");
            this.listing = listing;
            inv.setItem(13, display(listing, false));
            inv.setItem(11, item(Material.EMERALD_BLOCK, "§a✔ Buy for " + EconomyData.formatMoney(listing.price),
                    "§7Your balance: §6" + EconomyData.formatMoney(EconomyData.getBalance(player))));
            inv.setItem(15, item(Material.REDSTONE_BLOCK, "§cCancel", "§7Go back"));
            fillEmpty(inv, 0, 27, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Override
        protected void handle(InventoryClickEvent e, int slot) {
            if (slot == 11) {
                house().buy(player, listing.id);
                back();
            } else if (slot == 15) {
                back();
            }
        }
    }
}
