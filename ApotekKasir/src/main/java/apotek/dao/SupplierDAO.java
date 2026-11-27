package apotek.dao;

import apotek.model.Supplier;
import apotek.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO implements BaseDAO<Supplier, Integer> {

    private Supplier map(ResultSet rs) throws SQLException {
        return new Supplier(rs.getInt("id"), rs.getString("nama"), rs.getString("telepon"), rs.getString("alamat"));
    }

    @Override
    public List<Supplier> findAll() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM supplier ORDER BY nama");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    @Override
    public Supplier findById(Integer id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM supplier WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    @Override
    public void insert(Supplier s) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO supplier (nama, telepon, alamat) VALUES (?,?,?)")) {
            ps.setString(1, s.getNama());
            ps.setString(2, s.getTelepon());
            ps.setString(3, s.getAlamat());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Supplier s) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE supplier SET nama=?, telepon=?, alamat=? WHERE id=?")) {
            ps.setString(1, s.getNama());
            ps.setString(2, s.getTelepon());
            ps.setString(3, s.getAlamat());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM supplier WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
