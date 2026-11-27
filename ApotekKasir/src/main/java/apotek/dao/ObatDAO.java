package apotek.dao;

import apotek.model.Obat;
import apotek.util.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ObatDAO implements BaseDAO<Obat, String> {

    private static final String SELECT =
            "SELECT o.*, k.nama_kategori, s.nama AS nama_supplier "
            + "FROM obat o JOIN kategori k ON o.id_kategori = k.id "
            + "LEFT JOIN supplier s ON o.id_supplier = s.id ";

    private Obat map(ResultSet rs) throws SQLException {
        Obat o = new Obat();
        o.setKodeObat(rs.getString("kode_obat"));
        o.setNamaObat(rs.getString("nama_obat"));
        o.setIdKategori(rs.getInt("id_kategori"));
        o.setIdSupplier(rs.getInt("id_supplier"));          // 0 bila NULL
        o.setNamaKategori(rs.getString("nama_kategori"));
        o.setNamaSupplier(rs.getString("nama_supplier"));
        o.setHarga(rs.getDouble("harga"));
        o.setStok(rs.getInt("stok"));
        o.setTglKadaluwarsa(rs.getDate("tgl_kadaluwarsa").toLocalDate());
        return o;
    }

    @Override
    public List<Obat> findAll() throws SQLException {
        return query(SELECT + "ORDER BY o.nama_obat");
    }

    /** Pencarian berdasarkan nama atau kode obat. */
    public List<Obat> cari(String keyword) throws SQLException {
        return query(SELECT + "WHERE o.nama_obat LIKE ? OR o.kode_obat LIKE ? ORDER BY o.nama_obat",
                "%" + keyword + "%", "%" + keyword + "%");
    }

    private List<Obat> query(String sql, String... params) throws SQLException {
        List<Obat> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setString(i + 1, params[i]);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public Obat findById(String kode) throws SQLException {
        List<Obat> hasil = query(SELECT + "WHERE o.kode_obat = ?", kode);
        return hasil.isEmpty() ? null : hasil.get(0);
    }

    @Override
    public void insert(Obat o) throws SQLException {
        String sql = "INSERT INTO obat (kode_obat, nama_obat, id_kategori, id_supplier, harga, stok, tgl_kadaluwarsa) "
                + "VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, o.getKodeObat());
            isi(ps, o, 2);
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Obat o) throws SQLException {
        String sql = "UPDATE obat SET nama_obat=?, id_kategori=?, id_supplier=?, harga=?, stok=?, tgl_kadaluwarsa=? "
                + "WHERE kode_obat=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            isi(ps, o, 1);
            ps.setString(7, o.getKodeObat());
            ps.executeUpdate();
        }
    }

    /** Mengisi 6 parameter: nama, kategori, supplier, harga, stok, tgl kadaluwarsa mulai dari indeks awal. */
    private void isi(PreparedStatement ps, Obat o, int awal) throws SQLException {
        ps.setString(awal, o.getNamaObat());
        ps.setInt(awal + 1, o.getIdKategori());
        if (o.getIdSupplier() > 0) ps.setInt(awal + 2, o.getIdSupplier());
        else ps.setNull(awal + 2, Types.INTEGER);
        ps.setDouble(awal + 3, o.getHarga());
        ps.setInt(awal + 4, o.getStok());
        ps.setDate(awal + 5, Date.valueOf(o.getTglKadaluwarsa()));
    }

    @Override
    public void delete(String kode) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM obat WHERE kode_obat = ?")) {
            ps.setString(1, kode);
            ps.executeUpdate();
        }
    }

    /**
     * Mengurangi stok memakai koneksi yang dipinjam dari luar, supaya bisa dipakai di dalam
     * satu database transaction saat menyimpan penjualan (commit / rollback).
     *
     * @return false bila stok tidak cukup (tidak ada baris yang berubah)
     */
    public boolean kurangiStok(Connection con, String kode, int jumlah) throws SQLException {
        String sql = "UPDATE obat SET stok = stok - ? WHERE kode_obat = ? AND stok >= ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setString(2, kode);
            ps.setInt(3, jumlah);
            return ps.executeUpdate() == 1;
        }
    }

    /** Menambah stok kembali (dipakai untuk retur). */
    public void tambahStok(Connection con, String kode, int jumlah) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("UPDATE obat SET stok = stok + ? WHERE kode_obat = ?")) {
            ps.setInt(1, jumlah);
            ps.setString(2, kode);
            ps.executeUpdate();
        }
    }
}
