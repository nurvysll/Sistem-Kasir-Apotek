package apotek.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

/** Warna, font, dan gaya komponen supaya tampilan seragam di semua layar. */
public final class Theme {
    public static final Color PRIMARY = new Color(0x0F766E);
    public static final Color PRIMARY_DARK = new Color(0x115E59);
    public static final Color BG = new Color(0xF1F5F9);
    public static final Color TEXT = new Color(0x0F172A);
    public static final Color MUTED = new Color(0x64748B);
    public static final Color DANGER = new Color(0xDC2626);
    public static final Color INFO = new Color(0x2563EB);
    public static final Color ROW_EXPIRED = new Color(0xFEE2E2);
    public static final Color ROW_LOW = new Color(0xFEF3C7);

    public static final Font FONT = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 24);

    private Theme() { }

    /** Tombol berwarna. BasicButtonUI dipakai agar warna tetap tampil di semua Look and Feel. */
    public static JButton button(String text, Color bg) {
        JButton b = new JButton(text);
        b.setUI(new BasicButtonUI());
        b.setOpaque(true);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(FONT_BOLD);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(8, 18, 8, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
