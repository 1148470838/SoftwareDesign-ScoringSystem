-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2021-03-20 16:39:03
-- 服务器版本： 10.4.17-MariaDB
-- PHP 版本： 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `bearcome`
--
CREATE DATABASE IF NOT EXISTS `bearcome` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `bearcome`;

-- --------------------------------------------------------

--
-- 表的结构 `competitor`
--

CREATE TABLE IF NOT EXISTS `competitor` (
  `userid` int(16) NOT NULL,
  `partake` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`score`)),
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 转存表中的数据 `competitor`
--

REPLACE INTO `competitor` (`userid`, `partake`, `score`) VALUES
(1, '3,', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `contest`
--

CREATE TABLE IF NOT EXISTS `contest` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `starttime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  `maxmembers` int(16) NOT NULL,
  `currentmembers` int(16) NOT NULL DEFAULT 0,
  `userid` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creatorid` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `judgeid` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `jLock` tinyint(1) NOT NULL DEFAULT 0,
  `cLock` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 转存表中的数据 `contest`
--

REPLACE INTO `contest` (`id`, `name`, `description`, `starttime`, `endtime`, `maxmembers`, `currentmembers`, `userid`, `creatorid`, `judgeid`, `jLock`, `cLock`) VALUES
(1, 'test', 'hello', '2021-02-21 20:39:35', '2021-02-22 20:39:35', 10, 7, '', '4', '1,1,', 0, 0),
(2, 'test2', 'hey', '2021-02-23 20:40:06', '2021-02-24 20:40:06', 10, 6, '', '', NULL, 0, 0),
(3, '1', '1', '2021-02-25 14:38:17', '2021-07-01 14:38:17', 100, 2, '1,', '', NULL, 0, 0),
(4, '123123', '123131', '2021-03-04 13:53:17', '2021-03-05 13:53:17', 10, 0, NULL, '', NULL, 0, 0),
(6, 'a', 'a', '2021-03-18 21:15:00', '2021-05-18 21:15:00', 10, 0, NULL, '4,', NULL, 0, 0),
(7, '666', '0', '2021-03-18 21:15:00', '2021-04-18 21:15:00', 22, 0, NULL, '4,', NULL, 0, 0),
(8, '6662', '0', '2021-03-18 21:27:00', '2021-04-18 21:27:00', 11, 0, NULL, '4,', NULL, 0, 0),
(9, '66621', '0', '2021-03-18 21:27:00', '2021-04-18 21:27:00', 11, 0, NULL, '4,', '1,', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `contest9`
--

CREATE TABLE IF NOT EXISTS `contest9` (
  `userid` int(16) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 转存表中的数据 `contest9`
--

REPLACE INTO `contest9` (`userid`) VALUES
(1),
(4);

-- --------------------------------------------------------

--
-- 表的结构 `examine`
--

CREATE TABLE IF NOT EXISTS `examine` (
  `index1` int(16) NOT NULL AUTO_INCREMENT,
  `userid` int(16) NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` int(16) DEFAULT NULL,
  `id` int(16) DEFAULT NULL,
  PRIMARY KEY (`index1`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `judge`
--

CREATE TABLE IF NOT EXISTS `judge` (
  `userid` int(16) NOT NULL,
  `partake` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createpartake` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 转存表中的数据 `judge`
--

REPLACE INTO `judge` (`userid`, `partake`, `createpartake`) VALUES
(1, '', NULL),
(4, '', '4,7,8,9,');

-- --------------------------------------------------------

--
-- 表的结构 `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `WeChatCode` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `level` int(11) NOT NULL,
  `nickname` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 转存表中的数据 `users`
--

REPLACE INTO `users` (`userid`, `name`, `password`, `WeChatCode`, `level`, `nickname`) VALUES
(1, 'guest', '098f6bcd4621d373cade4e832627b4f6', '', 1, 'guest'),
(4, 'judge', '1562eb3f6d9c5ac7e159c04a96ff4dfe', NULL, 2, 'judge');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
