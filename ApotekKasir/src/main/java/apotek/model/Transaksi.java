package apotek.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Satu transaksi penjualan = keranjang (ArrayList detail) + pembayaran. */
public class Transaksi {
    private int id;
    private LocalDateTime tanggal = LocalDateTime.now();
    private int idKasir;
    private final List<DetailTransaksi> items = new ArrayList<>();
    private Pembayaran pembayaran;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getTanggal() { return tanggal; }
    public void setTanggal(LocalDateTime tanggal) { this.tanggal = tanggal; }
    public int getIdKasir() { return idKasir; }
    public void setIdKasir(int idKasir) { this.idKasir = idKasir; }
    public List<DetailTransaksi> getItems() { return items; }
    public Pembayaran getPembayaran() { return pembayaran; }
    public void setPembayaran(Pembayaran pembayaran) { this.pembayaran = pembayaran; }

    public void tambahItem(DetailTransaksi d) { items.add(d); }

    public double getTotal() {
        double total = 0;
        for (DetailTransaksi d : items) total += d.getSubtotal();
        return total;
    }
}
