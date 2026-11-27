# Sistem Kasir Apotek (PBO - Java Swing + MySQL, MVC)

## Cara menjalankan
1. Nyalakan **Apache & MySQL** di XAMPP.
2. Buka phpMyAdmin, tab **SQL**, jalankan isi `database/apotek.sql` (membuat database `apotek_db`, tabel, dan data awal).
3. Buka folder project ini di **NetBeans** (File > Open Project), tunggu Maven mengunduh dependency, lalu **Run** (`apotek.Main`).
4. Kalau MySQL punya password, ubah `USER` / `PASS` di `src/main/java/apotek/util/DBConnection.java`.

Akun awal: `admin` / `admin123` (Admin) dan `kasir` / `kasir123` (Kasir).

## Struktur (MVC)
| Paket | Isi |
|---|---|
| `model` | Entitas: User (abstract) > Admin / Kasir, Obat, Kategori, Supplier, Transaksi, DetailTransaksi, Retur, Pembayaran (abstract) > PembayaranCash / PembayaranQris |
| `dao` | Akses database (JDBC): `BaseDAO<T,K>`, ObatDAO, KategoriDAO, SupplierDAO, UserDAO |
| `controller` | Validasi + penghubung view ke DAO: LoginController, ObatController, ReferensiController |
| `view` | Tampilan Swing: LoginView, MainFrame, ObatPanel, PlaceholderPanel |
| `exception` | ValidasiException, StokTidakCukupException, ObatKadaluwarsaException |
| `util` | DBConnection, PasswordUtil (SHA-256), Session, Format, Theme |

## Sudah jadi
- Login 2 role, tab menu berbeda untuk Admin dan Kasir
- CRUD obat + pencarian nama/kode (Admin: lengkap, Kasir: hanya lihat dan cari)
- Baris merah = kedaluwarsa, kuning = stok < 10
- Model dan DAO Kategori & Supplier (CRUD lengkap, tinggal dibuatkan tampilannya)
- Logika `Obat.validasiUntukDijual()`, `Pembayaran` (CASH hitung kembalian, QRIS tidak)
- `ObatDAO.kurangiStok(con, ...)` dan `tambahStok(con, ...)` untuk dipakai di dalam database transaction

## Pembagian tugas (4 orang)
| Anggota | Bagian | File yang dibuat / dilengkapi |
|---|---|---|
| 1 | Login, role, dashboard, kelola user, tampilan (tema, animasi) | `LoginView`, `MainFrame`, `Theme` |
| 2 | Data obat + pencarian + peringatan kedaluwarsa/stok | `ObatPanel`, `ObatController` (dasar sudah ada) |
| 3 | Kategori & supplier (panel + controller) | `KategoriPanel`, `SupplierPanel`, `KategoriController`, `SupplierController` |
| 4 | Transaksi, struk, riwayat, retur, laporan penjualan | `TransaksiDAO`, `ReturDAO`, `TransaksiPanel`, `ReturPanel`, `LaporanPanel` |

Bagian 4 paling berat; kalau mau lebih rata, pindahkan retur atau laporan ke anggota 1.

## Aturan kerja Git
- Satu orang membuat repo GitHub, tambahkan 3 lainnya sebagai collaborator, kirim link ke asisten.
- Tiap anggota bikin branch sendiri (`git checkout -b fitur-transaksi`), commit kecil dan sering, lalu merge lewat Pull Request. Kontribusi tiap orang terlihat di riwayat commit.
- Jangan commit `target/` dan `nbproject/private/` (sudah ada di `.gitignore`).
