package apotek.controller;

import apotek.dao.UserDAO;
import apotek.exception.ValidasiException;
import apotek.model.User;
import apotek.util.Session;
import java.sql.SQLException;

public class LoginController {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) throws ValidasiException {
        if (username == null || username.isBlank() || password == null || password.isEmpty()) {
            throw new ValidasiException("Username dan password wajib diisi");
        }
        try {
            User user = userDAO.login(username.trim(), password);
            if (user == null) throw new ValidasiException("Username atau password salah");
            Session.setCurrent(user);
            return user;
        } catch (SQLException e) {
            throw new ValidasiException("Tidak bisa terhubung ke database. Pastikan MySQL di XAMPP sudah jalan "
                    + "dan database apotek_db sudah di-import.\n(" + e.getMessage() + ")");
        }
    }

    public void logout() {
        Session.clear();
    }
}
