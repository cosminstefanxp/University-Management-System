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
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'fd',
  `cod_disciplina` int(11) NOT NULL,
  `cnp_cadru_didactic` varchar(13) NOT NULL,
  `tip` enum('Curs','Seminar','Laborator','Proiect') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cod_disciplina` (`cod_disciplina`,`cnp_cadru_didactic`),
  KEY `cnp_cadru_didactic` (`cnp_cadru_didactic`),
  CONSTRAINT `activitate_ibfk_1` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activitate_ibfk_2` FOREIGN KEY (`cnp_cadru_didactic`) REFERENCES `utilizatori` (`cnp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activitate`
--

LOCK TABLES `activitate` WRITE;
/*!40000 ALTER TABLE `activitate` DISABLE KEYS */;
INSERT INTO `activitate` VALUES (1,1,'2890508035278','Curs'),(2,2,'1876398642864','Curs'),(3,7,'222','Curs'),(4,5,'2890508035278','Curs'),(5,4,'222','Curs'),(6,3,'222','Curs'),(7,6,'1876398642864','Curs'),(10,4,'555','Seminar'),(11,3,'555','Seminar'),(12,6,'1876398642864','Proiect'),(13,1,'666','Laborator'),(14,5,'555','Proiect'),(15,2,'1876398642864','Laborator'),(16,7,'666','Laborator'),(17,4,'666','Proiect'),(18,6,'666','Laborator');
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
  KEY `cnp_student` (`cnp_student`),
  CONSTRAINT `catalog_ibfk_1` FOREIGN KEY (`cnp_student`) REFERENCES `utilizatori` (`cnp`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catalog_ibfk_2` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disciplina`
--

LOCK TABLES `disciplina` WRITE;
/*!40000 ALTER TABLE `disciplina` DISABLE KEYS */;
INSERT INTO `disciplina` VALUES (1,'Inteligenta Artificiala','Obligatoriu',5,5,'Examen',4,1,0),(2,'SPG','Obligatoriu',5,5,'Examen',4,1,0),(3,'MP','Optional',3,1,'Colocviu',4,1,1),(4,'BM','Optional',4,4,'Colocviu',4,1,1),(5,'Bioinginerie','Facultativ',1,4,'Colocviu',4,2,0),(6,'IP','Optional',5,5,'Examen',4,2,2),(7,'PR','Optional',1,5,'Examen',4,2,2);
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
  `ora` int(11) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `sala` varchar(10) NOT NULL,
  `grupa` varchar(6) NOT NULL,
  PRIMARY KEY (`cod_disciplina`,`grupa`,`data`),
  KEY `disciplina` (`cod_disciplina`),
  CONSTRAINT `examen_ibfk_1` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examen`
--

LOCK TABLES `examen` WRITE;
/*!40000 ALTER TABLE `examen` DISABLE KEYS */;
/*!40000 ALTER TABLE `examen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optiuni_contract`
--

DROP TABLE IF EXISTS `optiuni_contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `optiuni_contract` (
  `cod_disciplina` int(11) NOT NULL,
  `cnp_student` varchar(13) NOT NULL,
  `an_studiu` int(11) NOT NULL,
  PRIMARY KEY (`cod_disciplina`,`cnp_student`,`an_studiu`),
  KEY `cnp_student` (`cnp_student`),
  CONSTRAINT `optiuni_contract_ibfk_1` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `optiuni_contract_ibfk_2` FOREIGN KEY (`cnp_student`) REFERENCES `utilizatori` (`cnp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optiuni_contract`
--

LOCK TABLES `optiuni_contract` WRITE;
/*!40000 ALTER TABLE `optiuni_contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `optiuni_contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orar`
--

DROP TABLE IF EXISTS `orar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orar` (
  `zi` varchar(10) NOT NULL,
  `ora` int(11) NOT NULL,
  `sala` varchar(10) NOT NULL,
  `durata` int(11) NOT NULL,
  `id_activitate` int(11) NOT NULL,
  `frecventa` varchar(15) NOT NULL DEFAULT 'Saptamanal',
  `grupa` varchar(6) NOT NULL,
  PRIMARY KEY (`id_activitate`,`grupa`),
  KEY `activitate` (`id_activitate`),
  CONSTRAINT `orar_ibfk_1` FOREIGN KEY (`id_activitate`) REFERENCES `activitate` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizatori`
--

LOCK TABLES `utilizatori` WRITE;
/*!40000 ALTER TABLE `utilizatori` DISABLE KEYS */;
INSERT INTO `utilizatori` VALUES ('000','000','STUDENT','Georgescu','Mihai','mihai@cs.pub.ro','Buzau','','Buget',0),('111','111','SUPER_ADMIN','Super','Admin','admin@cs.pub.ro','blvd. Unirii, nr3','null','null',0),('1876398642864','florica','SEF_CATEDRA','Moldoveanu','Florica','florica@cs.pub.ro','Pitesti','','NULL',0),('222','222','SEF_CATEDRA','Tapus','Nicolae','sef@cs.pub.ro','Victoriei','dr. ing. ','null',0),('2890508035278','florica','CADRU_DIDACTIC','Florea','Adina','adina@cs.pub.ro','','ing.','NULL',0),('333','333','SECRETAR','Secretar','Maria','maria@cs.pub.ro','','','NULL',0),('444','444','STUDENT','Stefan','Cosmin','cosmin@cs.pub.ro','Pitesti','','Buget',0),('555','555','CADRU_DIDACTIC','Ionescu','Mihai','ionescu@cs.pub.ro','','dr.','NULL',0),('666','666','CADRU_DIDACTIC','Traian','Rebedea','rebedea@cs.pub.ro','Bucuresti','','NULL',0),('777','777','STUDENT','Rizea','Daniel','daniel@cs.pub.ro','','341C4','Taxa',0),('888','888','STUDENT','Cepoi','Mihai','mihai@cs.pub.ro','Buzau','','Taxa',0),('999','999','STUDENT','Bordei','Dan','dan@cs.pub.ro','','','Buget',0);
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

-- Dump completed on 2011-11-11 13:01:40
