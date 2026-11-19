package minecraft.economy;

import org.bukkit.entity.Player;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages per-player GUI navigation history so that the "Back" button can
 * return to the previous economy screen with all its data intact.
 *
 * <p>Each player has an independent history stack. When the stack reaches
 * {@link #MAX_HISTORY} entries the oldest entry is discarded.</p>
 */
public final class GuiHistoryManager {

    public static final int MAX_HISTORY = 10;

    private static final GuiHistoryManager INSTANCE = new GuiHistoryManager();
    private final Map<UUID, ArrayDeque<GuiState>> history = new ConcurrentHashMap<>();

    public static GuiHistoryManager get() {
        return INSTANCE;
    }

    private GuiHistoryManager() {
    }

    public void push(Player player, GuiState state) {
        history.computeIfAbsent(player.getUniqueId(), k -> new ArrayDeque<>())
               .addLast(state);
        var stack = history.get(player.getUniqueId());
        while (stack.size() > MAX_HISTORY) {
            stack.removeFirst();
        }
    }

    public GuiState pop(Player player) {
        var stack = history.get(player.getUniqueId());
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        return stack.removeLast();
    }

    public boolean canGoBack(Player player) {
        var stack = history.get(player.getUniqueId());
        return stack != null && !stack.isEmpty();
    }

    public void clear(Player player) {
        history.remove(player.getUniqueId());
    }

    /** Re-opens the screen described by a history entry. */
    public static void restore(Player player, GuiState s) {
        switch (s.type()) {
            case "category" -> ShopGui.openCategoryPage(player, s.param1(), s.param2());
            case "confirm_buy" -> ShopGui.confirmBuy(player, s.param1(), s.param2());
            case "buy_catalog" -> BuyGui.open(player, s.param1(), s.param2());
            case "buy_search" -> BuyGui.openSearch(player, s.param1(), s.param2());
            case "auction" -> AuctionGui.open(player, s.param2());
            default -> ShopGui.openMainMenu(player);
        }
    }

    /**
     * A captured screen state: enough information to reconstruct the screen.
     */
    public record GuiState(String type, String param1, int param2, String param3) {
        public GuiState(String type) {
            this(type, null, 0, null);
        }

        public GuiState(String type, String param1, int param2) {
            this(type, param1, param2, null);
        }
    }
}
