package apotek.view;

import apotek.controller.ObatController;
import apotek.controller.ReferensiController;
import apotek.exception.ValidasiException;
import apotek.model.Kategori;
import apotek.model.Obat;
import apotek.model.Supplier;
import apotek.util.Format;
import apotek.util.Theme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Data obat. bisaEdit = true (Admin): ada form tambah / ubah / hapus.
 * bisaEdit = false (Kasir): hanya melihat dan mencari.
 */
public class ObatPanel extends JPanel {
    private final ObatController controller = new ObatController();
    private final ReferensiController referensi = new ReferensiController();
    private final boolean bisaEdit;

    private List<Obat> data = new ArrayList<>();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Kode", "Nama Obat", "Kategori", "Supplier", "Harga", "Stok", "Kedaluwarsa"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);

    private final JTextField txtCari = new JTextField(22);
    private final JTextField txtKode = new JTextField();
    private final JTextField txtNama = new JTextField();
    private final JComboBox<Kategori> cmbKategori = new JComboBox<>();
    private final JComboBox<Supplier> cmbSupplier = new JComboBox<>();
    private final JTextField txtHarga = new JTextField();
    private final JTextField txtStok = new JTextField();
    private final JTextField txtTgl = new JTextField();

    public ObatPanel(boolean bisaEdit) {
        this.bisaEdit = bisaEdit;
        setLayout(new BorderLayout(0, 10));
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        add(buatBarCari(), BorderLayout.NORTH);
        add(buatTabel(), BorderLayout.CENTER);
        if (bisaEdit) add(buatForm(), BorderLayout.SOUTH);

        muatReferensi();
        muatData(null);
    }

    // ---------- Susunan tampilan ----------

    private JPanel buatBarCari() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        p.setOpaque(false);
        JLabel l = new JLabel("Cari (nama / kode):");
        l.setFont(Theme.FONT_BOLD);
        JButton btnCari = Theme.button("Cari", Theme.PRIMARY);
        JButton btnSemua = Theme.button("Tampilkan Semua", Theme.MUTED);
        btnCari.addActionListener(e -> muatData(txtCari.getText()));
        txtCari.addActionListener(e -> muatData(txtCari.getText()));
        btnSemua.addActionListener(e -> {
            txtCari.setText("");
            muatData(null);
        });

        JLabel legenda = new JLabel("   Merah = kedaluwarsa   |   Kuning = stok < 10");
        legenda.setForeground(Theme.MUTED);
        p.add(l);
        p.add(txtCari);
        p.add(btnCari);
        p.add(btnSemua);
        p.add(legenda);
        return p;
    }

    private JScrollPane buatTabel() {
        table.setFont(Theme.FONT);
        table.setRowHeight(26);
        table.setAutoCreateRowSorter(true);
        table.setSelectionBackground(new Color(0x99F6E4));
        table.setSelectionForeground(Theme.TEXT);
        table.getTableHeader().setFont(Theme.FONT_BOLD);
        table.setDefaultRenderer(Object.class, new BarisRenderer());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && bisaEdit) isiFormDariBaris();
        });
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(Color.WHITE);
        return sp;
    }

    private JPanel buatForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Form Data Obat"),
                BorderFactory.createEmptyBorder(6, 8, 8, 8)));

        txtTgl.setToolTipText("Format yyyy-MM-dd, contoh 2027-12-31");

        tambahField(form, "Kode Obat", txtKode, 0, 0);
        tambahField(form, "Nama Obat", txtNama, 2, 0);
        tambahField(form, "Kategori", cmbKategori, 4, 0);
        tambahField(form, "Supplier", cmbSupplier, 0, 1);
        tambahField(form, "Harga (Rp)", txtHarga, 2, 1);
        tambahField(form, "Stok", txtStok, 4, 1);
        tambahField(form, "Kedaluwarsa (yyyy-MM-dd)", txtTgl, 0, 2);

        JButton btnTambah = Theme.button("Tambah", Theme.PRIMARY);
        JButton btnUbah = Theme.button("Ubah", Theme.INFO);
        JButton btnHapus = Theme.button("Hapus", Theme.DANGER);
        JButton btnReset = Theme.button("Reset", Theme.MUTED);
        btnTambah.addActionListener(e -> aksiTambah());
        btnUbah.addActionListener(e -> aksiUbah());
        btnHapus.addActionListener(e -> aksiHapus());
        btnReset.addActionListener(e -> resetForm());

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        tombol.setOpaque(false);
        tombol.add(btnTambah);
        tombol.add(btnUbah);
        tombol.add(btnHapus);
        tombol.add(btnReset);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        form.add(tombol, c);
        return form;
    }

    private void tambahField(JPanel p, String label, Component field, int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.gridx = x;
        c.gridy = y;
        c.anchor = GridBagConstraints.WEST;
        JLabel l = new JLabel(label);
        l.setFont(Theme.FONT_BOLD);
        p.add(l, c);
        c.gridx = x + 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(field, c);
    }

    // ---------- Data ----------

    private void muatReferensi() {
        try {
            cmbKategori.removeAllItems();
            for (Kategori k : referensi.semuaKategori()) cmbKategori.addItem(k);
            cmbSupplier.removeAllItems();
            for (Supplier s : referensi.semuaSupplier()) cmbSupplier.addItem(s);
        } catch (ValidasiException e) {
            tampilkanError(e);
        }
    }

    private void muatData(String keyword) {
        try {
            data = controller.cari(keyword);
        } catch (ValidasiException e) {
            tampilkanError(e);
            return;
        }
        model.setRowCount(0);
        for (Obat o : data) {
            model.addRow(new Object[]{
                    o.getKodeObat(), o.getNamaObat(), o.getNamaKategori(),
                    o.getNamaSupplier() == null ? "-" : o.getNamaSupplier(),
                    Format.rupiah(o.getHarga()), o.getStok(), o.getTglKadaluwarsa()});
        }
    }

    private Obat obatTerpilih() {
        int baris = table.getSelectedRow();
        if (baris < 0) return null;
        return data.get(table.convertRowIndexToModel(baris));
    }

    private void isiFormDariBaris() {
        Obat o = obatTerpilih();
        if (o == null) return;
        txtKode.setText(o.getKodeObat());
        txtKode.setEditable(false);                // kode = primary key, tidak boleh diubah
        txtNama.setText(o.getNamaObat());
        txtHarga.setText(String.valueOf((long) o.getHarga()));
        txtStok.setText(String.valueOf(o.getStok()));
        txtTgl.setText(o.getTglKadaluwarsa().toString());
        pilihKategori(o.getIdKategori());
        pilihSupplier(o.getIdSupplier());
    }

    private void pilihKategori(int id) {
        for (int i = 0; i < cmbKategori.getItemCount(); i++) {
            if (cmbKategori.getItemAt(i).getId() == id) cmbKategori.setSelectedIndex(i);
        }
    }

    private void pilihSupplier(int id) {
        for (int i = 0; i < cmbSupplier.getItemCount(); i++) {
            if (cmbSupplier.getItemAt(i).getId() == id) cmbSupplier.setSelectedIndex(i);
        }
    }

    private void resetForm() {
        table.clearSelection();
        txtKode.setEditable(true);
        for (JTextField f : new JTextField[]{txtKode, txtNama, txtHarga, txtStok, txtTgl}) f.setText("");
        if (cmbKategori.getItemCount() > 0) cmbKategori.setSelectedIndex(0);
        if (cmbSupplier.getItemCount() > 0) cmbSupplier.setSelectedIndex(0);
    }

    // ---------- Aksi tombol ----------

    private void aksiTambah() {
        try {
            controller.tambah(txtKode.getText(), txtNama.getText(),
                    (Kategori) cmbKategori.getSelectedItem(), (Supplier) cmbSupplier.getSelectedItem(),
                    txtHarga.getText(), txtStok.getText(), txtTgl.getText());
            muatData(null);
            resetForm();
            JOptionPane.showMessageDialog(this, "Data obat berhasil ditambahkan");
        } catch (ValidasiException e) {
            tampilkanError(e);
        }
    }

    private void aksiUbah() {
        if (obatTerpilih() == null) {
            JOptionPane.showMessageDialog(this, "Pilih obat di tabel dulu", "Ubah", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            controller.ubah(txtKode.getText(), txtNama.getText(),
                    (Kategori) cmbKategori.getSelectedItem(), (Supplier) cmbSupplier.getSelectedItem(),
                    txtHarga.getText(), txtStok.getText(), txtTgl.getText());
            muatData(null);
            resetForm();
            JOptionPane.showMessageDialog(this, "Data obat berhasil diubah");
        } catch (ValidasiException e) {
            tampilkanError(e);
        }
    }

    private void aksiHapus() {
        Obat o = obatTerpilih();
        if (o == null) {
            JOptionPane.showMessageDialog(this, "Pilih obat di tabel dulu", "Hapus", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this, "Hapus obat " + o.getNamaObat() + "?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;
        try {
            controller.hapus(o.getKodeObat());
            muatData(null);
            resetForm();
        } catch (ValidasiException e) {
            tampilkanError(e);
        }
    }

    private void tampilkanError(ValidasiException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Perhatian", JOptionPane.WARNING_MESSAGE);
    }

    // ---------- Warna baris: merah = kedaluwarsa, kuning = stok menipis ----------

    private class BarisRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object value, boolean selected,
                                                       boolean focus, int row, int col) {
            Component c = super.getTableCellRendererComponent(t, value, selected, focus, row, col);
            if (!selected) {
                Obat o = data.get(t.convertRowIndexToModel(row));
                if (o.isKadaluwarsa()) c.setBackground(Theme.ROW_EXPIRED);
                else if (o.getStok() < 10) c.setBackground(Theme.ROW_LOW);
                else c.setBackground(Color.WHITE);
                c.setForeground(Theme.TEXT);
            }
            return c;
        }
    }
}
