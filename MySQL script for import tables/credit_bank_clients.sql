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
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id_client` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `number_passport` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `main_bank` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_client`),
  KEY `FKbjxsfg5ly4fvhego3tomm8a1t` (`main_bank`),
  CONSTRAINT `FKbjxsfg5ly4fvhego3tomm8a1t` FOREIGN KEY (`main_bank`) REFERENCES `bank` (`id_bank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES ('5555dce1-0880-4044-ac4d-669bf15dc392','Петр','Петров','petrov@mail.ru','Петрович','1111 202333','89303555111',NULL),('9d63c020-7343-4fe3-b5e2-48e09f6f00d8','Антон','Антонов','antonov@mail.ru','Антонович','1802 705901','89707511333','13e4df11-a84b-4ace-a04e-1ce384f64320'),('baa916a3-bce8-4560-9e9c-c2984e120201','Иван','Иванов','ivanov@mail.ru','Иванович','1212 101103','89897750555','2670d375-8b75-4760-9b1a-0c167a0ac5a9'),('e743ee18-0880-4044-ac4d-669bf15dc392','Сергей','Сергеев','sergeev@mail.ru','Сергеевич','3618 909777','89275670701','13e4df11-a84b-4ace-a04e-1ce384f64320');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
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
