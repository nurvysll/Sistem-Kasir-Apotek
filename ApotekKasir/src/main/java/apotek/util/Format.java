package apotek.util;

import java.util.Locale;

public final class Format {
    private static final Locale ID = Locale.forLanguageTag("id-ID");

    private Format() { }

    /** 12000 -> "Rp 12.000" */
    public static String rupiah(double nilai) {
        return String.format(ID, "Rp %,.0f", nilai);
    }
}
