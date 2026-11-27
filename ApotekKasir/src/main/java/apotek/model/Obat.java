package apotek.model;

import apotek.exception.ObatKadaluwarsaException;
import apotek.exception.StokTidakCukupException;
import java.time.LocalDate;

public class Obat {
    private String kodeObat;
    private String namaObat;
    private int idKategori;
    private int idSupplier;
    private String namaKategori;   // hasil JOIN, hanya untuk tampilan
    private String namaSupplier;   // hasil JOIN, hanya untuk tampilan
    private double harga;
    private int stok;
    private LocalDate tglKadaluwarsa;

    public String getKodeObat() { return kodeObat; }
    public void setKodeObat(String kodeObat) { this.kodeObat = kodeObat; }
    public String getNamaObat() { return namaObat; }
    public void setNamaObat(String namaObat) { this.namaObat = namaObat; }
    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) { this.idKategori = idKategori; }
    public int getIdSupplier() { return idSupplier; }
    public void setIdSupplier(int idSupplier) { this.idSupplier = idSupplier; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    public String getNamaSupplier() { return namaSupplier; }
    public void setNamaSupplier(String namaSupplier) { this.namaSupplier = namaSupplier; }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    public LocalDate getTglKadaluwarsa() { return tglKadaluwarsa; }
    public void setTglKadaluwarsa(LocalDate tglKadaluwarsa) { this.tglKadaluwarsa = tglKadaluwarsa; }

    public boolean isKadaluwarsa() {
        return tglKadaluwarsa.isBefore(LocalDate.now());
    }

    /**
     * Dipakai saat transaksi: obat kedaluwarsa atau stok kurang tidak boleh dijual.
     * (Anggota yang mengerjakan Transaksi tinggal memanggil method ini.)
     */
    public void validasiUntukDijual(int jumlah) throws ObatKadaluwarsaException, StokTidakCukupException {
        if (isKadaluwarsa()) throw new ObatKadaluwarsaException(this);
        if (stok < jumlah) throw new StokTidakCukupException(this, jumlah);
    }
}
