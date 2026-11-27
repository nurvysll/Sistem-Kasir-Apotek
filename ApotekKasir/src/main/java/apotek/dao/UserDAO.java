package apotek.dao;

import apotek.model.Admin;
import apotek.model.Kasir;
import apotek.model.User;
import apotek.util.DBConnection;
import apotek.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /** @return User (Admin / Kasir) bila cocok, null bila username/password salah. */
    public User login(String username, String passwordPlain) throws SQLException {
        String sql = "SELECT id, username, nama_lengkap, role FROM users WHERE username = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, PasswordUtil.sha256(passwordPlain));
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                int id = rs.getInt("id");
                String nama = rs.getString("nama_lengkap");
                return "ADMIN".equals(rs.getString("role"))
                        ? new Admin(id, rs.getString("username"), nama)
                        : new Kasir(id, rs.getString("username"), nama);
            }
        }
    }
}
