package apotek.model;

public class DetailTransaksi {
    private int id;
    private String kodeObat;
    private String namaObat;
    private int jumlah;
    private double hargaSatuan;

    public DetailTransaksi() { }

    public DetailTransaksi(String kodeObat, String namaObat, int jumlah, double hargaSatuan) {
        this.kodeObat = kodeObat;
        this.namaObat = namaObat;
        this.jumlah = jumlah;
        this.hargaSatuan = hargaSatuan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getKodeObat() { return kodeObat; }
    public String getNamaObat() { return namaObat; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public double getHargaSatuan() { return hargaSatuan; }
    public double getSubtotal() { return jumlah * hargaSatuan; }
}
