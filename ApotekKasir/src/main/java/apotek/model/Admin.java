package apotek.model;

public class Admin extends User {
    public Admin(int id, String username, String namaLengkap) {
        super(id, username, namaLengkap);
    }

    @Override
    public String getRole() { return "ADMIN"; }
}
