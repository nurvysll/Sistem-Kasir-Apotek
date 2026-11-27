package apotek.controller;

import apotek.dao.ObatDAO;
import apotek.exception.ValidasiException;
import apotek.model.Kategori;
import apotek.model.Obat;
import apotek.model.Supplier;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ObatController {
    private final ObatDAO dao = new ObatDAO();

    public List<Obat> semua() throws ValidasiException {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            throw error(e);
        }
    }

    public List<Obat> cari(String keyword) throws ValidasiException {
        if (keyword == null || keyword.isBlank()) return semua();
        try {
            return dao.cari(keyword.trim());
        } catch (SQLException e) {
            throw error(e);
        }
    }

    public void tambah(String kode, String nama, Kategori kategori, Supplier supplier,
                       String harga, String stok, String tgl) throws ValidasiException {
        Obat o = bangun(kode, nama, kategori, supplier, harga, stok, tgl);
        try {
            dao.insert(o);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) throw new ValidasiException("Kode obat '" + o.getKodeObat() + "' sudah dipakai");
            throw error(e);
        }
    }

    public void ubah(String kode, String nama, Kategori kategori, Supplier supplier,
                     String harga, String stok, String tgl) throws ValidasiException {
        Obat o = bangun(kode, nama, kategori, supplier, harga, stok, tgl);
        try {
            dao.update(o);
        } catch (SQLException e) {
            throw error(e);
        }
    }

    public void hapus(String kode) throws ValidasiException {
        try {
            dao.delete(kode);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                throw new ValidasiException("Obat ini sudah pernah dipakai di transaksi, tidak bisa dihapus");
            }
            throw error(e);
        }
    }

    /** Validasi input form lalu bentuk objek Obat. */
    private Obat bangun(String kode, String nama, Kategori kategori, Supplier supplier,
                        String harga, String stok, String tgl) throws ValidasiException {
        if (kode == null || kode.isBlank()) throw new ValidasiException("Kode obat wajib diisi");
        if (nama == null || nama.isBlank()) throw new ValidasiException("Nama obat wajib diisi");
        if (kategori == null) throw new ValidasiException("Kategori wajib dipilih");

        double hargaNum;
        int stokNum;
        LocalDate tglNum;
        try {
            hargaNum = Double.parseDouble(harga.trim());
        } catch (NumberFormatException e) {
            throw new ValidasiException("Harga harus berupa angka");
        }
        try {
            stokNum = Integer.parseInt(stok.trim());
        } catch (NumberFormatException e) {
            throw new ValidasiException("Stok harus berupa bilangan bulat");
        }
        try {
            tglNum = LocalDate.parse(tgl.trim());
        } catch (DateTimeParseException e) {
            throw new ValidasiException("Tanggal kedaluwarsa harus berformat yyyy-MM-dd, contoh 2027-12-31");
        }
        if (hargaNum <= 0) throw new ValidasiException("Harga harus lebih dari 0");
        if (stokNum < 0) throw new ValidasiException("Stok tidak boleh negatif");

        Obat o = new Obat();
        o.setKodeObat(kode.trim().toUpperCase());
        o.setNamaObat(nama.trim());
        o.setIdKategori(kategori.getId());
        o.setIdSupplier(supplier == null ? 0 : supplier.getId());
        o.setHarga(hargaNum);
        o.setStok(stokNum);
        o.setTglKadaluwarsa(tglNum);
        return o;
    }

    private ValidasiException error(SQLException e) {
        return new ValidasiException("Kesalahan database: " + e.getMessage());
    }
}
