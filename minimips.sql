CREATE DATABASE  IF NOT EXISTS `minimips` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `minimips`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: minimips
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `code`
--

DROP TABLE IF EXISTS `code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `instruction` varchar(45) NOT NULL,
  `opcode` varchar(45) NOT NULL,
  `hex` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code`
--

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
/*!40000 ALTER TABLE `code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cycle`
--

DROP TABLE IF EXISTS `cycle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cycle` (
  `cyclenum` int(11) NOT NULL AUTO_INCREMENT,
  `IF_IR` varchar(45) DEFAULT NULL,
  `IF_NPC` varchar(45) DEFAULT NULL,
  `IF_PC` varchar(45) DEFAULT NULL,
  `ID_IR` varchar(45) DEFAULT NULL,
  `ID_NPC` varchar(45) DEFAULT NULL,
  `ID_A` varchar(45) DEFAULT NULL,
  `ID_B` varchar(45) DEFAULT NULL,
  `ID_IMM` varchar(45) DEFAULT NULL,
  `EX_IR` varchar(45) DEFAULT NULL,
  `EX_B` varchar(45) DEFAULT NULL,
  `EX_ALU` varchar(45) DEFAULT NULL,
  `EX_COND` varchar(45) DEFAULT NULL,
  `MEM_IR` varchar(45) DEFAULT NULL,
  `MEM_ALU` varchar(45) DEFAULT NULL,
  `MEM_RANGE` varchar(45) DEFAULT NULL,
  `MEM_LMD` varchar(45) DEFAULT NULL,
  `WB_RN` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cyclenum`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cycle`
--

LOCK TABLES `cycle` WRITE;
/*!40000 ALTER TABLE `cycle` DISABLE KEYS */;
/*!40000 ALTER TABLE `cycle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memory`
--

DROP TABLE IF EXISTS `memory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `memory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(45) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1025 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memory`
--

LOCK TABLES `memory` WRITE;
/*!40000 ALTER TABLE `memory` DISABLE KEYS */;
/*!40000 ALTER TABLE `memory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regs`
--

DROP TABLE IF EXISTS `regs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regs` (
  `id` int(11) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regs`
--

LOCK TABLES `regs` WRITE;
/*!40000 ALTER TABLE `regs` DISABLE KEYS */;
INSERT INTO `regs` VALUES (0,'0000000000000000'),(1,'0000000000000000'),(2,'0000000000000000'),(3,'0000000000000000'),(4,'0000000000000000'),(5,'0000000000000000'),(6,'0000000000000000'),(7,'0000000000000000'),(8,'0000000000000000'),(9,'0000000000000000'),(10,'0000000000000000'),(11,'0000000000000000'),(12,'0000000000000000'),(13,'0000000000000000'),(14,'0000000000000000'),(15,'0000000000000000'),(16,'0000000000000000'),(17,'0000000000000000'),(18,'0000000000000000'),(19,'0000000000000000'),(20,'0000000000000000'),(21,'0000000000000000'),(22,'0000000000000000'),(23,'0000000000000000'),(24,'0000000000000000'),(25,'0000000000000000'),(26,'0000000000000000'),(27,'0000000000000000'),(28,'0000000000000000'),(29,'0000000000000000'),(30,'0000000000000000'),(31,'0000000000000000');
/*!40000 ALTER TABLE `regs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-11 22:29:01
