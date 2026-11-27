package apotek.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Koneksi MySQL (XAMPP). Sesuaikan USER / PASS bila MySQL-mu pakai password. */
public final class DBConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/apotek_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jakarta";
    private static final String USER = "root";
    private static final String PASS = "";

    private DBConnection() { }

    /** Tiap pemanggil wajib menutupnya (pakai try-with-resources). */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
