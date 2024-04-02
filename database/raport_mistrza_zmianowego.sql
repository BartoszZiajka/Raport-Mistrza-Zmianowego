-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 02, 2024 at 04:14 PM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `raport_mistrza_zmianowego`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `adresy_zlecen_stalych`
--

CREATE TABLE `adresy_zlecen_stalych` (
  `id_adresu` int(11) NOT NULL,
  `adres` varchar(255) DEFAULT NULL,
  `id_zlecenia_stalego` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `adresy_zlecen_stalych`
--

INSERT INTO `adresy_zlecen_stalych` (`id_adresu`, `adres`, `id_zlecenia_stalego`) VALUES
(1, 'Miasto ul.Ulica 1', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `dane_z_obiektow`
--

CREATE TABLE `dane_z_obiektow` (
  `id_danych_z_obiektow` int(11) NOT NULL,
  `pw15_zasole` varchar(255) DEFAULT NULL,
  `c15_zasole` varchar(255) DEFAULT NULL,
  `pw20_zasole` varchar(255) DEFAULT NULL,
  `c20_zasole` varchar(255) DEFAULT NULL,
  `przeplyw_min_zasole` varchar(255) DEFAULT NULL,
  `przeplyw_max_zasole` varchar(255) DEFAULT NULL,
  `pw15_zaborze` varchar(255) DEFAULT NULL,
  `c15_zaborze` varchar(255) DEFAULT NULL,
  `pw20_zaborze` varchar(255) DEFAULT NULL,
  `c20_zaborze` varchar(255) DEFAULT NULL,
  `przeplyw_min_zaborze` varchar(255) DEFAULT NULL,
  `przeplyw_max_zaborze` varchar(255) DEFAULT NULL,
  `pw15_hydrofornia` varchar(255) DEFAULT NULL,
  `c15_hydrofornia` varchar(255) DEFAULT NULL,
  `pw20_hydrofornia` varchar(255) DEFAULT NULL,
  `c20_hydrofornia` varchar(255) DEFAULT NULL,
  `odczyt_chelmek` varchar(255) DEFAULT NULL,
  `zuzycie_chelmek` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dane_z_obiektow`
--

INSERT INTO `dane_z_obiektow` (`id_danych_z_obiektow`, `pw15_zasole`, `c15_zasole`, `pw20_zasole`, `c20_zasole`, `przeplyw_min_zasole`, `przeplyw_max_zasole`, `pw15_zaborze`, `c15_zaborze`, `pw20_zaborze`, `c20_zaborze`, `przeplyw_min_zaborze`, `przeplyw_max_zaborze`, `pw15_hydrofornia`, `c15_hydrofornia`, `pw20_hydrofornia`, `c20_hydrofornia`, `odczyt_chelmek`, `zuzycie_chelmek`) VALUES
(1, '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'),
(2, '', '1', '', '', '1', '', '', '', '', '', '', '', '', '1', '', '1', '', '1');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `dyzurni`
--

CREATE TABLE `dyzurni` (
  `id_dyzurnego` int(9) NOT NULL,
  `dyzurny` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dyzurni`
--

INSERT INTO `dyzurni` (`id_dyzurnego`, `dyzurny`) VALUES
(1, 'Jan Kowalski');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `dyzurni_raport`
--

CREATE TABLE `dyzurni_raport` (
  `id_dyzurnych` int(11) NOT NULL,
  `dyzur_zasole` varchar(255) DEFAULT NULL,
  `dyzur_zasole_2_zmiana` varchar(255) DEFAULT NULL,
  `dyzur_zaborze` varchar(255) DEFAULT NULL,
  `dyzur_zaborze_2_zmiana` varchar(255) DEFAULT NULL,
  `dyzur_hydrofornia` varchar(255) DEFAULT NULL,
  `dyzur_hydrofornia_2_zmiana` varchar(255) DEFAULT NULL,
  `dyzur_przepompownia` varchar(255) DEFAULT NULL,
  `dyzur_przepompownia_2_zmiana` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dyzurni_raport`
--

INSERT INTO `dyzurni_raport` (`id_dyzurnych`, `dyzur_zasole`, `dyzur_zasole_2_zmiana`, `dyzur_zaborze`, `dyzur_zaborze_2_zmiana`, `dyzur_hydrofornia`, `dyzur_hydrofornia_2_zmiana`, `dyzur_przepompownia`, `dyzur_przepompownia_2_zmiana`) VALUES
(1, 'Jan Kowalski', 'Jan Kowalski', 'Jan Kowalski', 'Jan Kowalski', 'Jan Kowalski', 'Jan Kowalski', 'Jan Kowalski', 'Jan Kowalski'),
(2, '', 'Jan Kowalski', '', '', 'Jan Kowalski', '', '', '');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `konta_kosztow_jazdy`
--

CREATE TABLE `konta_kosztow_jazdy` (
  `id_konta` int(9) NOT NULL,
  `numer_konta` varchar(255) DEFAULT NULL,
  `nazwa_konta` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `konta_kosztow_jazdy`
--

INSERT INTO `konta_kosztow_jazdy` (`id_konta`, `numer_konta`, `nazwa_konta`) VALUES
(1, '000', 'konto 1'),
(2, '001', 'konto 2'),
(3, '002', 'konto 3'),
(4, '003', 'konto 4'),
(5, '004', 'konto 5');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `konta_uzytkownikow`
--

CREATE TABLE `konta_uzytkownikow` (
  `id_konta_uzytkownika` int(11) NOT NULL,
  `nazwa_konta` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `haslo` varchar(255) DEFAULT NULL,
  `administrator` tinyint(1) NOT NULL DEFAULT 0,
  `raport_specjalny` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `konta_uzytkownikow`
--

INSERT INTO `konta_uzytkownikow` (`id_konta_uzytkownika`, `nazwa_konta`, `login`, `haslo`, `administrator`, `raport_specjalny`) VALUES
(1, 'Konto Testowe', 'test', '123', 0, 0),
(2, 'Konto Administratora', 'admin', '123', 1, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `nadgodziny_raport`
--

CREATE TABLE `nadgodziny_raport` (
  `id_nadgodziny` int(11) NOT NULL,
  `pracownik` varchar(255) DEFAULT NULL,
  `od_godz` varchar(255) DEFAULT NULL,
  `do_godz` varchar(255) DEFAULT NULL,
  `rodzaj_pracy` text DEFAULT NULL,
  `id_raportu` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nadgodziny_raport`
--

INSERT INTO `nadgodziny_raport` (`id_nadgodziny`, `pracownik`, `od_godz`, `do_godz`, `rodzaj_pracy`, `id_raportu`) VALUES
(5, 'Jan Kowalski', '14:00', '16:00', 'Prace remontowe', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `numery_kontaktowe`
--

CREATE TABLE `numery_kontaktowe` (
  `id_numeru_telefonu` int(9) NOT NULL,
  `numer_telefonu` varchar(255) DEFAULT NULL,
  `obiekt_numeru_telefonu` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `numery_kontaktowe`
--

INSERT INTO `numery_kontaktowe` (`id_numeru_telefonu`, `numer_telefonu`, `obiekt_numeru_telefonu`) VALUES
(1, '000-000-000', 'Obiekt 1');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `numery_telefonow_tauron`
--

CREATE TABLE `numery_telefonow_tauron` (
  `id_numeru_telefonu` int(11) NOT NULL,
  `podmiot_kontaktowy` varchar(255) DEFAULT NULL,
  `stanowisko_podmiotu` varchar(255) DEFAULT NULL,
  `email_kontaktowy` varchar(255) DEFAULT NULL,
  `numer_kontaktowy` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `numery_telefonow_tauron`
--

INSERT INTO `numery_telefonow_tauron` (`id_numeru_telefonu`, `podmiot_kontaktowy`, `stanowisko_podmiotu`, `email_kontaktowy`, `numer_kontaktowy`) VALUES
(12, 'Jan Kowalski', 'Dyrektor', 'jan.kowalski@gmail.com', '000-000-000');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pojazdy`
--

CREATE TABLE `pojazdy` (
  `id_pojazdu` int(9) NOT NULL,
  `nazwa_pojazdu` varchar(255) DEFAULT NULL,
  `cennik_normalny` varchar(255) DEFAULT NULL,
  `cennik_nadgodzinny` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pojazdy`
--

INSERT INTO `pojazdy` (`id_pojazdu`, `nazwa_pojazdu`, `cennik_normalny`, `cennik_nadgodzinny`) VALUES
(1, 'Pojazd 1', '(cennik)', '(cennik)');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `portierzy`
--

CREATE TABLE `portierzy` (
  `id_portiera` int(9) NOT NULL,
  `portier` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `portierzy`
--

INSERT INTO `portierzy` (`id_portiera`, `portier`) VALUES
(1, 'Jan Kowalski');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `portierzy_raport`
--

CREATE TABLE `portierzy_raport` (
  `id_portierow` int(11) NOT NULL,
  `do_godz` varchar(255) DEFAULT NULL,
  `od_godz` varchar(255) DEFAULT NULL,
  `portier_do` varchar(255) DEFAULT NULL,
  `portier_od` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `portierzy_raport`
--

INSERT INTO `portierzy_raport` (`id_portierow`, `do_godz`, `od_godz`, `portier_do`, `portier_od`) VALUES
(1, '07:00', '15:00', 'Jan Kowalski', 'Jan Kowalski'),
(2, '', '22:00', '', 'Jan Kowalski');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pracownicy`
--

CREATE TABLE `pracownicy` (
  `id_pracownika` int(11) NOT NULL,
  `pracownik` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pracownicy`
--

INSERT INTO `pracownicy` (`id_pracownika`, `pracownik`) VALUES
(1, 'Jan Kowalski');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `raporty`
--

CREATE TABLE `raporty` (
  `id_raportu` int(11) NOT NULL,
  `data_raportu` date DEFAULT NULL,
  `godziny_pracy` varchar(255) DEFAULT NULL,
  `mistrz_zmiany` varchar(255) DEFAULT NULL,
  `raport_zmiany` text DEFAULT NULL,
  `id_konta_uzytkownika` int(11) DEFAULT NULL,
  `id_portierow` int(11) DEFAULT NULL,
  `id_dyzurnych` int(11) DEFAULT NULL,
  `id_danych_z_obiektow` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `raporty`
--

INSERT INTO `raporty` (`id_raportu`, `data_raportu`, `godziny_pracy`, `mistrz_zmiany`, `raport_zmiany`, `id_konta_uzytkownika`, `id_portierow`, `id_dyzurnych`, `id_danych_z_obiektow`) VALUES
(1, '2024-04-02', '15:00 - 23:00', 'Konto Testowe', 'Przykładowy raport zmiany		', 1, 1, 1, 1),
(2, '2024-01-01', '12:00 - 20:00*', 'Konto Administratora', '', 2, 2, 2, 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `zlecenia_stale`
--

CREATE TABLE `zlecenia_stale` (
  `id_zlecenia_stalego` int(11) NOT NULL,
  `podmiot_zlecenia` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `zlecenia_stale`
--

INSERT INTO `zlecenia_stale` (`id_zlecenia_stalego`, `podmiot_zlecenia`) VALUES
(1, 'Zlecenie 1');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `adresy_zlecen_stalych`
--
ALTER TABLE `adresy_zlecen_stalych`
  ADD PRIMARY KEY (`id_adresu`),
  ADD KEY `id_zlecenia_stalego` (`id_zlecenia_stalego`);

--
-- Indeksy dla tabeli `dane_z_obiektow`
--
ALTER TABLE `dane_z_obiektow`
  ADD PRIMARY KEY (`id_danych_z_obiektow`);

--
-- Indeksy dla tabeli `dyzurni`
--
ALTER TABLE `dyzurni`
  ADD PRIMARY KEY (`id_dyzurnego`);

--
-- Indeksy dla tabeli `dyzurni_raport`
--
ALTER TABLE `dyzurni_raport`
  ADD PRIMARY KEY (`id_dyzurnych`);

--
-- Indeksy dla tabeli `konta_kosztow_jazdy`
--
ALTER TABLE `konta_kosztow_jazdy`
  ADD PRIMARY KEY (`id_konta`);

--
-- Indeksy dla tabeli `konta_uzytkownikow`
--
ALTER TABLE `konta_uzytkownikow`
  ADD PRIMARY KEY (`id_konta_uzytkownika`);

--
-- Indeksy dla tabeli `nadgodziny_raport`
--
ALTER TABLE `nadgodziny_raport`
  ADD PRIMARY KEY (`id_nadgodziny`),
  ADD KEY `id_raportu` (`id_raportu`);

--
-- Indeksy dla tabeli `numery_kontaktowe`
--
ALTER TABLE `numery_kontaktowe`
  ADD PRIMARY KEY (`id_numeru_telefonu`);

--
-- Indeksy dla tabeli `numery_telefonow_tauron`
--
ALTER TABLE `numery_telefonow_tauron`
  ADD PRIMARY KEY (`id_numeru_telefonu`);

--
-- Indeksy dla tabeli `pojazdy`
--
ALTER TABLE `pojazdy`
  ADD PRIMARY KEY (`id_pojazdu`);

--
-- Indeksy dla tabeli `portierzy`
--
ALTER TABLE `portierzy`
  ADD PRIMARY KEY (`id_portiera`);

--
-- Indeksy dla tabeli `portierzy_raport`
--
ALTER TABLE `portierzy_raport`
  ADD PRIMARY KEY (`id_portierow`);

--
-- Indeksy dla tabeli `pracownicy`
--
ALTER TABLE `pracownicy`
  ADD PRIMARY KEY (`id_pracownika`);

--
-- Indeksy dla tabeli `raporty`
--
ALTER TABLE `raporty`
  ADD PRIMARY KEY (`id_raportu`),
  ADD KEY `id_konta_uzytkownika` (`id_konta_uzytkownika`),
  ADD KEY `id_portierow` (`id_portierow`),
  ADD KEY `id_dyzurnych` (`id_dyzurnych`),
  ADD KEY `id_danych_z_obiektow` (`id_danych_z_obiektow`);

--
-- Indeksy dla tabeli `zlecenia_stale`
--
ALTER TABLE `zlecenia_stale`
  ADD PRIMARY KEY (`id_zlecenia_stalego`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adresy_zlecen_stalych`
--
ALTER TABLE `adresy_zlecen_stalych`
  MODIFY `id_adresu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `dane_z_obiektow`
--
ALTER TABLE `dane_z_obiektow`
  MODIFY `id_danych_z_obiektow` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `dyzurni`
--
ALTER TABLE `dyzurni`
  MODIFY `id_dyzurnego` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `dyzurni_raport`
--
ALTER TABLE `dyzurni_raport`
  MODIFY `id_dyzurnych` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `konta_kosztow_jazdy`
--
ALTER TABLE `konta_kosztow_jazdy`
  MODIFY `id_konta` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `konta_uzytkownikow`
--
ALTER TABLE `konta_uzytkownikow`
  MODIFY `id_konta_uzytkownika` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `nadgodziny_raport`
--
ALTER TABLE `nadgodziny_raport`
  MODIFY `id_nadgodziny` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `numery_kontaktowe`
--
ALTER TABLE `numery_kontaktowe`
  MODIFY `id_numeru_telefonu` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `numery_telefonow_tauron`
--
ALTER TABLE `numery_telefonow_tauron`
  MODIFY `id_numeru_telefonu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `pojazdy`
--
ALTER TABLE `pojazdy`
  MODIFY `id_pojazdu` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `portierzy`
--
ALTER TABLE `portierzy`
  MODIFY `id_portiera` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `portierzy_raport`
--
ALTER TABLE `portierzy_raport`
  MODIFY `id_portierow` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `pracownicy`
--
ALTER TABLE `pracownicy`
  MODIFY `id_pracownika` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `raporty`
--
ALTER TABLE `raporty`
  MODIFY `id_raportu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `zlecenia_stale`
--
ALTER TABLE `zlecenia_stale`
  MODIFY `id_zlecenia_stalego` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `adresy_zlecen_stalych`
--
ALTER TABLE `adresy_zlecen_stalych`
  ADD CONSTRAINT `adresy_zlecen_stalych_ibfk_1` FOREIGN KEY (`id_zlecenia_stalego`) REFERENCES `zlecenia_stale` (`id_zlecenia_stalego`);

--
-- Constraints for table `nadgodziny_raport`
--
ALTER TABLE `nadgodziny_raport`
  ADD CONSTRAINT `nadgodziny_raport_ibfk_1` FOREIGN KEY (`id_raportu`) REFERENCES `raporty` (`id_raportu`);

--
-- Constraints for table `raporty`
--
ALTER TABLE `raporty`
  ADD CONSTRAINT `raporty_ibfk_1` FOREIGN KEY (`id_konta_uzytkownika`) REFERENCES `konta_uzytkownikow` (`id_konta_uzytkownika`),
  ADD CONSTRAINT `raporty_ibfk_3` FOREIGN KEY (`id_portierow`) REFERENCES `portierzy_raport` (`id_portierow`),
  ADD CONSTRAINT `raporty_ibfk_4` FOREIGN KEY (`id_dyzurnych`) REFERENCES `dyzurni_raport` (`id_dyzurnych`),
  ADD CONSTRAINT `raporty_ibfk_5` FOREIGN KEY (`id_danych_z_obiektow`) REFERENCES `dane_z_obiektow` (`id_danych_z_obiektow`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
