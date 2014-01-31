CREATE DATABASE  IF NOT EXISTS `TravelDreamDB` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `TravelDreamDB`;
-- MySQL dump 10.13  Distrib 5.5.35, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: TravelDreamDB
-- ------------------------------------------------------
-- Server version	5.5.35-0ubuntu0.12.04.2

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
-- Table structure for table `Aereo`
--

DROP TABLE IF EXISTS `Aereo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Aereo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Decollo` varchar(45) NOT NULL,
  `Atterraggio` varchar(45) NOT NULL,
  `Data` datetime NOT NULL,
  `Posti_Disponibili` int(11) NOT NULL DEFAULT '0',
  `Costo` float NOT NULL DEFAULT '0',
  `Valido` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Aereo`
--

LOCK TABLES `Aereo` WRITE;
/*!40000 ALTER TABLE `Aereo` DISABLE KEYS */;
INSERT INTO `Aereo` VALUES (1,'Roma','Milano','2014-02-16 05:13:00',100,34,1),(2,'Milano','Roma','2014-02-22 00:00:00',100,35,1);
/*!40000 ALTER TABLE `Aereo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Gruppo`
--

DROP TABLE IF EXISTS `Gruppo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Gruppo` (
  `nome` varchar(40) NOT NULL,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Gruppo`
--

LOCK TABLES `Gruppo` WRITE;
/*!40000 ALTER TABLE `Gruppo` DISABLE KEYS */;
INSERT INTO `Gruppo` VALUES ('AMMINISTRATORE'),('DIPENDENTE'),('UTENTE');
/*!40000 ALTER TABLE `Gruppo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Prenotazione_Viaggio`
--

DROP TABLE IF EXISTS `Prenotazione_Viaggio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Prenotazione_Viaggio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Data` datetime NOT NULL,
  `id_Aereo_Andata` int(11) DEFAULT NULL,
  `id_Aereo_Ritorno` int(11) DEFAULT NULL,
  `id_Hotel` int(11) DEFAULT NULL,
  `id_Utente` varchar(45) NOT NULL,
  `Check_In_Hotel` datetime DEFAULT NULL,
  `Check_Out_Hotel` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Prenotazione_Viaggio_Utente` (`id_Utente`),
  KEY `fk_Prenotazione_Viaggio_Aereo_Andata` (`id_Aereo_Andata`),
  KEY `fk_Prenotazione_Viaggio_Aereo_Ritorno` (`id_Aereo_Ritorno`),
  KEY `fk_Prenotazione_Viaggio_Hotel` (`id_Hotel`),
  CONSTRAINT `fk_Prenotazione_Viaggio_Aereo_Andata` FOREIGN KEY (`id_Aereo_Andata`) REFERENCES `Aereo` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Viaggio_Aereo_Ritorno` FOREIGN KEY (`id_Aereo_Ritorno`) REFERENCES `Aereo` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Viaggio_Hotel` FOREIGN KEY (`id_Hotel`) REFERENCES `Hotel` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Viaggio_Utente` FOREIGN KEY (`id_Utente`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Prenotazione_Viaggio`
--

LOCK TABLES `Prenotazione_Viaggio` WRITE;
/*!40000 ALTER TABLE `Prenotazione_Viaggio` DISABLE KEYS */;
/*!40000 ALTER TABLE `Prenotazione_Viaggio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Escursione`
--

DROP TABLE IF EXISTS `Escursione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Escursione` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Luogo` varchar(45) NOT NULL,
  `Prezzo` float NOT NULL,
  `Data` datetime NOT NULL,
  `Descrizione` varchar(500) NOT NULL,
  `Valido` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Escursione`
--

LOCK TABLES `Escursione` WRITE;
/*!40000 ALTER TABLE `Escursione` DISABLE KEYS */;
INSERT INTO `Escursione` VALUES (1,'Milano',13,'2014-02-19 00:00:00','Gita al Duomo di Milano',1);
/*!40000 ALTER TABLE `Escursione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Utente`
--

DROP TABLE IF EXISTS `Utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Utente` (
  `Username` varchar(45) NOT NULL,
  `Password` varchar(128) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Telefono` varchar(40) NOT NULL,
  `id_Anagrafica` varchar(16) NOT NULL,
  `id_Amministratore` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Username`),
  KEY `fk_Utente_Registrato_Anagrafica` (`id_Anagrafica`),
  KEY `fk_Utente_Amministratore_Creatore` (`id_Amministratore`),
  CONSTRAINT `fk_Utente_Amministratore_Creatore` FOREIGN KEY (`id_Amministratore`) REFERENCES `Utente` (`Username`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Utente_Registrato_Anagrafica` FOREIGN KEY (`id_Anagrafica`) REFERENCES `Anagrafica` (`CF`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Utente`
--

LOCK TABLES `Utente` WRITE;
/*!40000 ALTER TABLE `Utente` DISABLE KEYS */;
INSERT INTO `Utente` VALUES ('admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','admin@gmail.com','034858329','dmndmn91d22d222w',NULL),('mario','59195c6c541c8307f1da2d1e768d6f2280c984df217ad5f4c64c3542b04111a4','mario@mario.com','039564567','mrrrss84a04i628w','admin');
/*!40000 ALTER TABLE `Utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Gruppo_Utente`
--

DROP TABLE IF EXISTS `Gruppo_Utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Gruppo_Utente` (
  `id_Utente` varchar(45) NOT NULL,
  `id_Gruppo` varchar(40) NOT NULL,
  PRIMARY KEY (`id_Utente`,`id_Gruppo`),
  KEY `fk_Utente_has_Gruppo_Gruppo1` (`id_Gruppo`),
  KEY `fk_Utente_has_Gruppo_Utente1` (`id_Utente`),
  CONSTRAINT `fk_Utente_has_Gruppo_Gruppo1` FOREIGN KEY (`id_Gruppo`) REFERENCES `Gruppo` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Utente_has_Gruppo_Utente1` FOREIGN KEY (`id_Utente`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Gruppo_Utente`
--

LOCK TABLES `Gruppo_Utente` WRITE;
/*!40000 ALTER TABLE `Gruppo_Utente` DISABLE KEYS */;
INSERT INTO `Gruppo_Utente` VALUES ('admin','AMMINISTRATORE'),('mario','DIPENDENTE');
/*!40000 ALTER TABLE `Gruppo_Utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Anagrafica`
--

DROP TABLE IF EXISTS `Anagrafica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Anagrafica` (
  `CF` varchar(16) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  `Data_Nascita` date NOT NULL,
  `Luogo_Nascita` varchar(45) NOT NULL,
  `Residenza` varchar(45) NOT NULL,
  PRIMARY KEY (`CF`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Anagrafica`
--

LOCK TABLES `Anagrafica` WRITE;
/*!40000 ALTER TABLE `Anagrafica` DISABLE KEYS */;
INSERT INTO `Anagrafica` VALUES ('dmndmn91d22d222w','admin','admin','1970-01-30','Milano','Milano'),('mrrrss84a04i628w','Mario','Rossi','1984-01-04','Milano','Milano');
/*!40000 ALTER TABLE `Anagrafica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Escursioni_in_Prenotazione_Pacchetto`
--

DROP TABLE IF EXISTS `Escursioni_in_Prenotazione_Pacchetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Escursioni_in_Prenotazione_Pacchetto` (
  `id_Prenotazione` int(11) NOT NULL,
  `id_Escursione` int(11) NOT NULL,
  PRIMARY KEY (`id_Prenotazione`,`id_Escursione`),
  KEY `fk_Prenotazione_Pacchetto_has_Escursione_Escursione1` (`id_Escursione`),
  KEY `fk_Prenotazione_Pacchetto_has_Escursione_Prenotazione_Pacchet1` (`id_Prenotazione`),
  CONSTRAINT `fk_Prenotazione_Pacchetto_has_Escursione_Escursione1` FOREIGN KEY (`id_Escursione`) REFERENCES `Escursione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Pacchetto_has_Escursione_Prenotazione_Pacchet1` FOREIGN KEY (`id_Prenotazione`) REFERENCES `Prenotazione_Pacchetto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Escursioni_in_Prenotazione_Pacchetto`
--

LOCK TABLES `Escursioni_in_Prenotazione_Pacchetto` WRITE;
/*!40000 ALTER TABLE `Escursioni_in_Prenotazione_Pacchetto` DISABLE KEYS */;
/*!40000 ALTER TABLE `Escursioni_in_Prenotazione_Pacchetto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pacchetto`
--

DROP TABLE IF EXISTS `Pacchetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pacchetto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Destinazione` varchar(45) NOT NULL,
  `Inizio_Validita` date NOT NULL,
  `Fine_Validita` date NOT NULL,
  `id_Dipendente` varchar(45) NOT NULL,
  `Descrizione` varchar(500) NOT NULL,
  `Numero_Persone` int(11) NOT NULL,
  `Valido` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_Pacchetto_Dipendente1` (`id_Dipendente`),
  CONSTRAINT `fk_Pacchetto_Dipendente1` FOREIGN KEY (`id_Dipendente`) REFERENCES `Utente` (`Username`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pacchetto`
--

LOCK TABLES `Pacchetto` WRITE;
/*!40000 ALTER TABLE `Pacchetto` DISABLE KEYS */;
INSERT INTO `Pacchetto` VALUES (1,'Milano','2014-02-01','2014-02-28','mario','Alla scoperta di Milano: un viaggio nella spendida città italiana',3,1);
/*!40000 ALTER TABLE `Pacchetto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Condivisione`
--

DROP TABLE IF EXISTS `Condivisione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Condivisione` (
  `Link` varchar(100) NOT NULL,
  `Data` datetime NOT NULL,
  `id_Utente` varchar(45) NOT NULL,
  `id_Prenotazione` int(11) NOT NULL,
  PRIMARY KEY (`Link`),
  KEY `fk_Condivisione_Pacchetto` (`id_Prenotazione`),
  KEY `fk_Condivisione_Utente_Registrato1` (`id_Utente`),
  CONSTRAINT `fk_Condivisione_Pacchetto` FOREIGN KEY (`id_Prenotazione`) REFERENCES `Prenotazione_Pacchetto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Condivisione_Utente_Registrato1` FOREIGN KEY (`id_Utente`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Condivisione`
--

LOCK TABLES `Condivisione` WRITE;
/*!40000 ALTER TABLE `Condivisione` DISABLE KEYS */;
/*!40000 ALTER TABLE `Condivisione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Citta` varchar(45) NOT NULL,
  `Camere_Disponibili` int(11) NOT NULL,
  `Nome` varchar(50) NOT NULL,
  `Stelle` int(11) NOT NULL,
  `Costo_Giornaliero` float NOT NULL,
  `Valido` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

LOCK TABLES `Hotel` WRITE;
/*!40000 ALTER TABLE `Hotel` DISABLE KEYS */;
INSERT INTO `Hotel` VALUES (1,'Milano',34,'Hotel La Scala',5,23,1);
/*!40000 ALTER TABLE `Hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Hotel_in_Pacchetto`
--

DROP TABLE IF EXISTS `Hotel_in_Pacchetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel_in_Pacchetto` (
  `id_Hotel` int(11) NOT NULL,
  `id_Pacchetto` int(11) NOT NULL,
  PRIMARY KEY (`id_Hotel`,`id_Pacchetto`),
  KEY `fk_Hotel_has_Pacchetto_Pacchetto1` (`id_Pacchetto`),
  KEY `fk_Hotel_has_Pacchetto_Hotel1` (`id_Hotel`),
  CONSTRAINT `fk_Hotel_has_Pacchetto_Hotel1` FOREIGN KEY (`id_Hotel`) REFERENCES `Hotel` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Hotel_has_Pacchetto_Pacchetto1` FOREIGN KEY (`id_Pacchetto`) REFERENCES `Pacchetto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel_in_Pacchetto`
--

LOCK TABLES `Hotel_in_Pacchetto` WRITE;
/*!40000 ALTER TABLE `Hotel_in_Pacchetto` DISABLE KEYS */;
INSERT INTO `Hotel_in_Pacchetto` VALUES (1,1);
/*!40000 ALTER TABLE `Hotel_in_Pacchetto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Escursioni_in_Prenotazione_Viaggio`
--

DROP TABLE IF EXISTS `Escursioni_in_Prenotazione_Viaggio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Escursioni_in_Prenotazione_Viaggio` (
  `id_Escursione` int(11) NOT NULL,
  `id_Prenotazione` int(11) NOT NULL,
  PRIMARY KEY (`id_Escursione`,`id_Prenotazione`),
  KEY `fk_Escursione_has_Prenotazione_Viaggio_Prenotazione_Viaggio1` (`id_Prenotazione`),
  KEY `fk_Escursione_has_Prenotazione_Viaggio_Escursione1` (`id_Escursione`),
  CONSTRAINT `fk_Escursione_has_Prenotazione_Viaggio_Escursione1` FOREIGN KEY (`id_Escursione`) REFERENCES `Escursione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Escursione_has_Prenotazione_Viaggio_Prenotazione_Viaggio1` FOREIGN KEY (`id_Prenotazione`) REFERENCES `Prenotazione_Viaggio` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Escursioni_in_Prenotazione_Viaggio`
--

LOCK TABLES `Escursioni_in_Prenotazione_Viaggio` WRITE;
/*!40000 ALTER TABLE `Escursioni_in_Prenotazione_Viaggio` DISABLE KEYS */;
/*!40000 ALTER TABLE `Escursioni_in_Prenotazione_Viaggio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Prenotazione_Pacchetto`
--

DROP TABLE IF EXISTS `Prenotazione_Pacchetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Prenotazione_Pacchetto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Data` datetime NOT NULL,
  `id_Aereo_Andata` int(11) NOT NULL,
  `id_Aereo_Ritorno` int(11) NOT NULL,
  `id_Hotel` int(11) NOT NULL,
  `id_Utente` varchar(45) NOT NULL,
  `id_Pacchetto` int(11) NOT NULL,
  `Check_In_Hotel` datetime DEFAULT NULL,
  `Check_Out_Hotel` datetime DEFAULT NULL,
  `NumeroPersone` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_Prenotazione_Utente_Registrato1` (`id_Utente`),
  KEY `fk_Prenotazione_Aereo_Andata` (`id_Aereo_Andata`),
  KEY `fk_Prenotazione_Aereo_Ritorno` (`id_Aereo_Ritorno`),
  KEY `fk_Prenotazione_Hotel` (`id_Hotel`),
  KEY `fk_Prenotazione_Pacchetto_Pacchetto1` (`id_Pacchetto`),
  CONSTRAINT `fk_Prenotazione_Aereo_Andata` FOREIGN KEY (`id_Aereo_Andata`) REFERENCES `Aereo` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Aereo_Ritorno` FOREIGN KEY (`id_Aereo_Ritorno`) REFERENCES `Aereo` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Hotel` FOREIGN KEY (`id_Hotel`) REFERENCES `Hotel` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Pacchetto_Pacchetto1` FOREIGN KEY (`id_Pacchetto`) REFERENCES `Pacchetto` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Utente_Registrato1` FOREIGN KEY (`id_Utente`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Prenotazione_Pacchetto`
--

LOCK TABLES `Prenotazione_Pacchetto` WRITE;
/*!40000 ALTER TABLE `Prenotazione_Pacchetto` DISABLE KEYS */;
/*!40000 ALTER TABLE `Prenotazione_Pacchetto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Aereo_in_Pacchetto`
--

DROP TABLE IF EXISTS `Aereo_in_Pacchetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Aereo_in_Pacchetto` (
  `id_Volo` int(11) NOT NULL,
  `id_Pacchetto` int(11) NOT NULL,
  PRIMARY KEY (`id_Volo`,`id_Pacchetto`),
  KEY `fk_Aereo_has_Pacchetto_Pacchetto1` (`id_Pacchetto`),
  KEY `fk_Aereo_has_Pacchetto_Aereo1` (`id_Volo`),
  CONSTRAINT `fk_Aereo_has_Pacchetto_Aereo1` FOREIGN KEY (`id_Volo`) REFERENCES `Aereo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Aereo_has_Pacchetto_Pacchetto1` FOREIGN KEY (`id_Pacchetto`) REFERENCES `Pacchetto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Aereo_in_Pacchetto`
--

LOCK TABLES `Aereo_in_Pacchetto` WRITE;
/*!40000 ALTER TABLE `Aereo_in_Pacchetto` DISABLE KEYS */;
INSERT INTO `Aereo_in_Pacchetto` VALUES (1,1),(2,1);
/*!40000 ALTER TABLE `Aereo_in_Pacchetto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Escursione_in_Pacchetto`
--

DROP TABLE IF EXISTS `Escursione_in_Pacchetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Escursione_in_Pacchetto` (
  `id_Escursione` int(11) NOT NULL,
  `id_Pacchetto` int(11) NOT NULL,
  PRIMARY KEY (`id_Escursione`,`id_Pacchetto`),
  KEY `fk_Escursione_has_Pacchetto_Pacchetto1` (`id_Pacchetto`),
  KEY `fk_Escursione_has_Pacchetto_Escursione1` (`id_Escursione`),
  CONSTRAINT `fk_Escursione_has_Pacchetto_Escursione1` FOREIGN KEY (`id_Escursione`) REFERENCES `Escursione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Escursione_has_Pacchetto_Pacchetto1` FOREIGN KEY (`id_Pacchetto`) REFERENCES `Pacchetto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Escursione_in_Pacchetto`
--

LOCK TABLES `Escursione_in_Pacchetto` WRITE;
/*!40000 ALTER TABLE `Escursione_in_Pacchetto` DISABLE KEYS */;
INSERT INTO `Escursione_in_Pacchetto` VALUES (1,1);
/*!40000 ALTER TABLE `Escursione_in_Pacchetto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-31 14:28:28
