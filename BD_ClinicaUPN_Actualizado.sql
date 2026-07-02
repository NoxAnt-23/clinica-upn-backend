CREATE DATABASE  IF NOT EXISTS `clinica_upn_bd` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `clinica_upn_bd`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: clinica_upn_bd
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `id_cita` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int NOT NULL,
  `id_personal_salud` int DEFAULT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `modalidad` varchar(20) NOT NULL,
  `especialidad` varchar(100) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `estado` varchar(50) DEFAULT 'Pendiente de Pago',
  `sede` varchar(100) DEFAULT NULL,
  `consultorio` varchar(50) DEFAULT NULL,
  `enlace_sesion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_cita`),
  KEY `fk_cita_paciente1_idx` (`id_paciente`),
  KEY `fk_cita_personal_salud1_idx` (`id_personal_salud`),
  CONSTRAINT `fk_cita_paciente1` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id_paciente`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_cita_personal_salud1` FOREIGN KEY (`id_personal_salud`) REFERENCES `personal_salud` (`id_personal_salud`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` VALUES (11,3,4,'2026-06-26','11:30:00','Virtual','Medicina General',NULL,'En Espera','Sede SJL',NULL,NULL),(17,4,4,'2026-06-26','15:30:00','Presencial','Medicina General',NULL,'Pagado','Sede SJL',NULL,NULL),(23,4,7,'2026-07-03','08:24:00','Presencial','Medicina General',NULL,'En Espera',NULL,NULL,NULL),(25,4,7,'2026-07-01','16:44:00','Presencial','Medicina General',NULL,'Pagado',NULL,NULL,NULL),(28,4,7,'2026-07-03','17:57:00','Presencial','Medicina General',NULL,'Pagado','A','101',NULL),(29,4,7,'2026-07-03','05:56:00','Presencial','Medicina General',NULL,'En Espera','B','111',NULL),(30,3,4,'2026-07-09','11:00:00','Presencial','Medicina General',NULL,'En Espera','Sede SJL',NULL,NULL),(34,4,7,'2026-07-01','19:13:00','Presencial','Medicina General',NULL,'Pendiente de Pago','V','123',NULL);
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comprobante_pago`
--

DROP TABLE IF EXISTS `comprobante_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comprobante_pago` (
  `id_comprobante` int NOT NULL AUTO_INCREMENT,
  `id_cita` int DEFAULT NULL,
  `fecha_pago` datetime DEFAULT NULL,
  `monto` decimal(10,2) DEFAULT NULL,
  `metodo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_comprobante`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comprobante_pago`
--

LOCK TABLES `comprobante_pago` WRITE;
/*!40000 ALTER TABLE `comprobante_pago` DISABLE KEYS */;
INSERT INTO `comprobante_pago` VALUES (1,1,'2026-05-27 13:09:38',50.00,'Yape'),(2,3,'2026-05-27 13:31:35',50.00,'Yape'),(3,2,'2026-06-17 11:34:23',50.00,'Yape'),(4,4,'2026-06-17 11:34:38',50.00,'Yape'),(5,3,'2026-06-17 11:34:43',50.00,'Yape'),(6,5,'2026-06-17 12:24:13',50.00,'Yape'),(7,6,'2026-06-17 12:28:33',50.00,'Yape'),(8,7,'2026-06-17 12:35:10',50.00,'Yape'),(9,8,'2026-06-17 12:39:41',50.00,'Yape'),(10,9,'2026-06-17 12:44:01',50.00,'Yape'),(11,10,'2026-06-17 13:18:31',50.00,'Yape'),(12,11,'2026-06-17 20:03:15',50.00,'Yape'),(13,12,'2026-06-17 20:19:20',50.00,'Yape'),(14,13,'2026-06-22 22:52:04',50.00,'Yape'),(15,15,'2026-06-23 11:42:38',50.00,'Yape'),(16,16,'2026-06-23 12:09:18',50.00,'Yape'),(17,17,'2026-06-23 12:23:43',50.00,'Yape'),(18,18,'2026-06-24 20:32:15',50.00,'Yape'),(19,19,'2026-06-26 21:45:17',50.00,'Yape'),(20,20,'2026-07-01 01:08:28',50.00,'Yape'),(21,22,'2026-07-01 01:10:57',50.00,'Yape'),(22,23,'2026-07-01 16:18:35',50.00,'Yape'),(23,24,'2026-07-01 16:28:10',50.00,'Yape'),(24,25,'2026-07-01 16:43:47',50.00,'Yape'),(25,26,'2026-07-01 17:53:42',50.00,'Yape'),(26,27,'2026-07-01 17:57:40',50.00,'Yape'),(27,33,'2026-07-01 18:23:16',50.00,'Yape'),(28,30,'2026-07-01 18:38:07',50.00,'Yape'),(29,29,'2026-07-01 18:38:11',50.00,'Yape'),(30,28,'2026-07-01 19:09:11',50.00,'Yape');
/*!40000 ALTER TABLE `comprobante_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidad`
--

DROP TABLE IF EXISTS `especialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especialidad` (
  `id_especialidad` int NOT NULL AUTO_INCREMENT,
  `nombre_especialidad` varchar(50) NOT NULL,
  PRIMARY KEY (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidad`
--

LOCK TABLES `especialidad` WRITE;
/*!40000 ALTER TABLE `especialidad` DISABLE KEYS */;
INSERT INTO `especialidad` VALUES (1,'Medicina General'),(2,'Nutricion'),(3,'Obstetricia'),(4,'Psicologia'),(5,'Rehabilitacion'),(6,'Fisioterapia');
/*!40000 ALTER TABLE `especialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente` (
  `id_paciente` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `celular` varchar(20) DEFAULT '000000000',
  `direccion` varchar(255) DEFAULT 'No registrado',
  `sexo` varchar(1) DEFAULT '-',
  `fecha_nacimiento` date NOT NULL,
  `dni` varchar(8) NOT NULL,
  `correo` varchar(150) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_paciente`),
  UNIQUE KEY `dni_UNIQUE` (`dni`),
  KEY `fk_paciente_usuario1_idx` (`id_usuario`),
  CONSTRAINT `fk_paciente_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente`
--

LOCK TABLES `paciente` WRITE;
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` VALUES (3,4,'Cesar','Gonzales','987654321','No registrado','M','2005-03-21','76503245','cgonzales@upn.pe','2026-05-27 08:45:20'),(4,8,'Marco','Quispe','941143615','No registrado','M','2000-07-25','74044657','n00341352@upn.pe','2026-06-22 22:27:28');
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `id_pago` int NOT NULL AUTO_INCREMENT,
  `id_cita` int NOT NULL,
  `monto` decimal(8,2) NOT NULL,
  `metodo_pago` varchar(50) NOT NULL COMMENT 'Yape, Plin, Tarjeta Visa, Efectivo',
  `estado_pago` varchar(30) NOT NULL DEFAULT 'Pendiente' COMMENT 'Pendiente, Pagado, Reembolsado',
  `numero_operacion` varchar(100) DEFAULT NULL COMMENT 'Voucher o código de Yape',
  `fecha_pago` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_pago`),
  KEY `fk_pago_cita1_idx` (`id_cita`),
  CONSTRAINT `fk_pago_cita1` FOREIGN KEY (`id_cita`) REFERENCES `cita` (`id_cita`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_salud`
--

DROP TABLE IF EXISTS `personal_salud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personal_salud` (
  `id_personal_salud` int NOT NULL AUTO_INCREMENT,
  `id_especialidad` int DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `registro_profesional` varchar(20) DEFAULT NULL,
  `celular` varchar(15) DEFAULT NULL,
  `tipo_personal` varchar(30) NOT NULL,
  PRIMARY KEY (`id_personal_salud`),
  KEY `fk_personal_salud_especialidad_idx` (`id_especialidad`),
  KEY `fk_personal_salud_usuario1_idx` (`id_usuario`),
  CONSTRAINT `fk_personal_salud_especialidad` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id_especialidad`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_personal_salud_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_salud`
--

LOCK TABLES `personal_salud` WRITE;
/*!40000 ALTER TABLE `personal_salud` DISABLE KEYS */;
INSERT INTO `personal_salud` VALUES (3,NULL,5,'Paola','Ayala',NULL,NULL,'Psicología'),(4,NULL,7,'Cristhian','Rayo',NULL,NULL,'Medicina General'),(7,NULL,13,'Luis','Ramirez',NULL,NULL,'Practicante'),(8,NULL,14,'Goku','Son',NULL,NULL,'Practicante');
/*!40000 ALTER TABLE `personal_salud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte_clinico`
--

DROP TABLE IF EXISTS `reporte_clinico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte_clinico` (
  `id_reporte_clinico` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int NOT NULL,
  `id_personal_salud` int NOT NULL,
  `id_cita` int NOT NULL,
  `fecha_atencion` datetime NOT NULL,
  `diagnostico` text NOT NULL,
  `tratamiento` text NOT NULL,
  `fecha_creacion` datetime DEFAULT CURRENT_TIMESTAMP,
  `procedimientos` text COMMENT 'Ej: Sutura, Lavado gástrico, etc.',
  `examenes_laboratorio` text COMMENT 'Ej: Hemograma completo, Perfil lipídico, etc.',
  PRIMARY KEY (`id_reporte_clinico`),
  KEY `fk_reporte_clinico_paciente1_idx` (`id_paciente`),
  KEY `fk_reporte_clinico_personal_salud1_idx` (`id_personal_salud`),
  KEY `fk_reporte_clinico_cita1_idx` (`id_cita`),
  CONSTRAINT `fk_reporte_clinico_cita1` FOREIGN KEY (`id_cita`) REFERENCES `cita` (`id_cita`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_reporte_clinico_paciente1` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id_paciente`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_reporte_clinico_personal_salud1` FOREIGN KEY (`id_personal_salud`) REFERENCES `personal_salud` (`id_personal_salud`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte_clinico`
--

LOCK TABLES `reporte_clinico` WRITE;
/*!40000 ALTER TABLE `reporte_clinico` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporte_clinico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tokens_recuperacion`
--

DROP TABLE IF EXISTS `tokens_recuperacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tokens_recuperacion` (
  `id_token` int NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `id_usuario` int NOT NULL,
  `fecha_expiracion` datetime NOT NULL,
  PRIMARY KEY (`id_token`),
  UNIQUE KEY `token` (`token`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `tokens_recuperacion_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokens_recuperacion`
--

LOCK TABLES `tokens_recuperacion` WRITE;
/*!40000 ALTER TABLE `tokens_recuperacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tokens_recuperacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `triaje`
--

DROP TABLE IF EXISTS `triaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `triaje` (
  `id_triaje` int NOT NULL AUTO_INCREMENT,
  `id_cita` int NOT NULL,
  `peso` decimal(5,2) DEFAULT NULL COMMENT 'Peso en KG',
  `talla` decimal(4,2) DEFAULT NULL COMMENT 'Estatura en Metros',
  `temperatura` decimal(4,2) DEFAULT NULL COMMENT 'Grados Celsius',
  `presion_arterial` varchar(20) DEFAULT NULL COMMENT 'Ej. 120/80',
  `saturacion_oxigeno` int DEFAULT NULL COMMENT 'Porcentaje %',
  `motivo_urgencia` text,
  `fecha_triaje` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_triaje`),
  KEY `fk_triaje_cita1_idx` (`id_cita`),
  CONSTRAINT `fk_triaje_cita1` FOREIGN KEY (`id_cita`) REFERENCES `cita` (`id_cita`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `triaje`
--

LOCK TABLES `triaje` WRITE;
/*!40000 ALTER TABLE `triaje` DISABLE KEYS */;
/*!40000 ALTER TABLE `triaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `correo` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(20) NOT NULL,
  `cambio_pendiente` tinyint DEFAULT '0',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo_UNIQUE` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin@upn.pe','$2a$12$sVkuctan6H0yGSboEvas7.WAWfMQjY6NS6yUOPh4Jndt8ZsAKSNeO','ADMIN',0),(4,'cgonzales@upn.pe','$2a$10$xyYMjLWQxp.G/k/CBgdnx.LgY/FPtZHCE3SrBfIwuCMOQVAMYYeE.','paciente',0),(5,'payala@upn.pe','$2a$10$xyYMjLWQxp.G/k/CBgdnx.LgY/FPtZHCE3SrBfIwuCMOQVAMYYeE.','MEDICO',0),(7,'crayo@upn.pe','$2a$10$6TitSLuX3hltlcjLk456CuMYZwfEDext.KkVWBwY7XL6xlFL8wcG.','MEDICO',0),(8,'n00341352@upn.pe','$2a$10$ghIy3M1718UO/ZuIJF9br.24NOf4OTAtFbD2NEH4095wmK7OetiYe','paciente',0),(13,'practicante@upn.pe','$2a$10$xyYMjLWQxp.G/k/CBgdnx.LgY/FPtZHCE3SrBfIwuCMOQVAMYYeE.','PRACTICANTE',0),(14,'goku@upn.pe','$2a$10$TK8TN00MAE2fnkXulCkDgORIHV5PFHX4bdamy5/pQcpf4vohU5716','PRACTICANTE',0),(15,'asistente@upn.pe','$2a$12$sVkuctan6H0yGSboEvas7.WAWfMQjY6NS6yUOPh4Jndt8ZsAKSNeO','ASISTENTE',0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-01 19:37:41
