package apotek.view;

import apotek.model.User;
import apotek.util.Session;
import apotek.util.Theme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/** Dashboard utama. Tab yang tampil bergantung pada role (Admin / Kasir). */
public class MainFrame extends JFrame {

    public MainFrame(User user) {
        super("Sistem Kasir Apotek");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buatHeader(user), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(Theme.FONT_BOLD);

        if ("ADMIN".equals(user.getRole())) {
            tabs.addTab("Data Obat", new ObatPanel(true));
            // TODO anggota 3: ganti dengan panel CRUD kategori & supplier
            tabs.addTab("Kategori & Supplier", new PlaceholderPanel("Kategori & Supplier", "Belum dikerjakan"));
            // TODO anggota 5/4: ganti dengan panel laporan penjualan
            tabs.addTab("Laporan Penjualan", new PlaceholderPanel("Laporan Penjualan", "Belum dikerjakan"));
        } else {
            tabs.addTab("Cari Obat", new ObatPanel(false));
            // TODO: ganti dengan panel transaksi, riwayat & struk, dan retur
            tabs.addTab("Transaksi", new PlaceholderPanel("Transaksi", "Belum dikerjakan"));
            tabs.addTab("Riwayat & Struk", new PlaceholderPanel("Riwayat & Struk", "Belum dikerjakan"));
            tabs.addTab("Retur", new PlaceholderPanel("Retur Obat", "Belum dikerjakan"));
        }
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buatHeader(User user) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        JLabel judul = new JLabel("Apotek Kasir");
        judul.setFont(Theme.FONT_TITLE.deriveFont(20f));
        judul.setForeground(Color.WHITE);
        header.add(judul, BorderLayout.WEST);

        JPanel kanan = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        kanan.setOpaque(false);
        JLabel info = new JLabel(user.getNamaLengkap() + "  (" + user.getRole() + ")");
        info.setFont(Theme.FONT_BOLD);
        info.setForeground(Color.WHITE);
        JButton btnLogout = Theme.button("Logout", Theme.PRIMARY_DARK);
        btnLogout.addActionListener(e -> {
            Session.clear();
            dispose();
            new LoginView().setVisible(true);
        });
        kanan.add(info);
        kanan.add(btnLogout);
        header.add(kanan, BorderLayout.EAST);
        return header;
    }
}
