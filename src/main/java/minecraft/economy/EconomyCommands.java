package minecraft.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EconomyCommands implements CommandExecutor, TabCompleter {

    private static final String ADMIN = "economy.admin";

    private final EconomyPlugin plugin;

    public EconomyCommands(EconomyPlugin plugin) {
        this.plugin = plugin;
    }

    // ------------------------------------------------------------------ helpers

    private static void err(CommandSender s, String msg) {
        s.sendMessage(ChatColor.RED + msg);
    }

    private static Player playerOnly(CommandSender sender) {
        if (sender instanceof Player p) return p;
        err(sender, "Only players can use this command.");
        return null;
    }

    private static long amountOrError(CommandSender s, String raw, boolean allowZero) {
        try {
            return EconomyData.parseAmount(raw, allowZero);
        } catch (NumberFormatException ex) {
            err(s, "Invalid amount '" + raw + "'. Use a whole number, e.g. 500, 2.5k, 1m (max "
                    + EconomyData.formatMoney(EconomyData.maxBalance()) + ").");
            return -1;
        }
    }

    private static UUID playerOrError(CommandSender s, String name) {
        UUID id = EconomyData.findUuid(name);
        if (id == null) err(s, "Unknown player: " + name + " (they must have joined the server before).");
        return id;
    }

    private static void refresh(UUID id) {
        Player p = Bukkit.getPlayer(id);
        if (p != null) EconomyScoreboard.get().update(p);
    }

    // ------------------------------------------------------------------ dispatch

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName().toLowerCase(Locale.ROOT)) {
            case "bal" -> bal(sender, args);
            case "baltop" -> baltop(sender);
            case "pay" -> pay(sender, args);
            case "fine" -> fine(sender, args);
            case "eco" -> eco(sender, args);
            case "shop" -> shop(sender, args);
            case "buy" -> buy(sender, args);
            case "sell" -> sell(sender, args);
            case "ecosb" -> {
                Player p = playerOnly(sender);
                if (p != null) EconomyScoreboard.get().toggle(p);
            }
            case "ah" -> ah(sender, args);
            case "sellaxe" -> sellaxe(sender, args);
            default -> {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------ /bal /baltop

    private void bal(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Player p = playerOnly(sender);
            if (p == null) return;
            long b = EconomyData.getBalance(p);
            p.sendMessage(ChatColor.GRAY + "Your balance: " + ChatColor.GOLD + EconomyData.formatMoney(b)
                    + ChatColor.DARK_GRAY + " (" + Money.exact(b) + ")");
            return;
        }
        if (!sender.hasPermission("economy.bal.others")) {
            err(sender, "You don't have permission to view other balances.");
            return;
        }
        UUID id = playerOrError(sender, args[0]);
        if (id == null) return;
        long b = EconomyData.getBalance(id);
        sender.sendMessage(ChatColor.AQUA + EconomyData.nameOf(id) + ChatColor.GRAY + "'s balance: " + ChatColor.GOLD
                + EconomyData.formatMoney(b) + ChatColor.DARK_GRAY + " (" + Money.exact(b) + ")");
    }

    private void baltop(CommandSender sender) {
        List<Map.Entry<UUID, Long>> top = EconomyData.top(10);
        sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Top balances");
        int rank = 1;
        for (Map.Entry<UUID, Long> e : top) {
            sender.sendMessage(ChatColor.YELLOW + "" + rank++ + ". " + ChatColor.WHITE + EconomyData.nameOf(e.getKey())
                    + ChatColor.GRAY + " - " + ChatColor.GOLD + EconomyData.formatMoney(e.getValue()));
        }
    }

    // ------------------------------------------------------------------ /pay

    private void pay(CommandSender sender, String[] args) {
        Player payer = playerOnly(sender);
        if (payer == null) return;
        if (args.length < 2) {
            err(sender, "Usage: /pay <player> <amount>");
            return;
        }
        UUID targetId = playerOrError(sender, args[0]);
        if (targetId == null) return;
        if (targetId.equals(payer.getUniqueId())) {
            err(sender, "You can't pay yourself.");
            return;
        }
        long amount = amountOrError(sender, args[1], false);
        if (amount < 0) return;

        if (!EconomyData.withdraw(payer.getUniqueId(), amount)) {
            err(sender, "Not enough money! Need " + EconomyData.formatMoney(amount)
                    + ", have " + EconomyData.formatMoney(EconomyData.getBalance(payer)) + ".");
            return;
        }
        EconomyData.deposit(targetId, amount);

        String targetName = EconomyData.nameOf(targetId);
        payer.sendMessage(ChatColor.GRAY + "Sent " + ChatColor.GOLD + EconomyData.formatMoney(amount)
                + ChatColor.GRAY + " to " + ChatColor.AQUA + targetName + ChatColor.GRAY + ".");
        Player target = Bukkit.getPlayer(targetId);
        if (target != null) {
            target.sendMessage(ChatColor.GRAY + "Received " + ChatColor.GOLD + EconomyData.formatMoney(amount)
                    + ChatColor.GRAY + " from " + ChatColor.AQUA + payer.getName() + ChatColor.GRAY + ".");
        }
    }

    // ------------------------------------------------------------------ /fine

    private void fine(CommandSender sender, String[] args) {
        if (!sender.hasPermission(ADMIN)) {
            err(sender, "You do not have permission for this command.");
            return;
        }
        if (args.length < 2) {
            err(sender, "Usage: /fine <player> <amount> [reason]");
            return;
        }
        UUID id = playerOrError(sender, args[0]);
        if (id == null) return;
        long amount = amountOrError(sender, args[1], false);
        if (amount < 0) return;
        String reason = args.length > 2 ? String.join(" ", Arrays.copyOfRange(args, 2, args.length)) : null;

        EconomyData.forceWithdraw(id, amount); // may leave the player in debt
        String name = EconomyData.nameOf(id);
        sender.sendMessage(ChatColor.YELLOW + "Fined " + name + " " + EconomyData.formatMoney(amount)
                + (reason != null ? " for: " + reason : "") + ". New balance: "
                + EconomyData.formatMoney(EconomyData.getBalance(id)) + ".");
        Player target = Bukkit.getPlayer(id);
        if (target != null) {
            target.sendMessage(ChatColor.RED + "You were fined " + EconomyData.formatMoney(amount)
                    + (reason != null ? " for: " + reason : "") + ".");
        }
        plugin.getLogger().info(sender.getName() + " fined " + name + " " + amount + (reason != null ? " (" + reason + ")" : ""));
    }

    // ------------------------------------------------------------------ /eco

    private void eco(CommandSender sender, String[] args) {
        if (!sender.hasPermission(ADMIN)) {
            err(sender, "You do not have permission for admin commands.");
            return;
        }
        if (args.length == 0) {
            err(sender, "Usage: /eco <give|take|set|reset|reload> [player] [amount]");
            return;
        }
        String sub = args[0].toLowerCase(Locale.ROOT);

        if (sub.equals("reload")) {
            EconomyData.reload();
            EconomyScoreboard.get().updateAll();
            sender.sendMessage(ChatColor.GREEN + "Economy config and prices reloaded.");
            return;
        }
        if (!List.of("give", "take", "set", "reset").contains(sub)) {
            err(sender, "Unknown subcommand: " + sub);
            return;
        }
        if (args.length < 2 || (!sub.equals("reset") && args.length < 3)) {
            err(sender, "Usage: /eco " + sub + " <player>" + (sub.equals("reset") ? "" : " <amount>"));
            return;
        }
        UUID id = playerOrError(sender, args[1]);
        if (id == null) return;
        String name = EconomyData.nameOf(id);

        switch (sub) {
            case "reset" -> {
                EconomyData.setBalance(id, EconomyData.startingBalance());
                sender.sendMessage(ChatColor.GREEN + "Reset " + name + " to " + EconomyData.formatMoney(EconomyData.startingBalance()) + ".");
            }
            case "give" -> {
                long a = amountOrError(sender, args[2], false);
                if (a < 0) return;
                EconomyData.deposit(id, a);
                sender.sendMessage(ChatColor.GREEN + "Gave " + EconomyData.formatMoney(a) + " to " + name + ".");
            }
            case "take" -> {
                long a = amountOrError(sender, args[2], false);
                if (a < 0) return;
                long cur = EconomyData.getBalance(id);
                EconomyData.setBalance(id, cur > 0 ? Math.max(0, cur - a) : cur); // never creates debt; use /fine for that
                sender.sendMessage(ChatColor.YELLOW + "Took " + EconomyData.formatMoney(Math.max(0, Math.min(a, cur)))
                        + " from " + name + ".");
            }
            case "set" -> {
                long a = amountOrError(sender, args[2], true);
                if (a < 0) return;
                EconomyData.setBalance(id, a);
                sender.sendMessage(ChatColor.GREEN + "Set " + name + "'s balance to " + EconomyData.formatMoney(a) + ".");
            }
            default -> {
            }
        }
        refresh(id);
    }

    // ------------------------------------------------------------------ /shop /buy /sell

    private void shop(CommandSender sender, String[] args) {
        Player p = playerOnly(sender);
        if (p == null) return;
        if (args.length == 0) {
            ShopGui.openMainMenu(p);
            return;
        }
        int page = 1;
        if (args.length > 1) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignored) {
                // keep page 1
            }
        }
        ShopGui.openCategoryPage(p, args[0], page);
    }

    private void buy(CommandSender sender, String[] args) {
        Player p = playerOnly(sender);
        if (p == null) return;
        if (args.length == 0) {
            BuyGui.open(p, "all", 1);
            return;
        }
        int amount = 1;
        String query = args[0];
        if (args.length > 1) {
            try {
                amount = Integer.parseInt(args[args.length - 1]);
                query = String.join(" ", Arrays.copyOfRange(args, 0, args.length - 1));
            } catch (NumberFormatException ex) {
                query = String.join(" ", args);
            }
        }
        try {
            EconomyData.ShopEntry entry = EconomyData.fuzzyMatch(query);
            if (entry == null) {
                err(p, "Unknown item: " + query);
                return;
            }
            ShopGui.confirmBuy(p, entry.id, amount);
        } catch (EconomyData.AmbiguousMatchException ex) {
            p.sendMessage(ChatColor.YELLOW + "Several items match '" + query + "' (" + ex.getMatches().size() + "). Pick one:");
            BuyGui.openSearch(p, query);
        }
    }

    private void sell(CommandSender sender, String[] args) {
        Player p = playerOnly(sender);
        if (p == null) return;
        if (args.length == 0) {
            SellGui.open(p);
            return;
        }
        String first = args[0].toLowerCase(Locale.ROOT);
        Items.SaleResult result;
        switch (first) {
            case "hand" -> {
                ItemStack hand = p.getInventory().getItemInMainHand();
                EconomyData.ShopEntry e = Items.sellEntry(hand);
                if (e == null) {
                    err(p, "The shop doesn't buy the item in your hand.");
                    return;
                }
                long earned = e.sell * hand.getAmount();
                int count = hand.getAmount();
                p.getInventory().setItemInMainHand(null);
                EconomyData.deposit(p.getUniqueId(), earned);
                result = new Items.SaleResult(count, earned);
            }
            case "all" -> result = Items.sellFromInventory(p, s -> true, Integer.MAX_VALUE);
            default -> {
                EconomyData.ShopEntry e = EconomyData.get(first);
                if (e == null || !e.sellable()) {
                    err(p, "The shop doesn't buy: " + args[0]);
                    return;
                }
                int limit = Integer.MAX_VALUE;
                if (args.length > 1 && !args[1].equalsIgnoreCase("all")) {
                    try {
                        limit = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ex) {
                        err(p, "Invalid amount.");
                        return;
                    }
                    if (limit <= 0) {
                        err(p, "Amount must be greater than zero.");
                        return;
                    }
                }
                Material mat = e.material;
                result = Items.sellFromInventory(p, s -> s.getType() == mat, limit);
            }
        }
        if (result.items() == 0) {
            err(p, "You have nothing the shop will buy.");
        } else {
            p.sendMessage(ChatColor.GREEN + "Sold " + result.items() + " item" + (result.items() == 1 ? "" : "s")
                    + " for " + ChatColor.GOLD + EconomyData.formatMoney(result.earned()) + ChatColor.GREEN + ".");
        }
    }

    // ------------------------------------------------------------------ /ah

    private void ah(CommandSender sender, String[] args) {
        Player p = playerOnly(sender);
        if (p == null) return;
        String sub = args.length == 0 ? "open" : args[0].toLowerCase(Locale.ROOT);
        switch (sub) {
            case "open", "browse" -> AuctionGui.open(p, 1);
            case "collect", "claim" -> plugin.auctions().collect(p);
            case "sell" -> {
                if (args.length < 2) {
                    err(p, "Usage: /ah sell <price>  (hold the item you want to sell)");
                    return;
                }
                long price = amountOrError(p, args[1], false);
                if (price < 0) return;
                plugin.auctions().create(p, price);
            }
            default -> err(p, "Usage: /ah [open|sell <price>|collect]");
        }
    }

    // ------------------------------------------------------------------ /sellaxe

    private void sellaxe(CommandSender sender, String[] args) {
        if (!sender.hasPermission(ADMIN)) {
            err(sender, "You do not have permission for this command.");
            return;
        }
        String sub = args.length == 0 ? "apply" : args[0].toLowerCase(Locale.ROOT);
        switch (sub) {
            case "apply" -> {
                Player p = playerOnly(sender);
                if (p == null) return;
                ItemStack hand = p.getInventory().getItemInMainHand();
                if (!SellAxe.isAxe(hand)) {
                    err(p, "Hold an axe to turn it into a Sell Axe (or use /sellaxe give <player>).");
                    return;
                }
                SellAxe.apply(hand);
                p.getInventory().setItemInMainHand(hand);
                p.sendMessage(ChatColor.GREEN + "Your axe is now a Sell Axe.");
            }
            case "give" -> {
                Player target;
                if (args.length > 1) {
                    target = Bukkit.getPlayerExact(args[1]);
                    if (target == null) {
                        err(sender, "Player not online: " + args[1]);
                        return;
                    }
                } else {
                    target = playerOnly(sender);
                    if (target == null) return;
                }
                Items.give(target, SellAxe.create(Material.NETHERITE_AXE), 1);
                sender.sendMessage(ChatColor.GREEN + "Gave a Sell Axe to " + target.getName() + ".");
                if (target != sender) target.sendMessage(ChatColor.GREEN + "You received a Sell Axe!");
            }
            default -> err(sender, "Usage: /sellaxe [apply | give [player]]");
        }
    }

    // ------------------------------------------------------------------ tab completion

    private static List<String> filter(Collection<String> options, String prefix) {
        String p = prefix.toLowerCase(Locale.ROOT);
        List<String> out = new ArrayList<>();
        for (String o : options) {
            if (o.toLowerCase(Locale.ROOT).startsWith(p)) out.add(o);
            if (out.size() >= 50) break;
        }
        return out;
    }

    private static List<String> onlineNames() {
        List<String> names = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) names.add(p.getName());
        return names;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String cmd = command.getName().toLowerCase(Locale.ROOT);
        switch (cmd) {
            case "bal", "pay", "fine" -> {
                if (args.length == 1) {
                    Set<String> names = new LinkedHashSet<>(onlineNames());
                    names.addAll(EconomyData.knownNames());
                    return filter(names, args[0]);
                }
            }
            case "eco" -> {
                if (args.length == 1) return filter(List.of("give", "take", "set", "reset", "reload"), args[0]);
                if (args.length == 2 && !args[0].equalsIgnoreCase("reload")) {
                    Set<String> names = new LinkedHashSet<>(onlineNames());
                    names.addAll(EconomyData.knownNames());
                    return filter(names, args[1]);
                }
            }
            case "buy", "sell" -> {
                if (args.length == 1) {
                    boolean buying = cmd.equals("buy");
                    List<String> ids = new ArrayList<>();
                    if (!buying) ids.addAll(List.of("hand", "all"));
                    for (EconomyData.ShopEntry e : EconomyData.all()) {
                        if (buying ? e.buyable : e.sellable()) ids.add(e.id);
                    }
                    return filter(ids, args[0]);
                }
                if (args.length == 2 && cmd.equals("sell")) return filter(List.of("all", "1", "16", "64"), args[1]);
            }
            case "shop" -> {
                if (args.length == 1) {
                    List<String> cats = new ArrayList<>(List.of("all"));
                    cats.addAll(List.of(ShopGui.CATEGORIES));
                    return filter(cats, args[0]);
                }
            }
            case "ah" -> {
                if (args.length == 1) return filter(List.of("open", "sell", "collect"), args[0]);
            }
            case "sellaxe" -> {
                if (args.length == 1) return filter(List.of("apply", "give"), args[0]);
                if (args.length == 2 && args[0].equalsIgnoreCase("give")) return filter(onlineNames(), args[1]);
            }
            default -> {
            }
        }
        return Collections.emptyList();
    }
}
