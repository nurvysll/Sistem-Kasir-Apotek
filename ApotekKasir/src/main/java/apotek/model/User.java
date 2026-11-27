package apotek.model;

/** Kelas induk pengguna. Turunannya: Admin dan Kasir (inheritance + polymorphism). */
public abstract class User {
    private final int id;
    private final String username;
    private final String namaLengkap;

    protected User(int id, String username, String namaLengkap) {
        this.id = id;
        this.username = username;
        this.namaLengkap = namaLengkap;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getNamaLengkap() { return namaLengkap; }

    /** "ADMIN" atau "KASIR" (sama dengan nilai kolom users.role). */
    public abstract String getRole();
}
