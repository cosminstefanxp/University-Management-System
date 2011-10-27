# Source on localhost: ... connected.
# Exporting metadata from Grupa342C4_StefanDobrinCosmin
DROP DATABASE IF EXISTS Grupa342C4_StefanDobrinCosmin;
CREATE DATABASE Grupa342C4_StefanDobrinCosmin;
USE Grupa342C4_StefanDobrinCosmin;
# TABLE: Grupa342C4_StefanDobrinCosmin.activitate
CREATE TABLE `activitate` (
  `id` int(11) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `cnp_cadru_didactic` varchar(13) NOT NULL,
  `tip` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cadru_didactic` (`cnp_cadru_didactic`),
  KEY `disciplina` (`cod_disciplina`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
# TABLE: Grupa342C4_StefanDobrinCosmin.catalog
CREATE TABLE `catalog` (
  `cnp_student` varchar(13) NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `nota` int(10) unsigned NOT NULL,
  `data` date NOT NULL,
  PRIMARY KEY (`cnp_student`,`cod_disciplina`,`data`),
  KEY `disciplina` (`cod_disciplina`),
  KEY `cnp_student` (`cnp_student`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
# TABLE: Grupa342C4_StefanDobrinCosmin.disciplina
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
# TABLE: Grupa342C4_StefanDobrinCosmin.examen
CREATE TABLE `examen` (
  `data` date NOT NULL,
  `ora` time NOT NULL,
  `cod_disciplina` int(11) NOT NULL,
  `sala` varchar(10) NOT NULL,
  `grupa` varchar(6) NOT NULL,
  PRIMARY KEY (`cod_disciplina`,`grupa`,`data`),
  KEY `disciplina` (`cod_disciplina`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
# TABLE: Grupa342C4_StefanDobrinCosmin.orar
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
# TABLE: Grupa342C4_StefanDobrinCosmin.utilizatori
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
#...done.
