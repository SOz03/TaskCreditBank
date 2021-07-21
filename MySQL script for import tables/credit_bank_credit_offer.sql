CREATE DATABASE  IF NOT EXISTS `credit_bank` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `credit_bank`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: credit_bank
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `credit_offer`
--

DROP TABLE IF EXISTS `credit_offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_offer` (
  `id_credit_offer` varchar(255) NOT NULL,
  `amount_credit` decimal(10,0) DEFAULT NULL,
  `client` varchar(255) DEFAULT NULL,
  `credit` varchar(255) DEFAULT NULL,
  `bank` varchar(255) NOT NULL,
  PRIMARY KEY (`id_credit_offer`),
  KEY `FK9ihjlhbx08ivndfk45pqmcv8b` (`bank`),
  KEY `FKmusq1ba7r0rt62w2t2w7pia4h` (`client`),
  KEY `FK9mjv273cqolwqjs7gqqsvt2cf` (`credit`),
  CONSTRAINT `FK9ihjlhbx08ivndfk45pqmcv8b` FOREIGN KEY (`bank`) REFERENCES `bank` (`id_bank`),
  CONSTRAINT `FK9mjv273cqolwqjs7gqqsvt2cf` FOREIGN KEY (`credit`) REFERENCES `credits` (`id_credit`),
  CONSTRAINT `FKmusq1ba7r0rt62w2t2w7pia4h` FOREIGN KEY (`client`) REFERENCES `clients` (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_offer`
--

LOCK TABLES `credit_offer` WRITE;
/*!40000 ALTER TABLE `credit_offer` DISABLE KEYS */;
INSERT INTO `credit_offer` VALUES ('03bc016b-cdc7-4f3a-b14a-437f38f06ac4',535000,'9d63c020-7343-4fe3-b5e2-48e09f6f00d8','19d25086-2a6f-4e9d-a096-7fb1193ee5f8','13e4df11-a84b-4ace-a04e-1ce384f64320'),('db42be13-e90c-4e49-b00c-19dd7dd10c2b',1100000,'e743ee18-0880-4044-ac4d-669bf15dc392','2a1e6cf2-5fdc-41bd-9bc4-17f1b434dd1a','13e4df11-a84b-4ace-a04e-1ce384f64320'),('ff13434c-c4bc-4fe6-b6c7-0a806e02f1e0',1100000,'baa916a3-bce8-4560-9e9c-c2984e120201','2a1e6cf2-5fdc-41bd-9bc4-17f1b434dd1a','2670d375-8b75-4760-9b1a-0c167a0ac5a9');
/*!40000 ALTER TABLE `credit_offer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-21 23:42:24
