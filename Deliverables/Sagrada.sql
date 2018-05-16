-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 16, 2018 alle 22:34
-- Versione del server: 5.7.17
-- Versione PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sagrada`
--
CREATE DATABASE IF NOT EXISTS `sagrada` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `sagrada`;

-- --------------------------------------------------------

--
-- Struttura della tabella `assignedobjectives`
--

CREATE TABLE `assignedobjectives` (
  `Username` varchar(100) NOT NULL,
  `PlayID` int(11) NOT NULL,
  `ObjectiveID` int(11) NOT NULL,
  `Score` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `assignedtools`
--

CREATE TABLE `assignedtools` (
  `Username` varchar(100) NOT NULL,
  `PlayID` int(11) NOT NULL,
  `ToolD` int(11) NOT NULL,
  `Used` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `objective`
--

CREATE TABLE `objective` (
  `ID` int(11) NOT NULL,
  `ObjectiveCard` blob NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `play`
--

CREATE TABLE `play` (
  `ID` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Duration` bigint(20) NOT NULL,
  `PlayersNumber` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `playrecord`
--

CREATE TABLE `playrecord` (
  `Username` varchar(100) NOT NULL,
  `PlayID` int(11) NOT NULL,
  `Score` int(11) NOT NULL,
  `PlayJoiningDate` date NOT NULL,
  `ChoseWinowID` int(11) NOT NULL,
  `WindowMatrixState` blob NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `tool`
--

CREATE TABLE `tool` (
  `ID` int(11) NOT NULL,
  `ToolCard` blob NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `Username` varchar(100) NOT NULL,
  `Password` text NOT NULL,
  `SubscriptionDate` date NOT NULL,
  `Email` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `assignedobjectives`
--
ALTER TABLE `assignedobjectives`
  ADD PRIMARY KEY (`Username`,`PlayID`,`ObjectiveID`);

--
-- Indici per le tabelle `assignedtools`
--
ALTER TABLE `assignedtools`
  ADD PRIMARY KEY (`Username`,`PlayID`,`ToolD`);

--
-- Indici per le tabelle `objective`
--
ALTER TABLE `objective`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `play`
--
ALTER TABLE `play`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `playrecord`
--
ALTER TABLE `playrecord`
  ADD PRIMARY KEY (`Username`,`PlayID`);

--
-- Indici per le tabelle `tool`
--
ALTER TABLE `tool`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Username`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `play`
--
ALTER TABLE `play`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
