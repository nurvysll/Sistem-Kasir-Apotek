package apotek.model;

import apotek.exception.ValidasiException;

public class PembayaranCash extends Pembayaran {
    private final double bayar;

    public PembayaranCash(double total, double bayar) throws ValidasiException {
        super(total);
        if (bayar < total) throw new ValidasiException("Uang yang dibayarkan kurang dari total belanja");
        this.bayar = bayar;
    }

    @Override public String getMetode() { return "CASH"; }
    @Override public double getBayar() { return bayar; }
    @Override public double hitungKembalian() { return bayar - total; }
}
