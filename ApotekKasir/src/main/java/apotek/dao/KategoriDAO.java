package apotek.dao;

import apotek.model.Kategori;
import apotek.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO implements BaseDAO<Kategori, Integer> {

    @Override
    public List<Kategori> findAll() throws SQLException {
        List<Kategori> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id, nama_kategori FROM kategori ORDER BY nama_kategori");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new Kategori(rs.getInt(1), rs.getString(2)));
        }
        return list;
    }

    @Override
    public Kategori findById(Integer id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id, nama_kategori FROM kategori WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? new Kategori(rs.getInt(1), rs.getString(2)) : null;
            }
        }
    }

    @Override
    public void insert(Kategori k) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO kategori (nama_kategori) VALUES (?)")) {
            ps.setString(1, k.getNamaKategori());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Kategori k) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE kategori SET nama_kategori = ? WHERE id = ?")) {
            ps.setString(1, k.getNamaKategori());
            ps.setInt(2, k.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM kategori WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
