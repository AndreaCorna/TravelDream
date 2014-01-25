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
) ENGINE=InnoDB AUTO_INCREMENT=21245 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Aereo`
--

LOCK TABLES `Aereo` WRITE;
/*!40000 ALTER TABLE `Aereo` DISABLE KEYS */;
INSERT INTO `Aereo` VALUES (1,'Milano','Roma','2011-05-16 00:00:00',400,10,0),(2,'Roma','Milano','1998-03-04 00:00:00',100,10,1),(3,'cia','ciai','2013-12-08 00:00:00',20,20,1),(5,'Milano','Roma','2012-12-03 05:11:00',200,30,1),(8,'Milano','Roma','2011-01-18 00:00:00',15,12.2,1),(49,'Roma','Milano','2014-01-07 00:00:00',400,45,1),(56,'Roma','Milano','2014-01-01 00:00:00',100,14,1),(57,'Milano','Roma','2014-01-04 00:00:00',100,14,1),(59,'Roma','Bergamo','2013-12-10 00:00:00',200,59.9,1),(67,'milano','roma','2011-12-03 05:00:00',500,10,0),(100,'Milano','Roma','2014-02-01 00:00:00',250,12,1),(101,'Roma','Milano','2032-11-05 00:00:00',250,12,1),(21234,'Milano','Barcellona','2014-01-15 09:37:21',100,84,0),(21235,'Bergamo','Roma','2014-01-01 00:00:00',120,14,1),(21236,'Bergamo','Londra','2014-01-01 00:00:00',120,15,1),(21237,'Bergamo','Firenze','2014-01-25 00:00:00',150,14.2,1),(21238,'Barcellona','Stoccolma','2014-02-20 13:19:00',12,33.8,1),(21239,'Stoccolma','Parigi','2014-02-27 17:31:00',34,66.9,1),(21240,'Stoccolma','Parigi','2014-02-27 17:31:00',34,66.9,1),(21241,'Bergamo','Bari','2014-01-13 00:00:00',150,14,1),(21242,'Londra','Milano','2014-01-30 00:00:00',120,14,1),(21243,'Roma','Milano','2014-01-31 00:00:00',120,14,1),(21244,'Milano','Roma','2014-02-04 00:00:00',120,124,1);
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
  CONSTRAINT `fk_Prenotazione_Viaggio_Utente` FOREIGN KEY (`id_Utente`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Viaggio_Aereo_Andata` FOREIGN KEY (`id_Aereo_Andata`) REFERENCES `Aereo` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Viaggio_Aereo_Ritorno` FOREIGN KEY (`id_Aereo_Ritorno`) REFERENCES `Aereo` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Viaggio_Hotel` FOREIGN KEY (`id_Hotel`) REFERENCES `Hotel` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Prenotazione_Viaggio`
--

LOCK TABLES `Prenotazione_Viaggio` WRITE;
/*!40000 ALTER TABLE `Prenotazione_Viaggio` DISABLE KEYS */;
INSERT INTO `Prenotazione_Viaggio` VALUES (1,'2014-01-07 00:00:00',49,NULL,NULL,'utente',NULL,NULL),(2,'2014-01-22 07:22:25',21242,NULL,NULL,'ciao',NULL,NULL),(3,'2014-01-22 07:33:35',21242,NULL,NULL,'ciao',NULL,NULL),(4,'2014-01-22 12:16:28',21242,NULL,NULL,'ciao',NULL,NULL),(5,'2014-01-22 12:18:31',21242,NULL,NULL,'ciao',NULL,NULL),(6,'2014-01-22 12:19:41',21242,NULL,NULL,'ciao',NULL,NULL),(7,'2014-01-22 12:20:48',21242,NULL,NULL,'ciao',NULL,NULL),(8,'2014-01-22 12:22:25',21242,NULL,NULL,'ciao',NULL,NULL),(9,'2014-01-22 12:23:50',21242,NULL,NULL,'ciao',NULL,NULL),(10,'2014-01-24 13:23:33',21242,21243,100,'utente','2014-01-30 13:23:04',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=504 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Escursione`
--

LOCK TABLES `Escursione` WRITE;
/*!40000 ALTER TABLE `Escursione` DISABLE KEYS */;
INSERT INTO `Escursione` VALUES (1,'Roma',100,'2013-03-02 00:00:00','gita a roma come dico io',0),(3,'milano',100,'2013-12-02 00:00:00','gita a milano',1),(56,'Milano',14,'2014-01-02 00:00:00','Gita a milano',0),(498,'Milano',12,'2013-12-12 00:00:00','gita al duomo',1),(499,'Milano',14,'2014-01-10 00:00:00','OOOOO',1),(500,'Bergamo',14,'2014-01-13 00:00:00','GIta sui colli bergamaschi',1),(501,'Milano',10,'2014-01-03 00:00:00','Bo',1),(502,'Milano',10,'2014-01-06 00:00:00','Gota',1),(503,'Milano',14,'2014-02-01 00:00:00','Noicia',1);
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
INSERT INTO `Utente` VALUES ('admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','admin@amdmi.com','45425','aaabbb91b88i222w','admin'),('andrea','b133a0c0e9bee3be20163d2ad31d6248db292aa6dcb1ee087a2aa50e0fc75ae2','andrea.corna.ac.91@gmail.com','3407901064','crnndr91n44r787w',NULL),('ciao','b133a0c0e9bee3be20163d2ad31d6248db292aa6dcb1ee087a2aa50e0fc75ae2','andrea.corna@gmail.com','2222','bbbccc91b22i444r','admin'),('hummer','b133a0c0e9bee3be20163d2ad31d6248db292aa6dcb1ee087a2aa50e0fc75ae2','andra@gmail.com','15484','crnndr91b22i628w','admin'),('utente','44c2dbe2c24719aae64ed42989c9e3f1e504474d0f4871bc26bab6695f95d912','andrea@gmail.com','21525621','crnndr91c33o628e','admin');
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
INSERT INTO `Gruppo_Utente` VALUES ('admin','AMMINISTRATORE'),('ciao','DIPENDENTE'),('andrea','UTENTE'),('hummer','UTENTE'),('utente','UTENTE');
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
INSERT INTO `Anagrafica` VALUES ('aaabbb91b88i222w','Admin','Admin','2013-02-04','admin','serita'),('bbbccc91b22i444r','skdkja','jmgdzik','2013-12-01','Bergamo','dskjackna'),('crnndr91b22i628w','Andrea','Corna','2013-12-03','seriate','cisanoh'),('crnndr91c33o628e','a','a','2013-12-01','a','jsn'),('crnndr91n44r787w','Andrea','Corna','2013-12-01','ciao','cisano');
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
  CONSTRAINT `fk_Prenotazione_Pacchetto_has_Escursione_Prenotazione_Pacchet1` FOREIGN KEY (`id_Prenotazione`) REFERENCES `Prenotazione_Pacchetto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Pacchetto_has_Escursione_Escursione1` FOREIGN KEY (`id_Escursione`) REFERENCES `Escursione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Escursioni_in_Prenotazione_Pacchetto`
--

LOCK TABLES `Escursioni_in_Prenotazione_Pacchetto` WRITE;
/*!40000 ALTER TABLE `Escursioni_in_Prenotazione_Pacchetto` DISABLE KEYS */;
INSERT INTO `Escursioni_in_Prenotazione_Pacchetto` VALUES (3,56),(4,56),(5,56),(6,56),(7,56),(8,56),(9,56),(10,56),(11,56),(12,56),(13,56),(14,56),(15,501),(16,501),(17,501),(18,501),(19,501),(20,503),(21,503);
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
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pacchetto`
--

LOCK TABLES `Pacchetto` WRITE;
/*!40000 ALTER TABLE `Pacchetto` DISABLE KEYS */;
INSERT INTO `Pacchetto` VALUES (43,'milano','2013-01-01','2014-01-31','ciao','ciao',1,0),(54,'Milano','2004-01-12','2018-01-11','ciao','OOOOOOO',4,1),(56,'Milano','2013-12-31','2014-01-05','ciao','Pacchetto a milano',4,0),(57,'Milano','2005-01-12','2020-01-31','ciao','Prova autoincremento',3,1),(58,'Milano','2014-01-01','2016-01-30','ciao','ci',4,1),(59,'Milano','2014-01-01','2014-01-31','ciao','cc',4,1),(60,'Milano','2014-01-01','2014-01-31','ciao','cc',4,1),(61,'Milano','2014-01-23','2014-07-25','ciao','Prova modifica',4,1);
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
INSERT INTO `Condivisione` VALUES ('','2014-01-20 20:12:32','ciao',14),('cond54userciaodate11401','2014-01-13 21:00:54','ciao',8),('cond56userciaodate11400','2014-01-12 16:23:19','ciao',5),('cond56userciaodate11401','2014-01-13 14:09:30','ciao',6),('cond56userciaodate11402','2014-01-14 10:33:58','ciao',9),('cond56userutentedate11401','2014-01-20 13:18:48','utente',11),('cond57userciaodate11401','2014-01-13 18:08:07','ciao',7),('cond57userciaodate11402','2014-01-14 13:16:06','ciao',10),('cond59userciaodate11403','2014-01-22 12:52:11','ciao',18),('cond60userciaodate11402','2014-01-21 23:21:57','ciao',15),('cond61userciaodate11404','2014-01-23 20:49:26','ciao',21),('prova','2013-05-08 00:00:00','ciao',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

LOCK TABLES `Hotel` WRITE;
/*!40000 ALTER TABLE `Hotel` DISABLE KEYS */;
INSERT INTO `Hotel` VALUES (1,'Roma',1,'Ciao',5,0,1),(56,'Milano',14,'Hotel Milano',5,15,1),(100,'Milano',29,'HotelMilano',4,14,1),(101,'Milano',15,'HotelPdor',5,15,1),(102,'Ilio',50,'Hotel Ele',5,14,0),(103,'Bergamo',5,'HotelBergamo',1,14,1),(104,'Milano',14,'HotelProvaCamere',5,14,1),(105,'Milano',15,'HotelProvaCam',5,14,1);
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
INSERT INTO `Hotel_in_Pacchetto` VALUES (56,54),(100,54),(56,56),(100,56),(56,57),(100,57),(101,57),(56,58),(100,58),(101,58),(104,58),(56,59),(100,59),(101,59),(104,59),(56,60),(100,60),(101,60),(104,60),(56,61),(100,61),(101,61),(104,61),(105,61);
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
  CONSTRAINT `fk_Escursione_has_Prenotazione_Viaggio_Prenotazione_Viaggio1` FOREIGN KEY (`id_Prenotazione`) REFERENCES `Prenotazione_Viaggio` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Escursione_has_Prenotazione_Viaggio_Escursione1` FOREIGN KEY (`id_Escursione`) REFERENCES `Escursione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  CONSTRAINT `fk_Prenotazione_Utente_Registrato1` FOREIGN KEY (`id_Utente`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Aereo_Andata` FOREIGN KEY (`id_Aereo_Andata`) REFERENCES `Aereo` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Aereo_Ritorno` FOREIGN KEY (`id_Aereo_Ritorno`) REFERENCES `Aereo` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Hotel` FOREIGN KEY (`id_Hotel`) REFERENCES `Hotel` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_Prenotazione_Pacchetto_Pacchetto1` FOREIGN KEY (`id_Pacchetto`) REFERENCES `Pacchetto` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Prenotazione_Pacchetto`
--

LOCK TABLES `Prenotazione_Pacchetto` WRITE;
/*!40000 ALTER TABLE `Prenotazione_Pacchetto` DISABLE KEYS */;
INSERT INTO `Prenotazione_Pacchetto` VALUES (1,'2013-05-08 00:00:00',1,2,1,'ciao',43,NULL,NULL,1),(3,'2014-01-12 16:12:17',56,57,56,'ciao',56,NULL,NULL,1),(4,'2014-01-12 16:22:41',56,57,56,'ciao',56,NULL,NULL,1),(5,'2014-01-12 16:23:16',56,57,56,'ciao',56,NULL,NULL,1),(6,'2014-01-13 14:09:27',56,57,56,'ciao',56,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(7,'2014-01-13 18:07:59',56,57,56,'ciao',57,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(8,'2014-01-13 21:00:48',56,57,56,'ciao',54,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(9,'2014-01-14 10:33:51',56,57,56,'ciao',56,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(10,'2014-01-14 13:15:56',56,57,100,'ciao',57,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(11,'2014-01-20 13:18:41',56,57,56,'utente',56,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(12,'2014-01-20 13:45:26',56,57,56,'ciao',57,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(13,'2014-01-20 20:12:01',56,57,100,'ciao',57,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(14,'2014-01-20 20:12:01',56,57,100,'ciao',57,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(15,'2014-01-21 23:21:48',56,57,101,'ciao',60,'2014-01-01 00:00:00','2014-01-04 00:00:00',1),(16,'2014-01-21 23:44:00',56,57,101,'ciao',60,'2014-01-01 00:00:00','2014-01-04 00:00:00',3),(17,'2014-01-22 12:47:32',56,57,100,'ciao',59,'2014-01-01 00:00:00','2014-01-04 00:00:00',2),(18,'2014-01-22 12:52:05',56,57,100,'ciao',59,'2014-01-01 00:00:00','2014-01-04 00:00:00',2),(19,'2014-01-23 07:24:02',56,57,101,'ciao',59,'2014-01-01 00:00:00','2014-01-04 00:00:00',2),(20,'2014-01-23 14:42:03',21243,21244,100,'ciao',61,'2014-01-31 00:00:00','2014-02-04 00:00:00',2),(21,'2014-01-23 20:49:18',21243,21244,100,'ciao',61,'2014-01-31 00:00:00','2014-02-04 00:00:00',2);
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
INSERT INTO `Aereo_in_Pacchetto` VALUES (49,43),(21234,43),(1,54),(5,54),(8,54),(49,54),(56,54),(57,54),(100,54),(21234,54),(56,56),(57,56),(1,57),(5,57),(8,57),(49,57),(56,57),(57,57),(100,57),(21234,57),(49,58),(56,58),(57,58),(100,58),(56,59),(57,59),(56,60),(57,60),(21243,61),(21244,61);
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
INSERT INTO `Escursione_in_Pacchetto` VALUES (3,43),(498,43),(3,54),(498,54),(3,57),(498,57),(499,57),(499,58),(499,59),(501,59),(502,59),(499,60),(501,60),(502,60),(503,61);
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

-- Dump completed on 2014-01-26  0:43:40
