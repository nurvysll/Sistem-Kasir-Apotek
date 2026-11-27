-- Sistem Kasir Apotek (MySQL / XAMPP)
-- Jalankan sekali lewat phpMyAdmin (tab SQL) atau: mysql -u root < database/apotek.sql

CREATE DATABASE IF NOT EXISTS apotek_db CHARACTER SET utf8mb4;
USE apotek_db;

CREATE TABLE IF NOT EXISTS users (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password      VARCHAR(64)  NOT NULL,              -- SHA-256 (hex)
    nama_lengkap  VARCHAR(100) NOT NULL,
    role          ENUM('ADMIN','KASIR') NOT NULL
);

CREATE TABLE IF NOT EXISTS kategori (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    nama_kategori  VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS supplier (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    nama     VARCHAR(100) NOT NULL,
    telepon  VARCHAR(20),
    alamat   VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS obat (
    kode_obat        VARCHAR(20)  PRIMARY KEY,
    nama_obat        VARCHAR(150) NOT NULL,
    id_kategori      INT NOT NULL,
    id_supplier      INT NULL,
    harga            DECIMAL(12,2) NOT NULL,
    stok             INT NOT NULL DEFAULT 0,
    tgl_kadaluwarsa  DATE NOT NULL,
    FOREIGN KEY (id_kategori) REFERENCES kategori(id),
    FOREIGN KEY (id_supplier) REFERENCES supplier(id)
);

CREATE TABLE IF NOT EXISTS transaksi (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    tanggal    DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_kasir   INT NOT NULL,
    total      DECIMAL(14,2) NOT NULL,
    metode     ENUM('CASH','QRIS') NOT NULL,
    bayar      DECIMAL(14,2) NOT NULL,
    kembalian  DECIMAL(14,2) NOT NULL DEFAULT 0,      -- QRIS = 0
    FOREIGN KEY (id_kasir) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS detail_transaksi (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    id_transaksi  INT NOT NULL,
    kode_obat     VARCHAR(20) NOT NULL,
    jumlah        INT NOT NULL,
    harga_satuan  DECIMAL(12,2) NOT NULL,
    subtotal      DECIMAL(14,2) NOT NULL,
    FOREIGN KEY (id_transaksi) REFERENCES transaksi(id),
    FOREIGN KEY (kode_obat)    REFERENCES obat(kode_obat)
);

CREATE TABLE IF NOT EXISTS retur (
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    id_detail_transaksi  INT NOT NULL,                -- harus item dari transaksi yang tercatat
    jumlah               INT NOT NULL,
    alasan               VARCHAR(255),
    tanggal              DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_detail_transaksi) REFERENCES detail_transaksi(id)
);

-- ===== Data awal =====
-- admin / admin123  dan  kasir / kasir123  (SHA-256)
INSERT IGNORE INTO users (username, password, nama_lengkap, role) VALUES
 ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrator', 'ADMIN'),
 ('kasir', 'f02b7c1e519e4fa436147f7e1399974f9510aa9c8e0cb8be29151eb540f9d214', 'Kasir Apotek',  'KASIR');

INSERT IGNORE INTO kategori (id, nama_kategori) VALUES
 (1, 'Analgesik'), (2, 'Antibiotik'), (3, 'Vitamin'), (4, 'Obat Batuk & Flu');

INSERT IGNORE INTO supplier (id, nama, telepon, alamat) VALUES
 (1, 'PT Kimia Farma', '021-1234567', 'Jakarta'),
 (2, 'PT Kalbe Farma', '021-7654321', 'Jakarta');

INSERT IGNORE INTO obat VALUES
 ('OB001', 'Paracetamol 500 mg',  1, 1,  5000, 120, '2027-12-31'),
 ('OB002', 'Amoxicillin 500 mg',  2, 2, 12000,  60, '2027-06-30'),
 ('OB003', 'Vitamin C 500 mg',    3, 1,  8000,   5, '2027-03-15'),
 ('OB004', 'OBH Combi Batuk',     4, 2, 15000,  40, '2026-10-01');   -- contoh obat sudah kedaluwarsa
