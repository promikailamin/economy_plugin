package sellaxeharness;

import minecraft.economy.EconomyData;
import minecraft.economy.EconomyPlugin;
import minecraft.economy.SellAxe;
import minecraft.economy.SellAxeListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.GameMode;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

/**
 * Integration checks for the Sell Axe chest behaviour. Runs on a real Paper server
 * because the double-chest inventory merge is server behaviour, not API logic.
 */
public final class SellAxeHarness extends JavaPlugin {

    private final StringBuilder report = new StringBuilder();
    private int failures = 0;

    @Override
    public void onEnable() {
        try {
            run();
        } catch (Throwable t) {
            failures++;
            report.append("exception: ").append(t).append('\n');
            getLogger().severe("SellAxeHarness crashed: " + t);
            t.printStackTrace();
        }

        report.append('\n').append("failures=").append(failures).append('\n');
        try {
            File dir = new File(getDataFolder().getParentFile(), "SellAxeHarness");
            if (!dir.exists() && !dir.mkdirs()) throw new IllegalStateException("cannot create " + dir);
            Files.write(new File(dir, "report.txt").toPath(),
                    report.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            getLogger().severe("Could not write report: " + e);
        }
        getLogger().info("SellAxeHarness report written, failures=" + failures);
        Bukkit.getScheduler().runTaskLater(this, Bukkit::shutdown, 20L);
    }

    private void run() {
        World world = Bukkit.getWorlds().get(0);
        report.append("Sell Axe chest checks\n");

        checkSingleChest(world);
        checkDoubleChest(world);
        checkTrappedChest(world);
        checkEventPath(world);
    }

    private void checkEventPath(World world) {
        UUID id = UUID.nameUUIDFromBytes("SellAxeHarness".getBytes(StandardCharsets.UTF_8));
        EconomyData.setBalance(id, 0);

        Block chestBlock = world.getBlockAt(30, 100, 0);
        chestBlock.setType(Material.CHEST, false);
        ItemStack diamonds = new ItemStack(Material.DIAMOND, 2);
        long expected = value(diamonds);
        ((Chest) chestBlock.getState()).getInventory().setItem(0, diamonds);

        BlockBreakEvent chestEvent = new BlockBreakEvent(chestBlock, proxyPlayer(SellAxe.create(Material.NETHERITE_AXE), id));
        new SellAxeListener().onBlockBreak(chestEvent);
        boolean chestCancelled = chestEvent.isCancelled();
        boolean chestRemains = chestBlock.getType() == Material.CHEST;
        boolean chestEmptied = ((Chest) chestBlock.getState()).getInventory().getItem(0) == null;
        boolean paid = EconomyData.getBalance(id) == expected;
        if (!chestCancelled) failures++;
        if (!chestRemains) failures++;
        if (!chestEmptied) failures++;
        if (!paid) failures++;
        report.append("event chest cancelled=").append(chestCancelled)
                .append(" remains=").append(chestRemains)
                .append(" emptied=").append(chestEmptied)
                .append(" paid=").append(paid).append(" balance=").append(EconomyData.getBalance(id))
                .append(" expected=").append(expected).append('\n');

        Block stoneBlock = world.getBlockAt(31, 100, 0);
        stoneBlock.setType(Material.STONE, false);
        BlockBreakEvent stoneEvent = new BlockBreakEvent(stoneBlock, proxyPlayer(SellAxe.create(Material.NETHERITE_AXE), id));
        new SellAxeListener().onBlockBreak(stoneEvent);
        boolean stoneNotCancelled = !stoneEvent.isCancelled();
        if (!stoneNotCancelled) failures++;
        report.append("event non-chest normalBreak=").append(stoneNotCancelled).append('\n');

        Block normalChestBlock = world.getBlockAt(32, 100, 0);
        normalChestBlock.setType(Material.CHEST, false);
        ((Chest) normalChestBlock.getState()).getInventory().setItem(0, new ItemStack(Material.DIAMOND, 1));
        BlockBreakEvent normalEvent = new BlockBreakEvent(normalChestBlock,
                proxyPlayer(new ItemStack(Material.NETHERITE_AXE), id));
        new SellAxeListener().onBlockBreak(normalEvent);
        boolean normalNotCancelled = !normalEvent.isCancelled();
        boolean normalKept = ((Chest) normalChestBlock.getState()).getInventory().getItem(0) != null;
        if (!normalNotCancelled) failures++;
        if (!normalKept) failures++;
        report.append("event normalAxe normalBreak=").append(normalNotCancelled)
                .append(" chestKept=").append(normalKept).append('\n');
    }

    private static Player proxyPlayer(ItemStack tool, UUID id) {
        PlayerInventory inventory = (PlayerInventory) Proxy.newProxyInstance(
                SellAxeHarness.class.getClassLoader(),
                new Class<?>[]{PlayerInventory.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("getItemInMainHand")) return tool;
                    return defaultValue(method.getReturnType());
                });
        return (Player) Proxy.newProxyInstance(
                SellAxeHarness.class.getClassLoader(),
                new Class<?>[]{Player.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "getGameMode": return GameMode.SURVIVAL;
                        case "getInventory": return inventory;
                        case "getUniqueId": return id;
                        case "toString": return "SellAxeHarnessPlayer";
                        case "hashCode": return System.identityHashCode(proxy);
                        case "equals": return proxy == args[0];
                        default: return defaultValue(method.getReturnType());
                    }
                });
    }

    private static Object defaultValue(Class<?> type) {
        if (!type.isPrimitive()) return null;
        if (type == boolean.class) return false;
        if (type == char.class) return '\0';
        if (type == byte.class) return (byte) 0;
        if (type == short.class) return (short) 0;
        if (type == int.class) return 0;
        if (type == long.class) return 0L;
        if (type == float.class) return 0f;
        if (type == double.class) return 0d;
        return null;
    }

    private void checkSingleChest(World world) {
        Block block = world.getBlockAt(0, 100, 0);
        block.setType(Material.CHEST, false);
        Chest chest = (Chest) block.getState();
        Inventory inv = chest.getInventory();

        ItemStack diamond = new ItemStack(Material.DIAMOND, 3);
        ItemStack dirt = new ItemStack(Material.DIRT, 10);
        ItemStack unsellable = SellAxe.create(Material.NETHERITE_AXE);
        inv.setItem(0, diamond);
        inv.setItem(1, dirt);
        inv.setItem(2, unsellable);

        long expected = value(diamond) + value(dirt);
        SellAxeListener.SaleResult result = SellAxeListener.sellChestContents(inv);

        boolean soldCount = result.sold() == 13;
        boolean earned = result.earned() == expected;
        boolean soldRemoved = inv.getItem(0) == null && inv.getItem(1) == null;
        boolean kept = inv.getItem(2) != null && SellAxe.isSellAxe(inv.getItem(2));
        boolean chestStillThere = world.getBlockAt(0, 100, 0).getType() == Material.CHEST;
        if (!soldCount) failures++;
        if (!earned) failures++;
        if (!soldRemoved) failures++;
        if (!kept) failures++;
        if (!chestStillThere) failures++;
        report.append("single sold=").append(result.sold())
                .append(" earned=").append(result.earned())
                .append(" expected=").append(expected)
                .append(" soldRemoved=").append(soldRemoved)
                .append(" unsellableKept=").append(kept)
                .append(" chestStillThere=").append(chestStillThere).append('\n');
    }

    private void checkDoubleChest(World world) {
        Block leftBlock = world.getBlockAt(10, 100, 0);
        Block rightBlock = world.getBlockAt(11, 100, 0);
        leftBlock.setType(Material.CHEST, false);
        rightBlock.setType(Material.CHEST, false);
        org.bukkit.block.data.type.Chest leftData =
                (org.bukkit.block.data.type.Chest) leftBlock.getBlockData();
        leftData.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
        leftBlock.setBlockData(leftData, false);
        org.bukkit.block.data.type.Chest rightData =
                (org.bukkit.block.data.type.Chest) rightBlock.getBlockData();
        rightData.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);
        rightBlock.setBlockData(rightData, false);

        Chest left = (Chest) leftBlock.getState();
        Inventory combined = left.getInventory();

        ItemStack diamond = new ItemStack(Material.DIAMOND, 4);
        ItemStack dirt = new ItemStack(Material.DIRT, 6);
        combined.setItem(0, diamond);
        combined.setItem(27, dirt);

        long expected = value(diamond) + value(dirt);
        SellAxeListener.SaleResult result = SellAxeListener.sellChestContents(combined);

        Inventory leftAfter = ((Chest) world.getBlockAt(10, 100, 0).getState()).getInventory();
        Inventory rightAfter = ((Chest) world.getBlockAt(11, 100, 0).getState()).getInventory();
        boolean combinedSize = combined.getSize() == 54;
        boolean soldCount = result.sold() == 10;
        boolean earned = result.earned() == expected;
        boolean bothCleared = leftAfter.getItem(0) == null && rightAfter.getItem(27) == null;
        boolean blocksStillThere = world.getBlockAt(10, 100, 0).getType() == Material.CHEST
                && world.getBlockAt(11, 100, 0).getType() == Material.CHEST;
        if (!combinedSize) failures++;
        if (!soldCount) failures++;
        if (!earned) failures++;
        if (!bothCleared) failures++;
        if (!blocksStillThere) failures++;
        report.append("double combinedSize=").append(combined.getSize())
                .append(" sold=").append(result.sold())
                .append(" earned=").append(result.earned())
                .append(" expected=").append(expected)
                .append(" bothCleared=").append(bothCleared)
                .append(" blocksStillThere=").append(blocksStillThere).append('\n');
    }

    private void checkTrappedChest(World world) {
        Block block = world.getBlockAt(20, 100, 0);
        block.setType(Material.TRAPPED_CHEST, false);
        Chest chest = (Chest) block.getState();
        ItemStack dirt = new ItemStack(Material.DIRT, 5);
        chest.getInventory().setItem(0, dirt);

        long expected = value(dirt);
        SellAxeListener.SaleResult result = SellAxeListener.sellChestContents(chest.getInventory());
        boolean soldCount = result.sold() == 5;
        boolean earned = result.earned() == expected;
        boolean cleared = chest.getInventory().getItem(0) == null;
        if (!soldCount) failures++;
        if (!earned) failures++;
        if (!cleared) failures++;
        report.append("trapped sold=").append(result.sold())
                .append(" earned=").append(result.earned())
                .append(" expected=").append(expected)
                .append(" cleared=").append(cleared).append('\n');
    }

    private static long value(ItemStack stack) {
        EconomyData.ShopEntry entry = EconomyPlugin.get() == null ? null : minecraft.economy.Items.sellEntry(stack);
        return entry == null ? 0 : entry.sell * stack.getAmount();
    }
}
