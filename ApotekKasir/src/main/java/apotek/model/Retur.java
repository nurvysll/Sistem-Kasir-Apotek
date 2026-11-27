package apotek.model;

import java.time.LocalDateTime;

public class Retur {
    private int id;
    private int idDetailTransaksi;   // harus item dari transaksi yang tercatat
    private int jumlah;
    private String alasan;
    private LocalDateTime tanggal = LocalDateTime.now();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdDetailTransaksi() { return idDetailTransaksi; }
    public void setIdDetailTransaksi(int idDetailTransaksi) { this.idDetailTransaksi = idDetailTransaksi; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public String getAlasan() { return alasan; }
    public void setAlasan(String alasan) { this.alasan = alasan; }
    public LocalDateTime getTanggal() { return tanggal; }
    public void setTanggal(LocalDateTime tanggal) { this.tanggal = tanggal; }
}
