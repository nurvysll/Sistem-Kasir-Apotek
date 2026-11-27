package apotek.model;

/** Abstract class pembayaran. Cash menghitung kembalian, Qris tidak (polymorphism). */
public abstract class Pembayaran {
    protected final double total;

    protected Pembayaran(double total) {
        this.total = total;
    }

    public double getTotal() { return total; }

    /** "CASH" atau "QRIS" (sama dengan nilai kolom transaksi.metode). */
    public abstract String getMetode();

    public abstract double getBayar();

    public abstract double hitungKembalian();
}
