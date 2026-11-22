package marketharness;

import minecraft.economy.AiItemFactory;
import minecraft.economy.AnvilBypassListener;
import minecraft.economy.AuctionAi;
import minecraft.economy.AuctionHouse;
import minecraft.economy.EconomyData;
import minecraft.economy.EconomyPlugin;
import minecraft.economy.MysteryShop;
import minecraft.economy.SpecialItems;
import minecraft.economy.SpecialToolListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public final class MarketHarness extends JavaPlugin {

    private static final List<String> ARCHETYPES = List.of(
            "junk", "supplies", "tools", "armor", "enchanted_tools",
            "enchanted_armor", "weapons", "crates", "books", "treasures", "special"
    );

    @Override
    public void onEnable() {
        StringBuilder report = new StringBuilder();
        int failures = 0;
        Random rng = new Random(20261119);

        report.append("ai market scale checks\n");
        AuctionAi ai = EconomyPlugin.get().ai();
        if (ai == null) {
            failures++;
            report.append("AI market missing\n");
        } else {
            report.append("target=").append(ai.targetListings())
                    .append(" active=").append(ai.activeListings()).append('\n');
            if (ai.targetListings() < 1000) failures++;
            if (ai.activeListings() < Math.min(ai.targetListings(), 1000)) failures++;
            if (ai.activeListings() > 1000) failures++;
        }

        AuctionHouse house = EconomyPlugin.get().auctions();
        report.append("\nAI relic and duration checks\n");
        if (ai != null) {
            var relics = ai.activeRelicCounts();
            int effectArmor = relics.getOrDefault("effect_armor", 0);
            int effectSwords = relics.getOrDefault("effect_sword", 0);
            int enchantArmor = relics.getOrDefault("enchant_armor", 0);
            int superTools = relics.getOrDefault("super_tool", 0);
            int enchantBooks = relics.getOrDefault("enchant_book", 0);
            int keepHelmets = relics.getOrDefault("keep_helmet", 0);
            report.append("effect_armor=").append(effectArmor)
                    .append(" effect_swords=").append(effectSwords)
                    .append(" enchant_armor=").append(enchantArmor)
                    .append(" super_tools=").append(superTools)
                    .append(" enchant_books=").append(enchantBooks)
                    .append(" keep_helmets=").append(keepHelmets).append('\n');
            if (effectArmor < 10 || effectArmor > 30) failures++;
            if (effectSwords < 10 || effectSwords > 30) failures++;
            if (enchantArmor < 10 || enchantArmor > 30) failures++;
            if (superTools < 10 || superTools > 30) failures++;
            if (enchantBooks < 20 || enchantBooks > 50) failures++;
            if (keepHelmets < 0 || keepHelmets > 1) failures++;
        }
        for (AuctionHouse.Listing listing : house.all()) {
            if (!listing.synthetic) continue;
            long hours = (listing.expires - listing.created) / 3_600_000L;
            if (hours < 1 || hours > 5) {
                failures++;
                report.append("bad AI duration=").append(hours).append("h id=").append(listing.id).append('\n');
            }
        }

        report.append("\nprice checks\n");
        for (String id : List.of("dirt", "coarse_dirt", "mud", "clay")) {
            EconomyData.ShopEntry entry = EconomyData.get(id);
            if (entry == null || entry.buy != 10 || entry.sell != 8) failures++;
            report.append(id).append(" buy=").append(entry == null ? -1 : entry.buy)
                    .append(" sell=").append(entry == null ? -1 : entry.sell).append('\n');
        }
        EconomyData.ShopEntry enchanting = EconomyData.get("enchanting_table");
        if (enchanting == null || enchanting.buy != 100_000) failures++;
        EconomyData.ShopEntry shell = EconomyData.get("shulker_shell");
        if (shell == null || shell.buy != 5_000) failures++;
        for (String id : List.of("shulker_box", "white_shulker_box", "black_shulker_box")) {
            EconomyData.ShopEntry entry = EconomyData.get(id);
            if (entry == null || entry.buy != 10_000) failures++;
        }
        report.append("enchanting_table buy=").append(enchanting == null ? -1 : enchanting.buy)
                .append(" shulker_shell buy=").append(shell == null ? -1 : shell.buy).append('\n');

        report.append("\nhead trade checks\n");
        for (String id : List.of("player_head", "creeper_head", "piglin_head",
                "skeleton_skull", "wither_skeleton_skull", "zombie_head")) {
            EconomyData.ShopEntry entry = EconomyData.get(id);
            boolean ok = entry != null && !entry.buyable && entry.sellable();
            if (!ok) failures++;
            report.append(id).append(" buyable=").append(entry != null && entry.buyable)
                    .append(" sellable=").append(entry != null && entry.sellable())
                    .append('\n');
        }
        report.append("\nAI buyer checks\n");
        FileConfiguration cfg = EconomyPlugin.get().getConfig();
        cfg.set("auction.ai.buyers.enabled", true);
        cfg.set("auction.ai.buyers.chance-per-listing-per-tick", 1.0D);
        cfg.set("auction.ai.buyers.max-per-tick", 5);
        cfg.set("auction.ai.buyers.min-age-minutes", 0);
        cfg.set("auction.ai.buyers.min-value", 1);
        cfg.set("auction.ai.buyers.max-price", 1_000_000_000L);
        cfg.set("auction.ai.buyers.max-value-ratio", 1.75D);
        if (ai != null) ai.reload();

        UUID seller = UUID.nameUUIDFromBytes("HarnessSeller".getBytes(StandardCharsets.UTF_8));
        EconomyData.setBalance(seller, 0);
        EconomyData.setAuctionSort(seller, AuctionHouse.SortMode.HIGH_TO_LOW);
        if (EconomyData.auctionSort(seller) != AuctionHouse.SortMode.HIGH_TO_LOW) failures++;
        EconomyData.setAuctionSort(seller, AuctionHouse.SortMode.SORTED);
        ItemStack diamonds = new ItemStack(Material.DIAMOND, 8);
        long fairValue = AiItemFactory.value(diamonds);
        long fairPrice = Math.max(1, fairValue);
        long fairId = house.addListing(seller, "HarnessSeller", diamonds, fairPrice);
        int syntheticBefore = house.syntheticCount();
        int bought = ai == null ? 0 : ai.tickBuyers();
        long earned = EconomyData.getBalance(seller);
        long expectedTax = (long) Math.floor(fairPrice * cfg.getDouble("auction.tax-percent", 5) / 100.0);
        long expectedEarned = fairPrice - expectedTax;
        boolean fairSold = house.get(fairId) == null && bought >= 1;
        boolean paid = earned == expectedEarned;
        if (!fairSold) failures++;
        if (!paid) failures++;
        if (house.syntheticCount() != syntheticBefore) failures++;
        long aiBoughtTotal = ai == null ? 0 : ai.totalBoughtByAi();
        long aiSpentTotal = ai == null ? 0 : ai.totalSpentByAi();
        if (aiBoughtTotal < 1 || aiSpentTotal < fairPrice) failures++;
        report.append("fair listing value=").append(fairValue).append(" price=").append(fairPrice)
                .append(" bought=").append(bought).append(" sellerEarned=").append(earned)
                .append(" expected=").append(expectedEarned)
                .append(" aiBoughtTotal=").append(aiBoughtTotal)
                .append(" aiSpentTotal=").append(aiSpentTotal).append('\n');

        long overpricedId = house.addListing(seller, "HarnessSeller", new ItemStack(Material.DIAMOND, 8),
                Math.max(1, fairValue * 3));
        int overpricedBought = ai == null ? 0 : ai.tickBuyers();
        boolean overpricedKept = house.get(overpricedId) != null;
        if (!overpricedKept || overpricedBought != 0) failures++;
        report.append("overpriced listing kept=").append(overpricedKept)
                .append(" bought=").append(overpricedBought).append('\n');

        cfg.set("auction.ai.buyers.enabled", false);
        if (ai != null) ai.reload();
        long disabledId = house.addListing(seller, "HarnessSeller", new ItemStack(Material.DIAMOND, 8),
                Math.max(1, fairValue));
        int disabledBought = ai == null ? 0 : ai.tickBuyers();
        boolean disabledKept = house.get(disabledId) != null;
        if (!disabledKept || disabledBought != 0) failures++;
        report.append("disabled buyers kept=").append(disabledKept)
                .append(" bought=").append(disabledBought).append('\n');

        report.append('\n');
        report.append("generation checks\n");
        for (AiItemFactory.Quality quality : AiItemFactory.Quality.values()) {
            for (String archetype : ARCHETYPES) {
                int min = Integer.MAX_VALUE;
                int max = 0;
                long total = 0;
                for (int i = 0; i < 40; i++) {
                    ItemStack item = AiItemFactory.generate(rng, archetype, quality);
                    if (item == null || item.getType().isAir()) {
                        failures++;
                        continue;
                    }
                    long value = AiItemFactory.value(item);
                    if (value <= 0) failures++;
                    min = Math.min(min, item.getAmount());
                    max = Math.max(max, item.getAmount());
                    total += value;
                }
                report.append(archetype).append(' ').append(quality)
                        .append(" avgValue=").append(total / 40)
                        .append(" amount=").append(min).append('-').append(max).append('\n');
            }
        }

        report.append("\nkeep inventory helmet checks\n");
        for (int i = 0; i < 50; i++) {
            ItemStack helmet = SpecialItems.keepInventoryHelmet(rng);
            if (!SpecialItems.isKeepInventoryHelmet(helmet)) failures++;
            if (SpecialItems.value(helmet) < 500_000_000L) failures++;
            if (SpecialItems.serial(helmet) <= 0) failures++;
        }
        report.append("generated 50 helmets, failures so far=").append(failures).append('\n');

        report.append("\neffect relic checks\n");
        Set<String> swordEffects = new HashSet<>();
        Set<String> armorEffects = new HashSet<>();
        for (int i = 0; i < 120; i++) {
            ItemStack sword = SpecialItems.effectSword(rng);
            if (SpecialItems.kind(sword) != SpecialItems.Kind.EFFECT_SWORD) failures++;
            if (SpecialItems.effect(sword) == null) failures++;
            int level = SpecialItems.effectLevel(sword);
            if (level < 1 || level > 4) failures++;
            if (SpecialItems.effect(sword) != null) swordEffects.add(SpecialItems.effect(sword).getName());

            ItemStack armor = SpecialItems.eternalArmor(rng);
            if (SpecialItems.kind(armor) != SpecialItems.Kind.ETERNAL_ARMOR) failures++;
            if (SpecialItems.effect(armor) == null) failures++;
            int armorLevel = SpecialItems.effectLevel(armor);
            if (armorLevel < 1 || armorLevel > 4) failures++;
            if (SpecialItems.effect(armor) != null) armorEffects.add(SpecialItems.effect(armor).getName());
        }
        if (swordEffects.size() < 10) failures++;
        if (armorEffects.size() < 10) failures++;
        report.append("sword effects=").append(swordEffects.size())
                .append(" armor effects=").append(armorEffects.size()).append('\n');

        report.append("\nhigh enchant book checks\n");
        for (int i = 0; i < 200; i++) {
            ItemStack book = SpecialItems.highEnchantBook(rng);
            if (SpecialItems.hasInvalidSingleLevelEnchant(book)) {
                failures++;
                report.append("invalid single-level book: ").append(book).append('\n');
            }
        }
        ItemStack badBook = SpecialItems.highEnchantBook(rng);
        EnchantmentStorageMeta badBookMeta = (EnchantmentStorageMeta) badBook.getItemMeta();
        badBookMeta.addStoredEnchant(Enchantment.MENDING, 15, true);
        badBook.setItemMeta(badBookMeta);
        if (!SpecialItems.hasInvalidSingleLevelEnchant(badBook)) {
            failures++;
            report.append("invalid single-level book was not detected\n");
        }

        report.append("\nanvil bypass checks\n");
        ItemStack protA = new ItemStack(Material.DIAMOND_CHESTPLATE);
        protA.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
        ItemStack protB = new ItemStack(Material.DIAMOND_CHESTPLATE);
        protB.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
        ItemStack protMerged = AnvilBypassListener.merge(protA, protB);
        int protLevel = protMerged == null ? -1 : protMerged.getEnchantmentLevel(Enchantment.PROTECTION);
        if (protLevel != 5) failures++;
        report.append("protection 4 + 4 = ").append(protLevel).append('\n');

        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
        pick.addUnsafeEnchantment(Enchantment.UNBREAKING, 2);
        ItemStack unbreakingBook = enchantedBook(Enchantment.UNBREAKING, 3);
        ItemStack unbreakingMerged = AnvilBypassListener.merge(pick, unbreakingBook);
        int unbreakingLevel = unbreakingMerged == null ? -1
                : unbreakingMerged.getEnchantmentLevel(Enchantment.UNBREAKING);
        if (unbreakingLevel != 3) failures++;
        report.append("unbreaking 2 + 3 = ").append(unbreakingLevel).append('\n');

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack sharp15 = enchantedBook(Enchantment.SHARPNESS, 15);
        ItemStack highMerged = AnvilBypassListener.merge(sword, sharp15);
        int sharpLevel = highMerged == null ? -1 : highMerged.getEnchantmentLevel(Enchantment.SHARPNESS);
        if (sharpLevel != 15) failures++;
        report.append("sharpness book 15 applied = ").append(sharpLevel).append('\n');

        ItemStack mending15 = enchantedBook(Enchantment.MENDING, 15);
        ItemStack mendingMerged = AnvilBypassListener.merge(sword, mending15);
        int mendingLevel = mendingMerged == null ? -1
                : mendingMerged.getEnchantmentLevel(Enchantment.MENDING);
        if (mendingLevel != 15) failures++;
        report.append("mending book 15 applied = ").append(mendingLevel).append('\n');

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addUnsafeEnchantment(Enchantment.INFINITY, 1);
        ItemStack mending = enchantedBook(Enchantment.MENDING, 1);
        ItemStack bowMerged = AnvilBypassListener.merge(bow, mending);
        boolean bowOk = bowMerged != null
                && bowMerged.containsEnchantment(Enchantment.INFINITY)
                && bowMerged.containsEnchantment(Enchantment.MENDING);
        if (!bowOk) failures++;
        report.append("infinity + mending allowed=").append(bowOk).append('\n');

        ItemStack smiteSword = new ItemStack(Material.DIAMOND_SWORD);
        smiteSword.addUnsafeEnchantment(Enchantment.SHARPNESS, 5);
        ItemStack smite = enchantedBook(Enchantment.SMITE, 5);
        ItemStack smiteMerged = AnvilBypassListener.merge(smiteSword, smite);
        boolean smiteOk = smiteMerged != null
                && smiteMerged.containsEnchantment(Enchantment.SHARPNESS)
                && smiteMerged.containsEnchantment(Enchantment.SMITE);
        if (!smiteOk) failures++;
        report.append("sharpness + smite allowed=").append(smiteOk).append('\n');

        ItemStack protBooksA = enchantedBook(Enchantment.PROTECTION, 10);
        ItemStack protBooksB = enchantedBook(Enchantment.PROTECTION, 10);
        ItemStack protBooksMerged = AnvilBypassListener.merge(protBooksA, protBooksB);
        int protBookLevel = protBooksMerged == null ? -1
                : ((EnchantmentStorageMeta) protBooksMerged.getItemMeta())
                        .getStoredEnchantLevel(Enchantment.PROTECTION);
        if (protBookLevel != 11) failures++;
        report.append("protection book 10 + 10 = ").append(protBookLevel).append('\n');

        report.append("\narea miner geometry checks\n");
        for (int size = 3; size <= 5; size++) {
            List<int[]> offsets = SpecialToolListener.areaOffsets(size);
            int expected = size * size * size - 1;
            int min = -(size / 2);
            int max = min + size - 1;
            Set<String> unique = new HashSet<>();
            boolean ok = offsets.size() == expected;
            for (int[] offset : offsets) {
                if (offset.length != 3) ok = false;
                if (offset[0] < min || offset[0] > max
                        || offset[1] < min || offset[1] > max
                        || offset[2] < min || offset[2] > max) ok = false;
                if (offset[0] == 0 && offset[1] == 0 && offset[2] == 0) ok = false;
                unique.add(offset[0] + "," + offset[1] + "," + offset[2]);
            }
            if (unique.size() != expected) ok = false;
            if (!ok) failures++;
            report.append(size).append('x').append(size).append('x').append(size)
                    .append(" blocks=").append(offsets.size())
                    .append(" range=").append(min).append("..").append(max)
                    .append(" ok=").append(ok).append('\n');
        }

        report.append("\nmarket normalisation checks\n");
        UUID helmetSeller = UUID.nameUUIDFromBytes("HelmetSeller".getBytes(StandardCharsets.UTF_8));
        UUID helmetSeller2 = UUID.nameUUIDFromBytes("HelmetSeller2".getBytes(StandardCharsets.UTF_8));
        house.addListing(helmetSeller, "HelmetSeller", SpecialItems.keepInventoryHelmet(rng), 1_000_000L);
        house.addListing(helmetSeller2, "HelmetSeller2", SpecialItems.keepInventoryHelmet(rng), 2_000_000L);
        int duplicateHelmets = house.enforceSingleKeepInventoryHelmet();
        int activeHelmets = 0;
        for (AuctionHouse.Listing listing : house.all()) {
            if (SpecialItems.isKeepInventoryHelmet(listing.item)) activeHelmets++;
        }
        boolean helmetsOk = duplicateHelmets >= 1 && activeHelmets == 1;
        if (!helmetsOk) failures++;
        report.append("duplicate helmets removed=").append(duplicateHelmets)
                .append(" active=").append(activeHelmets).append(" ok=").append(helmetsOk).append('\n');

        int syntheticBeforeTrim = house.syntheticCount();
        for (int i = 0; i < 5; i++) {
            house.addSynthetic(UUID.nameUUIDFromBytes(("TrimSeller" + i).getBytes(StandardCharsets.UTF_8)),
                    "TrimSeller", new ItemStack(Material.DIRT), 10L, "junk", null, 3_600_000L);
        }
        int trimmed = house.trimSyntheticListings(syntheticBeforeTrim);
        boolean trimOk = trimmed == 5 && house.syntheticCount() == syntheticBeforeTrim;
        if (!trimOk) failures++;
        report.append("synthetic trimmed=").append(trimmed)
                .append(" active=").append(house.syntheticCount())
                .append(" expected=").append(syntheticBeforeTrim)
                .append(" ok=").append(trimOk).append('\n');

        report.append("\ncrate payout checks\n");
        MysteryShop shop = EconomyPlugin.get().mystery();
        for (MysteryShop.Tier tier : MysteryShop.Tier.values()) {
            long cost = shop.cost(tier);
            long totalValue = 0;
            int rolls = 0;
            int jackpots = 0;
            long min = Long.MAX_VALUE;
            long max = 0;
            for (int i = 0; i < 4000; i++) {
                double roll = rng.nextDouble();
                double multiplier;
                if (roll < 0.60) multiplier = 0.10 + rng.nextDouble() * 0.40;
                else if (roll < 0.82) multiplier = 0.50 + rng.nextDouble() * 0.45;
                else if (roll < 0.95) multiplier = 0.95 + rng.nextDouble() * 0.75;
                else if (roll < 0.99) multiplier = 1.70 + rng.nextDouble() * 1.30;
                else multiplier = 3.00 + rng.nextDouble() * 4.00;
                boolean jackpot = rng.nextDouble() < tier.jackpotChance() || multiplier >= 4.0;
                if (jackpot) multiplier = Math.max(multiplier, 4.2);
                AiItemFactory.Quality quality = jackpot ? AiItemFactory.Quality.byRank(tier.quality().rank() + 1) : tier.quality();
                List<ItemStack> items = AiItemFactory.lootBundle(
                        rng, quality, Math.round(cost * multiplier * tier.payoutFactor()), jackpot);
                if (items.isEmpty()) {
                    failures++;
                    continue;
                }
                long value = 0;
                for (ItemStack item : items) {
                    long itemValue = AiItemFactory.value(item);
                    if (itemValue <= 0) failures++;
                    value += itemValue;
                }
                totalValue += value;
                rolls++;
                if (jackpot) jackpots++;
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
            long average = totalValue / Math.max(1, rolls);
            report.append(tier.id())
                    .append(" cost=").append(cost)
                    .append(" rolls=").append(rolls)
                    .append(" jackpots=").append(jackpots)
                    .append(" avg=").append(average)
                    .append(" return=").append(Math.round(average * 1000.0 / cost) / 10.0).append('%')
                    .append(" min=").append(min)
                    .append(" max=").append(max)
                    .append('\n');
        }
        report.append("\nfailures=").append(failures).append('\n');

        try {
            File file = new File(getDataFolder(), "report.txt");
            Files.createDirectories(file.getParentFile().toPath());
            Files.writeString(file.toPath(), report.toString(), StandardCharsets.UTF_8);
            getLogger().info("MarketHarness report written, failures=" + failures);
        } catch (Exception ex) {
            getLogger().severe("MarketHarness write failed: " + ex);
        }
        Bukkit.getScheduler().runTaskLater(this, Bukkit::shutdown, 20L * 3);
    }

    private static ItemStack enchantedBook(Enchantment enchantment, int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        book.setItemMeta(meta);
        return book;
    }
}
