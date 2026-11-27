package apotek.model;

public class PembayaranQris extends Pembayaran {
    public PembayaranQris(double total) {
        super(total);
    }

    @Override public String getMetode() { return "QRIS"; }
    @Override public double getBayar() { return total; }          // pas, sesuai tagihan
    @Override public double hitungKembalian() { return 0; }        // QRIS tidak ada kembalian
}
