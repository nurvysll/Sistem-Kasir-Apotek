package apotek.view;

import apotek.controller.LoginController;
import apotek.exception.ValidasiException;
import apotek.model.User;
import apotek.util.Theme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame {
    private final LoginController controller = new LoginController();
    private final JTextField txtUsername = new JTextField(18);
    private final JPasswordField txtPassword = new JPasswordField(18);

    public LoginView() {
        super("Login - Sistem Kasir Apotek");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        add(buatPanelKiri(), BorderLayout.WEST);
        add(buatPanelForm(), BorderLayout.CENTER);
    }

    private JPanel buatPanelKiri() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Theme.PRIMARY);
        p.setPreferredSize(new Dimension(300, 0));

        JLabel judul = new JLabel("Apotek Kasir");
        judul.setFont(Theme.FONT_TITLE);
        judul.setForeground(Color.WHITE);
        JLabel sub = new JLabel("Kelola obat, transaksi, dan laporan");
        sub.setFont(Theme.FONT);
        sub.setForeground(new Color(0xCCFBF1));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        p.add(judul, c);
        c.gridy = 1;
        c.insets = new Insets(8, 0, 0, 0);
        p.add(sub, c);
        return p;
    }

    private JPanel buatPanelForm() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);

        JLabel judul = new JLabel("Masuk");
        judul.setFont(Theme.FONT_TITLE);
        judul.setForeground(Theme.TEXT);

        for (JTextField f : new JTextField[]{txtUsername, txtPassword}) {
            f.setFont(Theme.FONT);
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0xCBD5E1)),
                    BorderFactory.createEmptyBorder(7, 9, 7, 9)));
        }

        JButton btnLogin = Theme.button("Login", Theme.PRIMARY);
        btnLogin.addActionListener(e -> prosesLogin());
        getRootPane().setDefaultButton(btnLogin);   // tekan Enter = klik Login

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 5, 0);

        c.gridy = 0;
        c.insets = new Insets(0, 0, 18, 0);
        p.add(judul, c);
        c.insets = new Insets(5, 0, 2, 0);
        c.gridy = 1;
        p.add(label("Username"), c);
        c.gridy = 2;
        p.add(txtUsername, c);
        c.gridy = 3;
        p.add(label("Password"), c);
        c.gridy = 4;
        p.add(txtPassword, c);
        c.gridy = 5;
        c.insets = new Insets(18, 0, 0, 0);
        p.add(btnLogin, c);
        return p;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.FONT_BOLD);
        l.setForeground(Theme.MUTED);
        return l;
    }

    private void prosesLogin() {
        try {
            User user = controller.login(txtUsername.getText(), new String(txtPassword.getPassword()));
            new MainFrame(user).setVisible(true);
            dispose();
        } catch (ValidasiException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login gagal", JOptionPane.WARNING_MESSAGE);
            txtPassword.setText("");
        }
    }
}
