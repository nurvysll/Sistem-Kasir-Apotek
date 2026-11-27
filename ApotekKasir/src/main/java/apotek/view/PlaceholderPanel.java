package apotek.view;

import apotek.util.Theme;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Panel sementara untuk fitur yang belum dikerjakan. Ganti dengan panel aslinya di MainFrame. */
public class PlaceholderPanel extends JPanel {
    public PlaceholderPanel(String judul, String keterangan) {
        super(new GridBagLayout());
        setBackground(Theme.BG);

        JLabel l1 = new JLabel(judul);
        l1.setFont(Theme.FONT_TITLE);
        l1.setForeground(Theme.TEXT);
        JLabel l2 = new JLabel(keterangan);
        l2.setFont(Theme.FONT);
        l2.setForeground(Color.GRAY);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(l1, c);
        c.gridy = 1;
        add(l2, c);
    }
}
