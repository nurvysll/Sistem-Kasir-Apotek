package apotek.exception;

/** Kesalahan input / aturan bisnis yang pesannya boleh langsung ditampilkan ke pengguna. */
public class ValidasiException extends Exception {
    public ValidasiException(String message) {
        super(message);
    }
}
