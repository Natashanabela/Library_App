-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 11, 2024 at 07:02 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpustakaan`
--

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `image` varchar(100) NOT NULL,
  `id_buku` int(10) NOT NULL,
  `judul` varchar(100) NOT NULL,
  `pengarang` varchar(100) NOT NULL,
  `penerbit` varchar(100) NOT NULL,
  `id_kategori` int(10) NOT NULL,
  `tahun_terbit` int(100) NOT NULL,
  `jumlah_halaman` int(200) NOT NULL,
  `status_buku` enum('Ready','Tidak Ready') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`image`, `id_buku`, `judul`, `pengarang`, `penerbit`, `id_kategori`, `tahun_terbit`, `jumlah_halaman`, `status_buku`) VALUES
('img/11-01-2024-211322-6684.jpg', 110, 'Laskar Pelangi', 'Andrea Hirata', 'Bentang Pusaka', 1, 2005, 529, 'Tidak Ready'),
('img/11-01-2024-211508-7862.jpg', 111, 'Si Juki komik strip', 'Faza Meonk', 'Bukune', 4, 2014, 164, 'Ready'),
('img/11-01-2024-211607-8280.jpg', 112, 'Detektif Conan Vol 01', 'Gosho Adyama', 'Elex Media', 4, 2023, 480, 'Tidak Ready'),
('img/11-01-2024-211659-462.jpg', 113, 'Harry Potter and The Goblet of Fire', 'J.K. Rowling', 'Gramedia Pustaka Utama', 1, 2017, 896, 'Ready'),
('img/11-01-2024-211843-9464.jpg', 114, 'Kamus Saku Bahasa Indonesia', 'Tim B First', 'Efata Publish', 5, 2016, 558, 'Ready'),
('img/11-01-2024-212001-5623.jpg', 115, 'Raden Ajeng Kartini', 'Whina Wicaksana', 'C- Klik Media', 2, 2022, 98, 'Ready');

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` int(10) NOT NULL,
  `nama_kategori` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `nama_kategori`) VALUES
(1, 'Novel'),
(2, 'Biografi'),
(4, 'Komik'),
(5, 'Kamus');

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `id_member` int(10) NOT NULL,
  `nama_member` varchar(100) NOT NULL,
  `jenis_kelamin` enum('Pria','Wanita') NOT NULL,
  `nomor_telepon` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`id_member`, `nama_member`, `jenis_kelamin`, `nomor_telepon`) VALUES
(220, 'Agnes Valerie Khoe', 'Wanita', '081123448762'),
(221, 'Natasha Anabela', 'Wanita', '089022345310'),
(222, 'Metta Apriliani', 'Wanita', '081920205156'),
(223, 'Chris Evans', 'Pria', '087240418877'),
(224, 'John Wick', 'Pria', '081290212100');

-- --------------------------------------------------------

--
-- Table structure for table `peminjaman`
--

CREATE TABLE `peminjaman` (
  `id_peminjaman` int(10) NOT NULL,
  `id_member` int(10) NOT NULL,
  `id_buku` int(10) NOT NULL,
  `tanggal_peminjaman` date NOT NULL,
  `tanggal_pengembalian` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `peminjaman`
--

INSERT INTO `peminjaman` (`id_peminjaman`, `id_member`, `id_buku`, `tanggal_peminjaman`, `tanggal_pengembalian`) VALUES
(1, 220, 113, '2024-01-11', '2024-01-18'),
(2, 222, 111, '2024-01-03', '2024-01-10'),
(3, 221, 114, '2024-01-02', '2024-01-11');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id_buku`),
  ADD KEY `id_kategori` (`id_kategori`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`id_member`);

--
-- Indexes for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`id_peminjaman`),
  ADD KEY `id_member` (`id_member`),
  ADD KEY `id_buku` (`id_buku`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id_kategori` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;

--
-- AUTO_INCREMENT for table `peminjaman`
--
ALTER TABLE `peminjaman`
  MODIFY `id_peminjaman` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `buku`
--
ALTER TABLE `buku`
  ADD CONSTRAINT `id_kategori` FOREIGN KEY (`id_kategori`) REFERENCES `kategori` (`id_kategori`) ON DELETE CASCADE;

--
-- Constraints for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `id_buku` FOREIGN KEY (`id_buku`) REFERENCES `buku` (`id_buku`) ON DELETE CASCADE,
  ADD CONSTRAINT `id_member` FOREIGN KEY (`id_member`) REFERENCES `member` (`id_member`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
