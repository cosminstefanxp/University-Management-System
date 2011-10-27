-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: Grupa342C4_StefanDobrinCosmin
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activitate`
--

DROP TABLE IF EXISTS `activitate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activitate` (
  `id` int(11) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `cnp_cadru_didactic` varchar(13) NOT NULL,
  `tip` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cadru_didactic` (`cnp_cadru_didactic`),
  KEY `disciplina` (`cod_disciplina`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activitate`
--

LOCK TABLES `activitate` WRITE;
/*!40000 ALTER TABLE `activitate` DISABLE KEYS */;
/*!40000 ALTER TABLE `activitate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalog`
--

DROP TABLE IF EXISTS `catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalog` (
  `cnp_student` varchar(13) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `nota` int(10) unsigned NOT NULL,
  `data` date NOT NULL,
  PRIMARY KEY (`cnp_student`,`cod_disciplina`,`data`),
  KEY `disciplina` (`cod_disciplina`),
  KEY `cnp_student` (`cnp_student`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalog`
--

LOCK TABLES `catalog` WRITE;
/*!40000 ALTER TABLE `catalog` DISABLE KEYS */;
/*!40000 ALTER TABLE `catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `disciplina` (
  `cod` int(11) NOT NULL,
  `denumire` varchar(45) NOT NULL,
  `tip` varchar(15) NOT NULL,
  `nr_ore` int(10) unsigned NOT NULL,
  `puncte_credit` int(10) unsigned NOT NULL,
  `examinare` varchar(15) NOT NULL,
  `an_studiu` int(10) unsigned NOT NULL,
  `semestru` int(10) unsigned NOT NULL,
  PRIMARY KEY (`cod`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disciplina`
--

LOCK TABLES `disciplina` WRITE;
/*!40000 ALTER TABLE `disciplina` DISABLE KEYS */;
/*!40000 ALTER TABLE `disciplina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examen`
--

DROP TABLE IF EXISTS `examen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examen` (
  `data` date NOT NULL,
  `ora` time NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `sala` varchar(10) NOT NULL,
  `grupa` varchar(6) NOT NULL,
  PRIMARY KEY (`cod_disciplina`,`grupa`,`data`),
  KEY `disciplina` (`cod_disciplina`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examen`
--

LOCK TABLES `examen` WRITE;
/*!40000 ALTER TABLE `examen` DISABLE KEYS */;
/*!40000 ALTER TABLE `examen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orar`
--

DROP TABLE IF EXISTS `orar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orar` (
  `zi` date NOT NULL,
  `interval` varchar(11) NOT NULL,
  `sala` varchar(10) NOT NULL,
  `id_activitate` int(11) NOT NULL,
  `frecventa` varchar(15) NOT NULL DEFAULT 'saptamanal',
  `grupa` varchar(6) NOT NULL,
  PRIMARY KEY (`zi`,`sala`,`interval`),
  KEY `activitate` (`id_activitate`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orar`
--

LOCK TABLES `orar` WRITE;
/*!40000 ALTER TABLE `orar` DISABLE KEYS */;
/*!40000 ALTER TABLE `orar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilizatori`
--

DROP TABLE IF EXISTS `utilizatori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utilizatori` (
  `cnp` varchar(13) NOT NULL,
  `parola` varchar(25) NOT NULL,
  `tip` varchar(10) NOT NULL,
  `nume` varchar(45) NOT NULL,
  `prenume` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `adresa` varchar(120) DEFAULT NULL,
  `titlu_grupa` varchar(10) DEFAULT NULL,
  `finantare` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`cnp`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizatori`
--

LOCK TABLES `utilizatori` WRITE;
/*!40000 ALTER TABLE `utilizatori` DISABLE KEYS */;
/*!40000 ALTER TABLE `utilizatori` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-10-27 15:48:39
