# Source on localhost: ... connected.
# Exporting metadata from Grupa342C4_StefanDobrinCosmin
DROP DATABASE IF EXISTS Grupa342C4_StefanDobrinCosmin;
CREATE DATABASE Grupa342C4_StefanDobrinCosmin;
USE Grupa342C4_StefanDobrinCosmin;
# TABLE: Grupa342C4_StefanDobrinCosmin.activitate
CREATE TABLE `activitate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cod_disciplina` int(11) NOT NULL,
  `cnp_cadru_didactic` varchar(13) NOT NULL,
  `tip` enum('Curs','Seminar','Laborator','Proiect') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cod_disciplina` (`cod_disciplina`,`cnp_cadru_didactic`),
  KEY `cnp_cadru_didactic` (`cnp_cadru_didactic`),
  CONSTRAINT `activitate_ibfk_2` FOREIGN KEY (`cnp_cadru_didactic`) REFERENCES `utilizatori` (`cnp`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activitate_ibfk_1` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12349 DEFAULT CHARSET=latin1;
# TABLE: Grupa342C4_StefanDobrinCosmin.catalog
CREATE TABLE `catalog` (
  `cnp_student` varchar(13) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `nota` int(10) unsigned NOT NULL,
  `data` date NOT NULL,
  PRIMARY KEY (`cnp_student`,`cod_disciplina`,`data`),
  KEY `disciplina` (`cod_disciplina`),
  KEY `cnp_student` (`cnp_student`),
  CONSTRAINT `catalog_ibfk_2` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catalog_ibfk_1` FOREIGN KEY (`cnp_student`) REFERENCES `utilizatori` (`cnp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
# TABLE: Grupa342C4_StefanDobrinCosmin.disciplina
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
) ENGINE=InnoDB AUTO_INCREMENT=12839430 DEFAULT CHARSET=latin1;
# TABLE: Grupa342C4_StefanDobrinCosmin.examen
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
# TABLE: Grupa342C4_StefanDobrinCosmin.optiuni_contract
CREATE TABLE `optiuni_contract` (
  `cnp_student` varchar(13) NOT NULL,
  `an_studiu` int(11) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  PRIMARY KEY (`cnp_student`,`an_studiu`,`cod_disciplina`),
  KEY `fk_contract_studiu_1` (`cnp_student`),
  KEY `cod_disciplina` (`cod_disciplina`),
  CONSTRAINT `optiuni_contract_ibfk_2` FOREIGN KEY (`cod_disciplina`) REFERENCES `disciplina` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `optiuni_contract_ibfk_1` FOREIGN KEY (`cnp_student`) REFERENCES `utilizatori` (`cnp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
# TABLE: Grupa342C4_StefanDobrinCosmin.orar
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
# TABLE: Grupa342C4_StefanDobrinCosmin.utilizatori
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
#...done.
