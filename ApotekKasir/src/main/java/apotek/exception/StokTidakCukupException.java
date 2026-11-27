package apotek.exception;

import apotek.model.Obat;

public class StokTidakCukupException extends Exception {
    public StokTidakCukupException(Obat obat, int diminta) {
        super("Stok " + obat.getNamaObat() + " tidak cukup (tersedia " + obat.getStok()
                + ", diminta " + diminta + ")");
    }
}
