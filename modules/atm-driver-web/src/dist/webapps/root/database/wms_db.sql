-- phpMyAdmin SQL Dump
-- version 4.9.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Aug 31, 2020 at 06:57 PM
-- Server version: 5.7.26
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wms_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `distribute_receive`
--

CREATE TABLE `distribute_receive` (
  `category` varchar(32) NOT NULL,
  `distribute_total` int(11) NOT NULL,
  `receive_total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `distribute_receive`
--

INSERT INTO `distribute_receive` (`category`, `distribute_total`, `receive_total`) VALUES
('electronics', 32, 20),
('stationery', 5, 17),
('sports', 45, 171),
('household', 6, 19),
('clothing', 3, 13);

-- --------------------------------------------------------

--
-- Table structure for table `inventory_items`
--

CREATE TABLE `inventory_items` (
  `id` int(11) NOT NULL,
  `upc` varchar(255) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `item_desc` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `quantity` int(255) NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `inventory_items`
--

INSERT INTO `inventory_items` (`id`, `upc`, `item_name`, `item_desc`, `category`, `quantity`, `last_modified`) VALUES
(71, '466867867', 'Desk Lamp', 'Remax LED Desk Light and Stand', 'electronics', 75, '2020-08-31 13:53:41'),
(73, '1234', 'Headphones', 'Wireless Headphones', 'electronics', 0, '2020-08-31 11:30:48'),
(74, '564', 'Book', 'Art Book', 'stationery', 3, '2020-08-30 17:35:57'),
(75, '678', 'Demin Jeans', 'Mens Demin Jeans', 'clothing', 13, '2020-08-31 18:38:07'),
(76, '789', 'Tea Cups', 'Just a tea&#39;s cup', 'household', 33, '2020-08-30 21:21:27'),
(77, '456', 'Nike Socks', 'Nike Basketball socks', 'sports', 165, '2020-08-31 18:38:43'),
(78, '678', 'Pilot Pens', 'Gel Ink Pilot Pens', 'stationery', -1, '2020-08-31 18:31:21'),
(80, '2016', 'Makeup Brush', 'Morphe Makeup Brushes SM', 'household', 2, '2020-08-31 10:50:51');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `inventory_items`
--
ALTER TABLE `inventory_items`
  ADD KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `inventory_items`
--
ALTER TABLE `inventory_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=87;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
