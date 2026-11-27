package apotek.exception;

import apotek.model.Obat;

public class ObatKadaluwarsaException extends Exception {
    public ObatKadaluwarsaException(Obat obat) {
        super(obat.getNamaObat() + " sudah kedaluwarsa (" + obat.getTglKadaluwarsa() + "), tidak boleh dijual");
    }
}
