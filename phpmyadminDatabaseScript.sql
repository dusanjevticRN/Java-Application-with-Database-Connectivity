-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 25, 2024 at 01:48 PM
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
-- Database: `person`
--

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

CREATE TABLE `korisnici` (
  `id` int(11) NOT NULL,
  `ime` varchar(30) NOT NULL,
  `prezime` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`id`, `ime`, `prezime`, `username`, `password`) VALUES
(1, 'marko', 'ilic', 'marko', '1234'),
(2, 'dimitrije', 'andzic', 'dimitrije', 'dimi123'),
(3, 'ivan', 'jevtic', 'ivan123', 'password'),
(4, 'dusan', 'jevtic', 'dule123', 'dusan123'),
(5, 'marko', 'jevtic', 'marko', '123456'),
(6, 'test1', 'test1', 'test1', 'test1'),
(7, 'dule', 'jevtic', 'finaltest', 'finaltest');

-- --------------------------------------------------------

--
-- Table structure for table `kupovine`
--

CREATE TABLE `kupovine` (
  `id` int(11) NOT NULL,
  `ime_korisnika` varchar(30) NOT NULL,
  `prezime_korisnika` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `ime_zgrade` varchar(30) NOT NULL,
  `vozilo` varchar(30) NOT NULL,
  `datum_kupovine` date NOT NULL,
  `datum_polaska` date NOT NULL,
  `vreme_polaska` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kupovine`
--

INSERT INTO `kupovine` (`id`, `ime_korisnika`, `prezime_korisnika`, `username`, `ime_zgrade`, `vozilo`, `datum_kupovine`, `datum_polaska`, `vreme_polaska`) VALUES
(1, 'dimitrije', '', '', 'ajfelova kula', '', '2024-04-23', '0000-00-00', '00:00:00'),
(2, 'ivan', '', '', 'delta', '', '2024-03-19', '0000-00-00', '00:00:00'),
(3, 'dusan', '', '', 'usce', '', '2024-03-18', '0000-00-00', '00:00:00'),
(4, 'marko', '', '', 'epsilon', '', '2024-01-29', '0000-00-00', '00:00:00'),
(9, 'test1', 'test1', 'test1', 'Shangai Tower', 'vx-166', '2024-05-25', '2025-05-13', '17:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `misije`
--

CREATE TABLE `misije` (
  `id` int(11) NOT NULL,
  `datum_pocetka` date NOT NULL,
  `datum_zavrsetka` date NOT NULL,
  `status_misije` varchar(10) NOT NULL,
  `ime_misije` varchar(40) NOT NULL,
  `lokacijamisije` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `misije`
--

INSERT INTO `misije` (`id`, `datum_pocetka`, `datum_zavrsetka`, `status_misije`, `ime_misije`, `lokacijamisije`) VALUES
(1, '2024-05-01', '2024-05-23', 'neuspesna', 'pariz', ''),
(2, '2024-04-04', '2024-04-18', 'uspesna', 'exec', 'Planeta S'),
(3, '2024-03-05', '2024-03-21', 'uspesna', 'japan', 'Satelit S'),
(4, '2024-04-16', '2024-05-06', 'uspesna', 'amerika', ''),
(5, '2024-01-08', '2024-03-18', 'neuspesna', 'toranj', '');

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `id` int(11) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `date_of_birth` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `first_name`, `last_name`, `date_of_birth`) VALUES
(1, 'Dimitrije', 'Andzic', '2024-05-15'),
(2, 'Mihajlo', 'Stojanovic', '2024-05-07');

-- --------------------------------------------------------

--
-- Table structure for table `planetesateliti`
--

CREATE TABLE `planetesateliti` (
  `id` int(11) NOT NULL,
  `ime` varchar(30) NOT NULL,
  `tip` varchar(30) NOT NULL,
  `srednja_udaljenost_od_zvezde` float NOT NULL,
  `najniza_temperatura` float NOT NULL,
  `najvisa_temperatura` float NOT NULL,
  `nivo_kiseonika` float NOT NULL,
  `nivo_drugih_gasova` float NOT NULL,
  `visina_gravitacije` float NOT NULL,
  `brzina_orbite` float NOT NULL,
  `broj_smrtnih_slucajeva` int(11) NOT NULL,
  `provedene_godine` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `planetesateliti`
--

INSERT INTO `planetesateliti` (`id`, `ime`, `tip`, `srednja_udaljenost_od_zvezde`, `najniza_temperatura`, `najvisa_temperatura`, `nivo_kiseonika`, `nivo_drugih_gasova`, `visina_gravitacije`, `brzina_orbite`, `broj_smrtnih_slucajeva`, `provedene_godine`) VALUES
(1, 'Planeta A', 'planeta', 150, 160, 300, 20, 75, 1500, 30, 10, 1),
(2, 'Planeta B', 'planeta', 180, 170, 290, 22, 73, 1200, 28, 5, 2),
(3, 'Satelit A', 'satelit', 140, 165, 295, 18, 72, 1300, 32, 12, 1),
(4, 'Planeta C', 'planeta', 200, 180, 310, 21, 74, 1600, 27, 8, 1),
(5, 'Satelit B', 'satelit', 190, 175, 305, 19, 70, 1400, 29, 15, 2),
(6, 'Planeta S', 'planeta', 120, 250, 350, 20, 70, 1200, 30, 10, 1),
(7, 'Satelit S', 'satelit', 120, 180, 260, 16, 80, 1400, 31, 16, 1);

-- --------------------------------------------------------

--
-- Table structure for table `putovanja`
--

CREATE TABLE `putovanja` (
  `id` int(11) NOT NULL,
  `kod_vozila` varchar(30) NOT NULL,
  `datum_polaska` date NOT NULL,
  `traveler_ime` varchar(30) NOT NULL,
  `traveler_prezime` varchar(30) NOT NULL,
  `traveler_username` varchar(30) NOT NULL,
  `drugistanovnik_ime` varchar(30) DEFAULT NULL,
  `drugistanovnik_prezime` varchar(30) DEFAULT NULL,
  `zgrada` varchar(30) NOT NULL,
  `drugistanovnik_username` varchar(30) DEFAULT NULL,
  `vreme_polaska` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `putovanja`
--

INSERT INTO `putovanja` (`id`, `kod_vozila`, `datum_polaska`, `traveler_ime`, `traveler_prezime`, `traveler_username`, `drugistanovnik_ime`, `drugistanovnik_prezime`, `zgrada`, `drugistanovnik_username`, `vreme_polaska`) VALUES
(1, 'vx-100', '2024-05-05', '', '', '', NULL, NULL, '', NULL, '00:00:00'),
(2, 'vx-101', '2024-02-12', '', '', '', NULL, NULL, '', NULL, '00:00:00'),
(3, 'vx-102', '2024-01-01', '', '', '', NULL, NULL, '', NULL, '00:00:00'),
(4, 'vx-103', '2024-01-28', '', '', '', NULL, NULL, '', NULL, '00:00:00'),
(5, 'vx-104', '2024-02-12', '', '', '', NULL, NULL, '', NULL, '00:00:00'),
(6, 'vx-105', '2024-05-31', 'test1', 'test1', 'test1', 'asd', 'asd', 'K3', 'asd', '20:00:00'),
(7, 'vx-117', '2024-05-31', 'test1', 'test1', 'test1', NULL, NULL, 'merdeka', NULL, '14:00:00'),
(8, 'vx-133', '2025-05-09', 'test1', 'test1', 'test1', NULL, NULL, 'K3', NULL, '16:00:00'),
(9, 'vx-114', '2024-07-17', 'test1', 'test1', 'test1', NULL, NULL, 'K3', NULL, '11:00:00'),
(10, 'vx-127', '2026-05-20', 'test1', 'test1', 'test1', NULL, NULL, 'Willis Tower', NULL, '17:00:00'),
(11, 'vx-105', '2025-05-21', 'test1', 'test1', 'test1', NULL, NULL, 'TAIPEI 100', NULL, '12:00:00'),
(12, 'vx-142', '2025-05-14', 'test1', 'test1', 'test1', NULL, NULL, 'Vincom Tower', NULL, '12:20:00'),
(13, 'vx-105', '2024-06-13', 'test1', 'test1', 'test1', NULL, NULL, 'Willis Tower', NULL, '16:00:00'),
(15, 'vx-119', '2024-05-29', 'test1', 'test1', 'test1', NULL, NULL, 'K3', NULL, '16:00:00'),
(16, 'vx-166', '2025-05-13', 'test1', 'test1', 'test1', NULL, NULL, 'Shangai Tower', NULL, '17:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `stambenezgrade`
--

CREATE TABLE `stambenezgrade` (
  `id` int(11) NOT NULL,
  `ime_zgrade` varchar(30) NOT NULL,
  `broj_jedinica` int(11) NOT NULL,
  `slobodne_jedinice` int(11) NOT NULL,
  `planetasatelit` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stambenezgrade`
--

INSERT INTO `stambenezgrade` (`id`, `ime_zgrade`, `broj_jedinica`, `slobodne_jedinice`, `planetasatelit`) VALUES
(1, 'zgrada_1', 100, 80, 'Planeta A'),
(2, 'ajfelova kula', 120, 100, 'Planeta B'),
(3, 'delta', 110, 70, 'Satelit A'),
(4, 'usce', 90, 60, 'Planeta C'),
(5, 'epsilon', 200, 70, 'Satelit B'),
(6, 'K3', 200, 116, 'Planeta S'),
(7, 'merdeka', 80, 19, 'Planeta S'),
(8, 'Shangai Tower', 140, 9, 'Planeta S'),
(9, 'Royal Tower', 140, 100, 'Planeta S'),
(10, 'TAIPEI 100', 100, 12, 'Satelit S'),
(11, 'Vincom Tower', 300, 119, 'Satelit S'),
(12, 'Willis Tower', 300, 78, 'Satelit S');

-- --------------------------------------------------------

--
-- Table structure for table `stanovnici`
--

CREATE TABLE `stanovnici` (
  `id` int(11) NOT NULL,
  `id_kupovine` int(11) NOT NULL,
  `ime` varchar(30) NOT NULL,
  `prezime` varchar(30) NOT NULL,
  `datum_rodjenja` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `korisnici`
--
ALTER TABLE `korisnici`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kupovine`
--
ALTER TABLE `kupovine`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `misije`
--
ALTER TABLE `misije`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `planetesateliti`
--
ALTER TABLE `planetesateliti`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `putovanja`
--
ALTER TABLE `putovanja`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stambenezgrade`
--
ALTER TABLE `stambenezgrade`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stanovnici`
--
ALTER TABLE `stanovnici`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `korisnici`
--
ALTER TABLE `korisnici`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `kupovine`
--
ALTER TABLE `kupovine`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `misije`
--
ALTER TABLE `misije`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `planetesateliti`
--
ALTER TABLE `planetesateliti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `putovanja`
--
ALTER TABLE `putovanja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `stambenezgrade`
--
ALTER TABLE `stambenezgrade`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `stanovnici`
--
ALTER TABLE `stanovnici`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
