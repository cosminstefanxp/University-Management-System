-- phpMyAdmin SQL Dump
-- version 3.3.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 09, 2011 at 12:51 AM
-- Server version: 5.1.54
-- PHP Version: 5.3.5-1ubuntu7.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Grupa342C4_StefanDobrinCosmin`
--

-- --------------------------------------------------------

--
-- Table structure for table `activitate`
--

CREATE TABLE IF NOT EXISTS `activitate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cod_disciplina` int(11) NOT NULL,
  `cnp_cadru_didactic` varchar(13) NOT NULL,
  `tip` enum('Curs','Seminar','Laborator','Proiect') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cod_disciplina` (`cod_disciplina`,`cnp_cadru_didactic`),
  KEY `cnp_cadru_didactic` (`cnp_cadru_didactic`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12349 ;

--
-- Dumping data for table `activitate`
--

INSERT INTO `activitate` (`id`, `cod_disciplina`, `cnp_cadru_didactic`, `tip`) VALUES
(2, 12839421, '222', 'Curs'),
(12345, 12839426, '555', 'Seminar'),
(12348, 12839426, '222', 'Curs');

-- --------------------------------------------------------

--
-- Table structure for table `catalog`
--

CREATE TABLE IF NOT EXISTS `catalog` (
  `cnp_student` varchar(13) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `nota` int(10) unsigned NOT NULL,
  `data` date NOT NULL,
  PRIMARY KEY (`cnp_student`,`cod_disciplina`,`data`),
  KEY `disciplina` (`cod_disciplina`),
  KEY `cnp_student` (`cnp_student`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `catalog`
--

INSERT INTO `catalog` (`cnp_student`, `cod_disciplina`, `nota`, `data`) VALUES
('666', 12839426, 5, '2011-06-08'),
('888', 12839421, 8, '2011-06-08');

-- --------------------------------------------------------

--
-- Table structure for table `disciplina`
--

CREATE TABLE IF NOT EXISTS `disciplina` (
  `cod` int(11) NOT NULL AUTO_INCREMENT,
  `denumire` varchar(45) NOT NULL,
  `tip` varchar(15) NOT NULL,
  `nr_ore` int(10) unsigned NOT NULL,
  `puncte_credit` int(10) unsigned NOT NULL,
  `examinare` varchar(15) NOT NULL,
  `an_studiu` int(10) unsigned NOT NULL,
  `semestru` int(10) unsigned NOT NULL,
  `grup` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cod`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12839430 ;

--
-- Dumping data for table `disciplina`
--

INSERT INTO `disciplina` (`cod`, `denumire`, `tip`, `nr_ore`, `puncte_credit`, `examinare`, `an_studiu`, `semestru`, `grup`) VALUES
(12839421, 'Programare', 'Optional', 14, 3, 'Examen', 2, 1, 1),
(12839424, 'Metode Numerice', 'Obligatoriu', 10, 3, 'Examen', 4, 2, 0),
(12839425, 'Analiza', 'Obligatoriu', 9, 4, 'Examen', 2, 1, 0),
(12839426, 'Baze de Date', 'Obligatoriu', 7, 4, 'Examen', 4, 2, 0),
(12839427, 'Protocoale', 'Facultativ', 19, 3, 'Examen', 3, 2, 0),
(12839429, 'USO', 'Optional', 4, 3, 'Examen', 5, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `examen`
--

CREATE TABLE IF NOT EXISTS `examen` (
  `data` date NOT NULL,
  `ora` int(11) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `sala` varchar(10) NOT NULL,
  `grupa` varchar(6) NOT NULL,
  PRIMARY KEY (`cod_disciplina`,`grupa`,`data`),
  KEY `disciplina` (`cod_disciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `examen`
--

INSERT INTO `examen` (`data`, `ora`, `cod_disciplina`, `sala`, `grupa`) VALUES
('2011-05-08', 11, 12839425, 'AN342', '342C4'),
('2011-11-01', 20, 12839427, 'BN101', '341C4'),
('2011-11-02', 8, 12839427, 'BN123', '341C4');

-- --------------------------------------------------------

--
-- Table structure for table `optiuni_contract`
--

CREATE TABLE IF NOT EXISTS `optiuni_contract` (
  `cnp_student` varchar(13) NOT NULL,
  `an_studiu` int(11) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  PRIMARY KEY (`cnp_student`,`an_studiu`,`cod_disciplina`),
  KEY `fk_contract_studiu_1` (`cnp_student`),
  KEY `cod_disciplina` (`cod_disciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `optiuni_contract`
--

INSERT INTO `optiuni_contract` (`cnp_student`, `an_studiu`, `cod_disciplina`) VALUES
('666', 2, 12839421);

-- --------------------------------------------------------

--
-- Table structure for table `orar`
--

CREATE TABLE IF NOT EXISTS `orar` (
  `zi` varchar(10) NOT NULL,
  `ora` int(11) NOT NULL,
  `sala` varchar(10) NOT NULL,
  `durata` int(11) NOT NULL,
  `id_activitate` int(11) NOT NULL,
  `frecventa` varchar(15) NOT NULL DEFAULT 'Saptamanal',
  `grupa` varchar(6) NOT NULL,
  PRIMARY KEY (`id_activitate`,`grupa`),
  KEY `activitate` (`id_activitate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orar`
--

INSERT INTO `orar` (`zi`, `ora`, `sala`, `durata`, `id_activitate`, `frecventa`, `grupa`) VALUES
('Luni', 8, 'dasdasdasd', 1, 2, 'Saptamanal', '342C4'),
('Luni', 8, 'dadsadsads', 1, 12345, 'Saptamanal', '342C4');

-- --------------------------------------------------------

--
-- Table structure for table `utilizatori`
--

CREATE TABLE IF NOT EXISTS `utilizatori` (
  `cnp` varchar(13) NOT NULL,
  `parola` varchar(25) NOT NULL,
  `tip` varchar(15) NOT NULL,
  `nume` varchar(45) NOT NULL,
  `prenume` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `adresa` varchar(120) DEFAULT NULL,
  `titlu_grupa` varchar(15) DEFAULT NULL,
  `finantare` varchar(10) DEFAULT NULL,
  `contract_completat` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`cnp`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utilizatori`
--

INSERT INTO `utilizatori` (`cnp`, `parola`, `tip`, `nume`, `prenume`, `email`, `adresa`, `titlu_grupa`, `finantare`, `contract_completat`) VALUES
('111', '111', 'SUPER_ADMIN', 'Super', 'Man', 'super@man.com', 'Pitesti', 'null', 'null', 0),
('1890508035278', 'cosmin', 'ADMIN', 'Stefan', 'Cosmin', 'cosminstefanxp@gmail.com', 'Victoriei, 10', 'dr. ', NULL, 0),
('222', '222', 'SEF_CATEDRA', 'Sef Catedra', 'Boss', 'boss@cs.pub.ro', 'Bucuresti', 'prof. ', 'null', 0),
('333', '333', 'ADMIN', 'Admin', 'Smecher', 'Email@', '', '', 'null', 0),
('444', '444', 'SECRETAR', 'Secretar', 'Cu valoare', 'valoare@mea.com', 'Pitesti', '', 'null', 0),
('555', '555', 'CADRU_DIDACTIC', 'Cadru', 'Didactic', 'Cadru@cs.pub.ro', 'Romania', '', 'NULL', 0),
('666', '666', 'STUDENT', 'Cosmin', 'Stefan', 'cosmin@feedrz.com', 'Pitesti', '342C4', 'Buget', 0),
('888', '888', 'STUDENT', 'Daniel', 'Voiculescu', 'dan@mail.com', '', '341C4', 'Buget', 0);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activitate`
--
ALTER TABLE `activitate`
  ADD CONSTRAINT `activitate_ibfk_2` FOREIGN KEY (`cnp_cadru_didactic`) REFERENCES `utilizatori` (`cnp`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activitate_ibfk_1` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `catalog`
--
ALTER TABLE `catalog`
  ADD CONSTRAINT `catalog_ibfk_2` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `catalog_ibfk_1` FOREIGN KEY (`cnp_student`) REFERENCES `utilizatori` (`cnp`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `examen`
--
ALTER TABLE `examen`
  ADD CONSTRAINT `examen_ibfk_1` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `optiuni_contract`
--
ALTER TABLE `optiuni_contract`
  ADD CONSTRAINT `optiuni_contract_ibfk_2` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `optiuni_contract_ibfk_1` FOREIGN KEY (`cnp_student`) REFERENCES `utilizatori` (`cnp`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `orar`
--
ALTER TABLE `orar`
  ADD CONSTRAINT `orar_ibfk_1` FOREIGN KEY (`id_activitate`) REFERENCES `activitate` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
