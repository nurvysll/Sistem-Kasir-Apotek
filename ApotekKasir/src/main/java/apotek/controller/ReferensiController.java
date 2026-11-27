package apotek.controller;

import apotek.dao.KategoriDAO;
import apotek.dao.SupplierDAO;
import apotek.exception.ValidasiException;
import apotek.model.Kategori;
import apotek.model.Supplier;
import java.sql.SQLException;
import java.util.List;

/** Data pendukung (kategori & supplier) untuk combo box. CRUD lengkapnya dikerjakan anggota 3. */
public class ReferensiController {
    private final KategoriDAO kategoriDAO = new KategoriDAO();
    private final SupplierDAO supplierDAO = new SupplierDAO();

    public List<Kategori> semuaKategori() throws ValidasiException {
        try {
            return kategoriDAO.findAll();
        } catch (SQLException e) {
            throw new ValidasiException("Gagal memuat kategori: " + e.getMessage());
        }
    }

    public List<Supplier> semuaSupplier() throws ValidasiException {
        try {
            return supplierDAO.findAll();
        } catch (SQLException e) {
            throw new ValidasiException("Gagal memuat supplier: " + e.getMessage());
        }
    }
}
