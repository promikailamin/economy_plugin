package minecraft.economy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Pure money formatting / parsing helpers (no Bukkit dependency).
 */
public final class Money {

    private static final String[] SUFFIXES = {"K", "M", "B", "T", "Q"};

    private Money() {
    }

    /** Short form: 999 -> $999, 1500 -> $1.5K, 2_500_000 -> $2.5M. Negative values get a leading '-'. */
    public static String format(long amount) {
        if (amount == Long.MIN_VALUE) amount = Long.MIN_VALUE + 1;
        if (amount < 0) return "-" + format(-amount);
        if (amount < 1_000) return "$" + amount;

        double v = amount;
        int i = -1;
        while (v >= 1000 && i < SUFFIXES.length - 1) {
            v /= 1000.0;
            i++;
        }
        String s = String.format(Locale.US, "%.1f", v);
        if (s.equals("1000.0") && i < SUFFIXES.length - 1) { // rounding spill, e.g. 999.96K
            s = "1";
            i++;
        } else if (s.endsWith(".0")) {
            s = s.substring(0, s.length() - 2);
        }
        return "$" + s + SUFFIXES[i];
    }

    /** Full form with thousands separators: $1,234,567 */
    public static String exact(long amount) {
        String n = NumberFormat.getIntegerInstance(Locale.US).format(Math.abs(amount));
        return (amount < 0 ? "-$" : "$") + n;
    }

    /**
     * Parses "1500", "1,500", "$1.5k", "2m", "0.5b" ... into whole coins (fractions are floored).
     *
     * @throws NumberFormatException when invalid, too large, or (unless allowZero) not positive
     */
    public static long parse(String input, long max, boolean allowZero) {
        if (input == null) throw new NumberFormatException("empty");
        String t = input.trim().toLowerCase(Locale.ROOT).replace(",", "").replace("_", "");
        if (t.startsWith("$")) t = t.substring(1);
        if (t.isEmpty()) throw new NumberFormatException("empty");

        long mult = 1;
        char last = t.charAt(t.length() - 1);
        if (last == 'k') mult = 1_000L;
        else if (last == 'm') mult = 1_000_000L;
        else if (last == 'b') mult = 1_000_000_000L;
        else if (last == 't') mult = 1_000_000_000_000L;
        if (mult > 1) t = t.substring(0, t.length() - 1);

        BigDecimal v = new BigDecimal(t).multiply(BigDecimal.valueOf(mult)).setScale(0, RoundingMode.DOWN);
        if (v.signum() < 0 || (v.signum() == 0 && !allowZero)) throw new NumberFormatException("not positive");
        if (v.compareTo(BigDecimal.valueOf(max)) > 0) throw new NumberFormatException("too large");
        return v.longValueExact();
    }
}
