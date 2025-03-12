-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 08, 2024 at 08:42 AM
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
-- Database: `bus_routedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `bus_routes`
--

CREATE TABLE `bus_routes` (
  `id` int(11) NOT NULL,
  `source` varchar(255) NOT NULL,
  `destination` varchar(255) NOT NULL,
  `car_name` varchar(255) DEFAULT NULL,
  `car_bname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stops` varchar(255) NOT NULL,
  `departure_time` time NOT NULL,
  `arrival_time` time NOT NULL,
  `travel_date` date NOT NULL,
  `cost_per_seat` decimal(10,2) NOT NULL,
  `Ph_No` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `bus_routes`
--

INSERT INTO `bus_routes` (`id`, `source`, `destination`, `car_name`, `car_bname`, `stops`, `departure_time`, `arrival_time`, `travel_date`, `cost_per_seat`, `Ph_No`) VALUES
(1, 'Yangon', 'Ngwe Saung', 'Kyal Nan Taw', 'ကြယ်နန်းတော်', 'Yangon- Pathein- Ngwe Saung', '06:00:00', '12:00:00', '2024-09-10', 18000.00, '09740937943'),
(2, 'Pathein', 'Yangon', 'Nay La Win', ' နေလဝင်း', 'Pathein-nyaungdon- Yangon', '05:00:00', '09:30:00', '2024-09-10', 14000.00, '09789700763'),
(3, 'pathein', 'yangon', 'Nay La Win', ' နေလဝင်း', 'pathein- yangon', '05:00:00', '09:30:00', '2024-09-09', 15000.00, '09795535563'),
(4, 'pathein', 'yangon', 'Nay La Win', 'နေလဝင်း', 'pathein- yangon', '06:00:00', '10:30:00', '2024-09-09', 15000.00, '09254364433'),
(6, 'Yangon', 'Pathein', 'Kyal Nan Taw', 'ကြယ်နန်းတော်', 'Yangon- Pathein', '07:00:00', '11:30:00', '2024-09-10', 15000.00, '09250622442'),
(7, 'pathein', 'mandalay', 'Shwe Mandalay', 'ရွှေမန္တလေး', 'Pathein- Yangon- Mandalay', '06:30:00', '20:00:00', '2024-09-10', 38000.00, '09777787060'),
(8, 'Mandalay', 'Pathein', 'Myat Mandalar Tun', 'မြတ်မန္တလာထွန်း', 'Mandalay- Yangon- Pathein', '05:00:00', '19:00:00', '2024-09-10', 38000.00, '094555501909'),
(9, 'Pathein', 'NayPyiTaw', 'Maw Khon Thit', 'မော်ကွန်းသစ်', 'Pathein- Yangon- NayPyiTaw', '06:00:00', '19:00:00', '2024-09-10', 30000.00, '09254676600'),
(10, 'NayPyiTaw', 'Pathein', 'MawKhonThit', 'မော်ကွန်းသစ်', 'NayPyiTaw- Yangon- Pathein', '05:00:00', '18:00:00', '2024-09-10', 30000.00, '0943037748'),
(11, 'Pathein', 'ChaungThar', 'Shwe Mingalar', 'ရွှေမင်္ဂလာ', 'Pathein- ChaungThar', '08:00:00', '09:30:00', '2024-09-10', 10000.00, '09422518581'),
(12, 'ChaungThar', 'Pathein', 'Zwe', 'ဇွဲ', 'ChaungThar- Pathein', '05:30:00', '07:00:00', '2024-09-10', 10000.00, '09941396827'),
(13, 'yangon', 'pathein', 'KyalNanTaw', ' ကြယ်နန်းတော်', 'yangon- Pathein', '09:30:00', '14:00:00', '2024-09-05', 10000.00, '09941396827'),
(15, 'Yangon', 'Ngwe Saung', 'Kyal Nan Taw', 'ကြယ်နန်းတော်', 'Yangon- Pathein- Ngwe Saung', '06:00:00', '12:00:00', '2024-09-06', 18000.00, '09740937943'),
(16, 'Pathein', 'Yangon', 'Nay La Win', ' နေလဝင်း', 'Pathein-nyaungdon- Yangon', '05:00:00', '09:30:00', '2024-09-06', 14000.00, '09789700763'),
(17, 'Pathein', 'Yangon', 'Nay La Win', ' နေလဝင်း', 'Pathein- Yangon', '05:00:00', '09:30:00', '2024-09-06', 15000.00, '09795535563'),
(18, 'Pathein', 'Yangon', 'Nay La Win', 'နေလဝင်း', 'Pathein- Yangon', '06:00:00', '10:30:00', '2024-09-06', 15000.00, '09254364433'),
(19, 'Yangon', 'Pathein', 'Kyal Nan Taw', 'ကြယ်နန်းတော်', 'Yangon- Pathein', '07:00:00', '11:30:00', '2024-09-06', 15000.00, '09250622442'),
(20, 'Pathein', 'Mandalay', 'Shwe Mandalay', 'ရွှေမန္တလေး', 'Pathein- Yangon- Mandalay', '06:30:00', '20:00:00', '2024-09-06', 38000.00, '09777787060'),
(21, 'Mandalay', 'Pathein', 'Myat Mandalar Tun', 'မြတ်မန္တလာထွန်း', 'Mandalay- Yangon- Pathein', '05:00:00', '19:00:00', '2024-09-06', 38000.00, '094555501909'),
(22, 'Pathein', 'NayPyiTaw', 'Maw Khon Thit', 'မော်ကွန်းသစ်', 'Pathein- Yangon- NayPyiTaw', '06:00:00', '19:00:00', '2024-09-06', 30000.00, '09254676600'),
(23, 'NayPyiTaw', 'Pathein', 'Maw Khon Thit', 'မော်ကွန်းသစ်', 'NayPyiTaw- Yangon- Pathein', '05:00:00', '18:00:00', '2024-09-06', 30000.00, '0943037748'),
(24, 'Pathein', 'ChaungThar', 'Shwe Mingalar', 'ရွှေမင်္ဂလာ', 'Pathein- ChaungThar', '08:00:00', '09:30:00', '2024-09-06', 10000.00, '09422518581'),
(25, 'ChaungThar', 'Pathein', 'Zwe', 'ဇွဲ', 'ChaungThar- Pathein', '05:30:00', '07:00:00', '2024-09-06', 10000.00, '09941396827'),
(26, 'Yangon', 'Pathein', 'Kyal Nan Taw', 'ကြယ်နန်းတော်', 'Yangon- Pathein', '05:30:00', '10:00:00', '2024-09-06', 10000.00, '09941396827'),
(27, 'Yangon', 'Ngwe Saung', 'Kyal Nan Taw', 'ကြယ်နန်းတော်', 'Yangon- Pathein- Ngwe Saung', '06:00:00', '12:00:00', '2024-09-07', 18000.00, '09740937943'),
(28, 'Pathein', 'Yangon', 'Nay La Win', ' နေလဝင်း', 'Pathein-nyaungdon- Yangon', '05:00:00', '09:30:00', '2024-09-07', 14000.00, '09789700763'),
(29, 'Pathein', 'Yangon', 'Nay La Win', ' နေလဝင်း', 'Pathein- Yangon', '05:00:00', '09:30:00', '2024-09-07', 15000.00, '09795535563'),
(30, 'Pathein', 'Yangon', 'Nay La Win', 'နေလဝင်း', 'Pathein- Yangon', '06:00:00', '10:30:00', '2024-09-07', 15000.00, '09254364433'),
(31, 'Yangon', 'Pathein', 'Kyal Nan Taw', 'ကြယ်နန်းတော်', 'Yangon- Pathein', '07:00:00', '11:30:00', '2024-09-07', 15000.00, '09250622442'),
(32, 'Pathein', 'Mandalay', 'Shwe Mandalay', 'ရွှေမန္တလေး', 'Pathein- Yangon- Mandalay', '06:30:00', '20:00:00', '2024-09-07', 38000.00, '09777787060'),
(33, 'Mandalay', 'Pathein', 'Myat Mandalar Tun', 'မြတ်မန္တလာထွန်း', 'Mandalay- Yangon- Pathein', '05:00:00', '19:00:00', '2024-09-07', 38000.00, '094555501909'),
(34, 'Pathein', 'NayPyiTaw', 'Maw Khon Thit', 'မော်ကွန်းသစ်', 'Pathein- Yangon- NayPyiTaw', '06:00:00', '19:00:00', '2024-09-07', 30000.00, '09254676600'),
(35, 'NayPyiTaw', 'Pathein', 'Maw Khon Thit', 'မော်ကွန်းသစ်', 'NayPyiTaw- Yangon- Pathein', '05:00:00', '18:00:00', '2024-09-07', 30000.00, '0943037748'),
(36, 'Pathein', 'ChaungThar', 'Shwe Mingalar', 'ရွှေမင်္ဂလာ', 'Pathein- ChaungThar', '08:00:00', '09:30:00', '2024-09-07', 10000.00, '09422518581'),
(37, 'ChaungThar', 'Pathein', 'Zwe', 'ဇွဲ', 'ChaungThar- Pathein', '05:30:00', '07:00:00', '2024-09-07', 10000.00, '09941396827'),
(38, 'Yangon', 'Pathein', 'Kyal Nan Taw', 'ကြယ်နန်းတော်', 'Yangon- Pathein', '05:30:00', '10:00:00', '2024-09-07', 10000.00, '09941396827'),
(42, 'yangon', 'pathein', 'nay la win', 'နေလဝင်း', 'yangon- pathein', '18:00:00', '22:00:00', '2024-09-05', 12000.00, '09941396827');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bus_routes`
--
ALTER TABLE `bus_routes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bus_routes`
--
ALTER TABLE `bus_routes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
