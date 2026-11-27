package apotek.model;

public class Kasir extends User {
    public Kasir(int id, String username, String namaLengkap) {
        super(id, username, namaLengkap);
    }

    @Override
    public String getRole() { return "KASIR"; }
}
