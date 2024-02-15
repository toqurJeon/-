-- MySQL dump 10.13  Distrib 8.1.0, for macos13 (arm64)
--
-- Host: i10a308.p.ssafy.io    Database: moiroom
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `characteristic`
--

DROP TABLE IF EXISTS `characteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `characteristic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) NOT NULL,
  `updated_at` varchar(255) NOT NULL,
  `activity` int DEFAULT NULL,
  `altruism` int DEFAULT NULL,
  `communion` int DEFAULT NULL,
  `empathy` int DEFAULT NULL,
  `generous` int DEFAULT NULL,
  `humor` int DEFAULT NULL,
  `positivity` int DEFAULT NULL,
  `sleep_at` varchar(255) DEFAULT NULL,
  `sociability` int DEFAULT NULL,
  `wake_up_at` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `characteristic`
--

LOCK TABLES `characteristic` WRITE;
/*!40000 ALTER TABLE `characteristic` DISABLE KEYS */;
INSERT INTO `characteristic` VALUES (19,'2024-02-08 16:47:11.544191','2024-02-09 10:35:59',0,5000,5000,0,5000,5000,5000,'23:57',5000,'06:30'),(20,'2024-02-09 17:24:42','2024-02-09 17:24:42',8698,1132,5541,1223,3985,6347,6656,'00:12',5123,'08:14'),(22,'2024-02-09 17:26:59','2024-02-09 17:26:59',9098,932,1041,5423,3115,6877,3456,'22:34',1233,'07:00'),(28,'2024-02-12 14:49:08','2024-02-13 11:00:02',0,5000,5000,0,5000,5000,5000,NULL,5000,NULL),(29,'2024-02-12 15:50:52','2024-02-15 16:15:00',0,1234,3814,1568,4538,8423,0,NULL,5678,NULL),(30,'2024-02-12 16:06:59','2024-02-15 16:45:28',1500,1234,3814,149,4538,8423,0,'22:00',5678,'06:30'),(31,'2024-02-12 16:21:12','2024-02-12 19:20:21',0,5000,5000,0,5000,5000,5000,NULL,5000,NULL),(33,'2024-02-13 16:56:29','2024-02-15 15:56:35',0,1234,3814,1568,4538,8423,0,NULL,5678,NULL),(34,'2024-02-14 04:13:06','2024-02-14 04:13:06',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(35,'2024-02-14 04:16:37','2024-02-14 04:16:37',5343,3093,2331,2434,4333,2245,3644,'9:00',3240,'10:23'),(36,'2024-02-14 04:16:37','2024-02-14 04:16:37',3242,1222,3333,1111,1231,2222,8736,'11:00',3245,'11:23'),(37,'2024-02-14 04:16:38','2024-02-14 04:16:38',5434,3843,1241,4434,4230,2122,3144,'12:00',3215,'10:23'),(38,'2024-02-14 04:16:38','2024-02-14 04:16:38',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(39,'2024-02-14 04:16:38','2024-02-14 04:16:38',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(40,'2024-02-14 04:16:38','2024-02-14 04:16:38',1234,1111,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(41,'2024-02-14 04:16:38','2024-02-14 04:16:38',1234,3333,2341,4434,2344,1112,3333,'10:00',3245,'12:23'),(42,'2024-02-14 04:16:38','2024-02-14 04:16:38',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(43,'2024-02-14 04:16:38','2024-02-14 04:16:38',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(44,'2024-02-14 04:16:38','2024-02-14 04:16:38',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(45,'2024-02-14 04:16:38','2024-02-14 04:16:38',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(46,'2024-02-14 04:16:38','2024-02-14 04:16:38',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(47,'2024-02-14 04:16:39','2024-02-14 04:16:39',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(48,'2024-02-14 04:16:39','2024-02-14 04:16:39',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(49,'2024-02-14 04:16:39','2024-02-14 04:16:39',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(50,'2024-02-14 04:16:39','2024-02-14 04:16:39',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(51,'2024-02-14 04:16:39','2024-02-14 04:16:39',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(52,'2024-02-14 04:16:39','2024-02-14 04:16:39',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(53,'2024-02-14 04:16:39','2024-02-14 04:16:39',1234,3333,2341,4434,4233,2222,3444,'10:00',3245,'12:23'),(59,'2024-02-15 16:15:40','2024-02-15 20:49:58',1000,5678,2134,4567,1234,9999,2222,NULL,5678,NULL);
/*!40000 ALTER TABLE `characteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) NOT NULL,
  `updated_at` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `chat_room_id` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsm2v24rnsi2qvcevgjescyob3` (`chat_room_id`,`member_id`),
  CONSTRAINT `FKsm2v24rnsi2qvcevgjescyob3` FOREIGN KEY (`chat_room_id`, `member_id`) REFERENCES `member_chat_room` (`chat_room_id`, `member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
INSERT INTO `chat_message` VALUES (52,'2024-02-15 17:10:51','2024-02-15 17:10:51','하하',69,17),(53,'2024-02-15 17:11:01','2024-02-15 17:11:01','안녕',69,17),(54,'2024-02-15 17:11:08','2024-02-15 17:11:08','안녕',69,18),(55,'2024-02-15 17:15:34','2024-02-15 17:15:34','하이',69,17),(56,'2024-02-15 17:16:16','2024-02-15 17:16:16','테스트',69,17),(57,'2024-02-15 17:24:47','2024-02-15 17:24:47','ㅎㅎㅎ',69,18),(58,'2024-02-15 17:24:54','2024-02-15 17:24:54','ddd',69,17),(59,'2024-02-15 17:25:08','2024-02-15 17:25:08','ㅎㅎㅎ',69,18),(60,'2024-02-15 17:25:15','2024-02-15 17:25:15','마지막',69,18),(61,'2024-02-15 17:59:23','2024-02-15 17:59:23','하하하',69,18),(62,'2024-02-15 17:59:51','2024-02-15 17:59:51','dkdkdk',69,17),(63,'2024-02-15 18:00:29','2024-02-15 18:00:29','sjdj',69,17),(64,'2024-02-15 18:00:46','2024-02-15 18:00:46','djdj',69,17),(65,'2024-02-15 18:01:16','2024-02-15 18:01:16','hgf',69,17),(66,'2024-02-15 18:01:21','2024-02-15 18:01:21','ㄷㄹㅎㅍ',69,18),(67,'2024-02-15 18:03:17','2024-02-15 18:03:17','하하',69,18),(68,'2024-02-15 18:03:33','2024-02-15 18:03:33','하하',69,18),(69,'2024-02-15 18:04:36','2024-02-15 18:04:36','하하하',69,18),(70,'2024-02-15 18:04:42','2024-02-15 18:04:42','후후후',69,18),(71,'2024-02-15 18:05:09','2024-02-15 18:05:09','달다라',69,17),(72,'2024-02-15 18:19:41','2024-02-15 18:19:41','아아라',69,18),(73,'2024-02-15 18:22:06','2024-02-15 18:22:06','하허하',69,17),(74,'2024-02-15 18:26:41','2024-02-15 18:26:41','ㅎㅎㅎㅎ',69,18),(75,'2024-02-15 18:31:20','2024-02-15 18:31:20','하하',69,17),(76,'2024-02-15 18:31:26','2024-02-15 18:31:26','팔',69,18),(77,'2024-02-15 18:34:34','2024-02-15 18:34:34','하하하',69,17),(78,'2024-02-15 18:34:43','2024-02-15 18:34:43','나나나',69,17),(79,'2024-02-15 18:35:57','2024-02-15 18:35:57','아아',69,17),(80,'2024-02-15 18:36:07','2024-02-15 18:36:07','ㅇㅇㅇ',69,18),(81,'2024-02-15 18:36:26','2024-02-15 18:36:26','ㅎㅎㅎ',69,17),(82,'2024-02-15 18:38:33','2024-02-15 18:38:33','하하하',69,17),(83,'2024-02-15 18:38:36','2024-02-15 18:38:36','유오ㅓ',69,17),(84,'2024-02-15 18:38:54','2024-02-15 18:38:54','얼',69,17),(85,'2024-02-15 18:39:01','2024-02-15 18:39:01','ㅠ듀듀',69,17),(86,'2024-02-15 18:39:20','2024-02-15 18:39:20','가라아',69,18),(87,'2024-02-15 18:39:30','2024-02-15 18:39:30','아아라',69,17),(88,'2024-02-15 21:36:17','2024-02-15 21:36:17','안녕',71,18),(89,'2024-02-15 21:41:00','2024-02-15 21:41:00','하이',71,43),(90,'2024-02-15 21:45:43','2024-02-15 21:45:43','하이요',71,43),(91,'2024-02-15 21:52:59','2024-02-15 21:52:59','하이',71,43),(92,'2024-02-15 22:16:24','2024-02-15 22:16:24','아앙',71,43),(93,'2024-02-15 22:16:29','2024-02-15 22:16:29','ㅇㅇㅇ',71,18),(94,'2024-02-15 22:17:47','2024-02-15 22:17:47','ㅎㅎㅎ',71,43),(95,'2024-02-15 22:20:00','2024-02-15 22:20:00','하이',71,18),(96,'2024-02-15 22:25:49','2024-02-15 22:25:49','아제발',71,43),(97,'2024-02-15 22:26:10','2024-02-15 22:26:10','아넌',71,43),(98,'2024-02-15 22:26:27','2024-02-15 22:26:27','아찰',71,43),(99,'2024-02-15 22:29:53','2024-02-15 22:29:53','아아',71,43),(100,'2024-02-15 22:34:38','2024-02-15 22:34:38','하이',71,43),(101,'2024-02-15 22:34:51','2024-02-15 22:34:51','하이',71,18),(102,'2024-02-15 22:35:44','2024-02-15 22:35:44','예전폰',71,43),(103,'2024-02-15 22:35:48','2024-02-15 22:35:48','지금폰',71,18),(104,'2024-02-15 22:38:49','2024-02-15 22:38:49','자자',71,18),(105,'2024-02-15 22:42:21','2024-02-15 22:42:21','아아아',71,18),(106,'2024-02-15 22:42:29','2024-02-15 22:42:29','아아',71,43),(107,'2024-02-15 22:42:31','2024-02-15 22:42:31','라라해',71,18),(108,'2024-02-15 22:53:08','2024-02-15 22:53:08','빠빠',71,43),(109,'2024-02-15 22:55:16','2024-02-15 22:55:16','따따',71,43),(110,'2024-02-15 23:00:27','2024-02-15 23:00:27','나나',71,18),(111,'2024-02-15 23:01:05','2024-02-15 23:01:05','하하',71,18),(112,'2024-02-15 23:10:09','2024-02-15 23:10:09','ㅠㅑ퍄퍄해',71,43),(113,'2024-02-15 23:10:15','2024-02-15 23:10:15','ㅗㅎㅎㅎ',71,43),(114,'2024-02-15 23:15:34','2024-02-15 23:15:34','다다다',71,43),(115,'2024-02-15 23:17:40','2024-02-15 23:17:40','하하하',71,18),(116,'2024-02-15 23:20:09','2024-02-15 23:20:09','빠빠',71,18),(117,'2024-02-15 23:21:03','2024-02-15 23:21:03','하핳',71,18),(118,'2024-02-15 23:42:28','2024-02-15 23:42:28','바밤',71,43),(119,'2024-02-15 23:42:32','2024-02-15 23:42:32','따따',71,43),(120,'2024-02-15 23:43:24','2024-02-15 23:43:24','해보자',71,43),(121,'2024-02-15 23:47:26','2024-02-15 23:47:26','안녕',71,43),(122,'2024-02-15 23:48:03','2024-02-15 23:48:03','안녕',71,18),(123,'2024-02-15 23:48:07','2024-02-15 23:48:07','왜',71,18),(124,'2024-02-15 23:49:35','2024-02-15 23:49:35','하이',71,18),(125,'2024-02-15 23:49:42','2024-02-15 23:49:42','안녕',71,43),(126,'2024-02-15 23:56:41','2024-02-15 23:56:41','안녕',71,18),(127,'2024-02-16 00:15:03','2024-02-16 00:15:03','와우',71,18),(128,'2024-02-16 00:18:23','2024-02-16 00:18:23','아아',71,18),(129,'2024-02-16 00:22:06','2024-02-16 00:22:06','안녕',71,18),(130,'2024-02-16 00:22:12','2024-02-16 00:22:12','안녕',71,43),(131,'2024-02-16 00:22:15','2024-02-16 00:22:15','나는',71,43),(132,'2024-02-16 00:22:21','2024-02-16 00:22:21','예전폰이야',71,43),(133,'2024-02-16 00:24:36','2024-02-16 00:24:36','안녕',71,18),(134,'2024-02-16 00:24:44','2024-02-16 00:24:44','난 지금폰이야',71,18),(135,'2024-02-16 00:24:49','2024-02-16 00:24:49','안녕',71,43),(136,'2024-02-16 00:27:33','2024-02-16 00:27:33','1',71,18),(137,'2024-02-16 00:27:36','2024-02-16 00:27:36','1',71,43),(138,'2024-02-16 00:30:08','2024-02-16 00:30:08','그래',71,43),(139,'2024-02-16 00:35:01','2024-02-16 00:35:01','하아',71,43),(140,'2024-02-16 00:35:06','2024-02-16 00:35:06','후',71,43),(141,'2024-02-16 00:37:23','2024-02-16 00:37:23','언제',71,18),(142,'2024-02-16 00:37:25','2024-02-16 00:37:25','되냐',71,18),(143,'2024-02-16 00:44:01','2024-02-16 00:44:01','안녕',71,18),(144,'2024-02-16 00:46:59','2024-02-16 00:46:59','왜',71,18),(145,'2024-02-16 00:47:01','2024-02-16 00:47:01','두개씩',71,18),(146,'2024-02-16 00:47:03','2024-02-16 00:47:03','나와',71,18),(147,'2024-02-16 00:47:06','2024-02-16 00:47:06','미치겠네',71,18),(148,'2024-02-16 00:48:41','2024-02-16 00:48:41','안녕',71,18),(149,'2024-02-16 00:50:41','2024-02-16 00:50:41','왜그러지',71,18),(150,'2024-02-16 00:52:59','2024-02-16 00:52:59','안녕',71,18),(151,'2024-02-16 00:55:18','2024-02-16 00:55:18','ㅗㅓㅓ',71,18);
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) NOT NULL,
  `updated_at` varchar(255) NOT NULL,
  `last_message` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
INSERT INTO `chat_room` VALUES (69,'2024-02-15 17:10:43','2024-02-15 18:39:30','아아라'),(70,'2024-02-15 18:05:51','2024-02-15 18:05:51','대화를 시작해보세요.'),(71,'2024-02-15 21:28:01','2024-02-16 00:55:18','ㅗㅓㅓ');
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `metropolitan_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK15uq30qgmdk9kv9dlw88480g8` (`metropolitan_id`),
  CONSTRAINT `FK15uq30qgmdk9kv9dlw88480g8` FOREIGN KEY (`metropolitan_id`) REFERENCES `metropolitan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,1,'종로구'),(2,1,'중구'),(3,1,'용산구'),(4,1,'성동구'),(5,1,'광진구'),(6,1,'동대문구'),(7,1,'중랑구'),(8,1,'성북구'),(9,1,'강북구'),(10,1,'도봉구'),(11,1,'노원구'),(12,1,'은평구'),(13,1,'서대문구'),(14,1,'마포구'),(15,1,'양천구'),(16,1,'강서구'),(17,1,'구로구'),(18,1,'금천구'),(19,1,'영등포구'),(20,1,'동작구'),(21,1,'관악구'),(22,1,'서초구'),(23,1,'강남구'),(24,1,'송파구'),(25,1,'강동구'),(26,2,'중구'),(27,2,'서구'),(28,2,'동구'),(29,2,'영도구'),(30,2,'부산진구'),(31,2,'동래구'),(32,2,'남구'),(33,2,'북구'),(34,2,'강서구'),(35,2,'해운대구'),(36,2,'사하구'),(37,2,'금정구'),(38,2,'연제구'),(39,2,'수영구'),(40,2,'사상구'),(41,2,'기장군'),(42,3,'중구'),(43,3,'동구'),(44,3,'서구'),(45,3,'남구'),(46,3,'북구'),(47,3,'수성구'),(48,3,'달서구'),(49,3,'달성군'),(50,3,'군위군'),(51,4,'중구'),(52,4,'동구'),(53,4,'미추홀구'),(54,4,'연수구'),(55,4,'남동구'),(56,4,'부평구'),(57,4,'계양구'),(58,4,'서구'),(59,4,'강화군'),(60,4,'옹진군'),(61,5,'동구'),(62,5,'서구'),(63,5,'남구'),(64,5,'북구'),(65,5,'광산구'),(66,6,'중구'),(67,6,'남구'),(68,6,'동구'),(69,6,'북구'),(70,6,'울주군'),(71,7,'조치원읍'),(72,7,'연기면'),(73,7,'연동면'),(74,7,'부강면'),(75,7,'금남면'),(76,7,'장군면'),(77,7,'연서면'),(78,7,'전의면'),(79,7,'전동면'),(80,7,'소정면'),(81,7,'한솔동'),(82,7,'새롬동'),(83,7,'나성동'),(84,7,'다정동'),(85,7,'도담동'),(86,7,'어진동'),(87,7,'해밀동'),(88,7,'아름동'),(89,7,'종촌동'),(90,7,'고운동'),(91,7,'보람동'),(92,7,'대평동'),(93,7,'소담동'),(94,7,'반곡동'),(95,8,'수원시'),(96,8,'성남시'),(97,8,'의정부시'),(98,8,'안양시'),(99,8,'부천시'),(100,8,'광명시'),(101,8,'동두천시'),(102,8,'평택시'),(103,8,'안산시'),(104,8,'고양시'),(105,8,'과천시'),(106,8,'구리시'),(107,8,'남양주시'),(108,8,'오산시'),(109,8,'시흥시'),(110,8,'군포시'),(111,8,'의왕시'),(112,8,'하남시'),(113,8,'용인시'),(114,8,'파주시'),(115,8,'이천시'),(116,8,'안성시'),(117,8,'김포시'),(118,8,'화성시'),(119,8,'광주시'),(120,8,'양주시'),(121,8,'포천시'),(122,8,'여주시'),(123,8,'연천군'),(124,8,'가평군'),(125,8,'양평군'),(126,9,'춘천시'),(127,9,'원주시'),(128,9,'강릉시'),(129,9,'동해시'),(130,9,'태백시'),(131,9,'속초시'),(132,9,'삼척시'),(133,9,'홍천군'),(134,9,'횡성군'),(135,9,'영월군'),(136,9,'평창군'),(137,9,'정선군'),(138,9,'철원군'),(139,9,'화천군'),(140,9,'양구군'),(141,9,'인제군'),(142,9,'고성군'),(143,9,'양양군'),(144,10,'청주시'),(145,10,'충주시'),(146,10,'제천시'),(147,10,'보은군'),(148,10,'옥처군'),(149,10,'영동군'),(150,10,'증평군'),(151,10,'진천군'),(152,10,'괴산군'),(153,10,'음성군'),(154,10,'단양군'),(155,11,'천안시'),(156,11,'공주시'),(157,11,'보령시'),(158,11,'아산시'),(159,11,'서산시'),(160,11,'논산시'),(161,11,'계룡시'),(162,11,'당진시'),(163,11,'금산군'),(164,11,'부여군'),(165,11,'서천군'),(166,11,'청양군'),(167,11,'서천군'),(168,11,'쳥양군'),(169,11,'홍성군'),(170,11,'예산군'),(171,11,'태안군'),(172,12,'전주시'),(173,12,'군산시'),(174,12,'익산시'),(175,12,'정읍시'),(176,12,'남원시'),(177,12,'김제시'),(178,12,'완주군'),(179,12,'진안군'),(180,12,'무주군'),(181,12,'장수군'),(182,12,'임실군'),(183,12,'순창군'),(184,12,'고창군'),(185,12,'부안군'),(186,13,'목포시'),(187,13,'여수시'),(188,13,'순천시'),(189,13,'나주시'),(190,13,'광양시'),(191,13,'담양군'),(192,13,'곡성군'),(193,13,'구례군'),(194,13,'고흥군'),(195,13,'보성군'),(196,13,'화순군'),(197,13,'장흥군'),(198,13,'강진군'),(199,13,'해남군'),(200,13,'영암군'),(201,13,'무안군'),(202,13,'함평군'),(203,13,'영광군'),(204,13,'장성군'),(205,13,'완도군'),(206,13,'진도군'),(207,13,'신안군'),(208,14,'포항시'),(209,14,'경주시'),(210,14,'김천시'),(211,14,'안동시'),(212,14,'구미시'),(213,14,'영주시'),(214,14,'영천시'),(215,14,'상주시'),(216,14,'문경시'),(217,14,'경산시'),(218,14,'의성군'),(219,14,'청송군'),(220,14,'영양군'),(221,14,'영덕군'),(222,14,'청도군'),(223,14,'고령군'),(224,14,'성주군'),(225,14,'칠곡군'),(226,14,'예천군'),(227,14,'봉화군'),(228,14,'울진군'),(229,14,'울릉군'),(230,15,'창원시'),(231,15,'진주시'),(232,15,'통영시'),(233,15,'사천시'),(234,15,'김해시'),(235,15,'밀양시'),(236,15,'거제시'),(237,15,'양산시'),(238,15,'의령군'),(239,15,'함안군'),(240,15,'창녕군'),(241,15,'고성군'),(242,15,'남해군'),(243,15,'하동군'),(244,15,'산청군'),(245,15,'함양군'),(246,15,'거창군'),(247,15,'합천군'),(248,16,'제주시'),(249,16,'서귀포시');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interest` (
  `created_at` varchar(255) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest`
--

LOCK TABLES `interest` WRITE;
/*!40000 ALTER TABLE `interest` DISABLE KEYS */;
INSERT INTO `interest` VALUES ('2024-02-13 08:24:54',1,'2024-02-13 23:39:52','Film & Animation'),('2024-02-13 08:24:54',2,'2024-02-13 23:40:41','Autos & Vehicles'),('2024-02-13 08:24:54',3,'2024-02-13 08:24:54','Music'),('2024-02-13 08:24:54',4,'2024-02-13 08:24:54','Pets & Animals'),('2024-02-13 08:24:54',5,'2024-02-13 08:24:54','Sports'),('2024-02-13 08:24:54',6,'2024-02-13 08:24:54','Travel & Events'),('2024-02-13 08:24:54',7,'2024-02-13 08:24:54','Gaming'),('2024-02-13 08:24:54',8,'2024-02-13 08:24:54','Videoblogging'),('2024-02-13 08:24:54',9,'2024-02-13 08:24:54','People & Blogs'),('2024-02-13 08:24:54',10,'2024-02-13 08:24:54','Comedy'),('2024-02-13 08:24:54',11,'2024-02-13 08:24:54','Entertainment'),('2024-02-13 08:24:54',12,'2024-02-13 08:24:54','News & Politics'),('2024-02-13 08:24:54',13,'2024-02-13 08:24:54','Howto & Style'),('2024-02-13 08:24:54',14,'2024-02-13 08:24:54','Education'),('2024-02-13 08:24:54',15,'2024-02-13 08:24:54','Science & Technology'),('2024-02-13 08:24:54',16,'2024-02-13 08:24:54','Nonprofits & Activism');
/*!40000 ALTER TABLE `interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matching_result`
--

DROP TABLE IF EXISTS `matching_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matching_result` (
  `rate` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `member_two_id` bigint NOT NULL,
  `rate_introduction` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matching_result`
--

LOCK TABLES `matching_result` WRITE;
/*!40000 ALTER TABLE `matching_result` DISABLE KEYS */;
INSERT INTO `matching_result` VALUES (9635,15,13,17,'함께 많은 활동을 즐기세요!'),(5000,23,13,15,'sociability fits well'),(10000,24,16,15,'sociability fits well'),(10000,25,15,17,'sociability fits well'),(10000,28,18,16,'함께 많은 활동을 즐기세요!'),(10000,29,18,17,'함께 많은 활동을 즐기세요!'),(7437,30,13,19,'활발함이 배가 될 것 같아요!'),(7437,31,13,20,'활발함이 배가 될 것 같아요!'),(7437,32,13,21,'활발함이 배가 될 것 같아요!'),(7437,33,13,22,'활발함이 배가 될 것 같아요!'),(7437,34,13,23,'활발함이 배가 될 것 같아요!'),(7437,35,13,24,'활발함이 배가 될 것 같아요!'),(7437,36,13,25,'활발함이 배가 될 것 같아요!'),(7437,37,13,26,'활발함이 배가 될 것 같아요!'),(7437,38,13,27,'활발함이 배가 될 것 같아요!'),(7437,39,13,28,'활발함이 배가 될 것 같아요!'),(7437,40,13,29,'활발함이 배가 될 것 같아요!'),(7437,41,13,30,'활발함이 배가 될 것 같아요!'),(7076,42,13,31,'활발함이 배가 될 것 같아요!'),(7684,43,13,32,'서로를 배려하는 모습이 아름다워요!'),(7437,44,13,33,'활발함이 배가 될 것 같아요!'),(7437,45,13,34,'활발함이 배가 될 것 같아요!'),(6798,46,13,35,'사소한 것들은 넘겨버려요!'),(7016,47,13,36,'서로를 배려하는 모습이 아름다워요!'),(7258,48,13,37,'사소한 것들은 넘겨버려요!'),(7437,49,13,38,'활발함이 배가 될 것 같아요!'),(7990,50,13,16,'활발함이 배가 될 것 같아요!'),(9635,51,13,18,'함께 많은 활동을 즐기세요!'),(7894,52,18,19,'사소한 것들은 넘겨버려요!'),(7894,53,18,20,'사소한 것들은 넘겨버려요!'),(7894,54,18,21,'사소한 것들은 넘겨버려요!'),(7894,55,18,22,'사소한 것들은 넘겨버려요!'),(7894,56,18,23,'사소한 것들은 넘겨버려요!'),(7894,57,18,24,'사소한 것들은 넘겨버려요!'),(7894,58,18,25,'사소한 것들은 넘겨버려요!'),(7894,59,18,26,'사소한 것들은 넘겨버려요!'),(7894,60,18,27,'사소한 것들은 넘겨버려요!'),(7894,61,18,28,'사소한 것들은 넘겨버려요!'),(7894,62,18,29,'사소한 것들은 넘겨버려요!'),(7894,63,18,30,'사소한 것들은 넘겨버려요!'),(7505,64,18,31,'활발함이 배가 될 것 같아요!'),(7616,65,18,32,'사소한 것들은 넘겨버려요!'),(7894,66,18,33,'사소한 것들은 넘겨버려요!'),(7894,67,18,34,'사소한 것들은 넘겨버려요!'),(7241,68,18,35,'사소한 것들은 넘겨버려요!'),(7270,69,18,36,'들어줄게요, 말해봐요.'),(7639,70,18,37,'사소한 것들은 넘겨버려요!'),(7894,71,18,38,'사소한 것들은 넘겨버려요!'),(7918,72,17,9,'함께 많은 활동을 즐기세요!'),(7990,73,17,16,'활발함이 배가 될 것 같아요!'),(7493,74,17,19,'사소한 것들은 넘겨버려요!'),(7493,75,17,20,'사소한 것들은 넘겨버려요!'),(7493,76,17,21,'사소한 것들은 넘겨버려요!'),(7493,77,17,22,'사소한 것들은 넘겨버려요!'),(7493,78,17,23,'사소한 것들은 넘겨버려요!'),(7493,79,17,24,'사소한 것들은 넘겨버려요!'),(7493,80,17,25,'사소한 것들은 넘겨버려요!'),(7493,81,17,26,'사소한 것들은 넘겨버려요!'),(7493,82,17,27,'사소한 것들은 넘겨버려요!'),(7493,83,17,28,'사소한 것들은 넘겨버려요!'),(7493,84,17,29,'사소한 것들은 넘겨버려요!'),(7493,85,17,30,'사소한 것들은 넘겨버려요!'),(7132,86,17,31,'활발함이 배가 될 것 같아요!'),(7740,87,17,32,'서로를 배려하는 모습이 아름다워요!'),(7493,88,17,33,'사소한 것들은 넘겨버려요!'),(7493,89,17,34,'사소한 것들은 넘겨버려요!'),(6788,90,17,35,'사소한 것들은 넘겨버려요!'),(6891,91,17,36,'서로를 배려하는 모습이 아름다워요!'),(7248,92,17,37,'사소한 것들은 넘겨버려요!'),(7493,93,17,38,'사소한 것들은 넘겨버려요!'),(7732,94,39,13,'함께 많은 활동을 즐기세요!'),(7990,95,39,16,'활발함이 배가 될 것 같아요!'),(7847,96,39,18,'함께 많은 활동을 즐기세요!'),(7831,97,39,19,'들어줄게요, 말해봐요.'),(7831,98,39,20,'들어줄게요, 말해봐요.'),(7831,99,39,21,'들어줄게요, 말해봐요.'),(7831,100,39,22,'들어줄게요, 말해봐요.'),(7831,101,39,23,'들어줄게요, 말해봐요.'),(7831,102,39,24,'들어줄게요, 말해봐요.'),(7831,103,39,25,'들어줄게요, 말해봐요.'),(7831,104,39,26,'들어줄게요, 말해봐요.'),(7831,105,39,27,'들어줄게요, 말해봐요.'),(7831,106,39,28,'들어줄게요, 말해봐요.'),(7831,107,39,29,'들어줄게요, 말해봐요.'),(7831,108,39,30,'들어줄게요, 말해봐요.'),(7942,109,39,31,'들어줄게요, 말해봐요.'),(7554,110,39,32,'들어줄게요, 말해봐요.'),(7831,111,39,33,'들어줄게요, 말해봐요.'),(7831,112,39,34,'들어줄게요, 말해봐요.'),(7306,113,39,35,'들어줄게요, 말해봐요.'),(6490,114,39,36,'사소한 것들은 넘겨버려요!'),(7004,115,39,37,'사소하다 생각 말고, 뭐든 같이 이야기해요.'),(7831,116,39,38,'들어줄게요, 말해봐요.'),(7847,117,17,39,'함께 많은 활동을 즐기세요!'),(7437,118,43,13,'활발함이 배가 될 것 같아요!'),(7493,119,43,17,'사소한 것들은 넘겨버려요!'),(7493,120,43,18,'사소한 것들은 넘겨버려요!'),(10000,121,43,19,'함께 많은 활동을 즐기세요!'),(10000,122,43,20,'함께 많은 활동을 즐기세요!'),(10000,123,43,21,'함께 많은 활동을 즐기세요!'),(10000,124,43,22,'함께 많은 활동을 즐기세요!'),(10000,125,43,23,'함께 많은 활동을 즐기세요!'),(10000,126,43,24,'함께 많은 활동을 즐기세요!'),(10000,127,43,25,'함께 많은 활동을 즐기세요!'),(10000,128,43,26,'함께 많은 활동을 즐기세요!'),(10000,129,43,27,'함께 많은 활동을 즐기세요!'),(10000,130,43,28,'함께 많은 활동을 즐기세요!'),(10000,131,43,29,'함께 많은 활동을 즐기세요!'),(10000,132,43,30,'함께 많은 활동을 즐기세요!'),(9611,133,43,31,'함께 많은 활동을 즐기세요!'),(9722,134,43,32,'함께 많은 활동을 즐기세요!'),(10000,135,43,33,'함께 많은 활동을 즐기세요!'),(10000,136,43,34,'함께 많은 활동을 즐기세요!'),(9220,137,43,35,'들어줄게요, 말해봐요.'),(7909,138,43,36,'함께 많은 활동을 즐기세요!'),(9164,139,43,37,'함께 많은 활동을 즐기세요!'),(10000,140,43,38,'함께 많은 활동을 즐기세요!'),(7831,141,43,39,'들어줄게요, 말해봐요.');
/*!40000 ALTER TABLE `matching_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `account_status` int NOT NULL,
  `login_status` int NOT NULL,
  `roommate_search_status` int NOT NULL DEFAULT '1',
  `city_id` bigint DEFAULT NULL,
  `created_at` varchar(255) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `metropolitan_id` bigint DEFAULT NULL,
  `social_id` bigint NOT NULL,
  `updated_at` varchar(255) NOT NULL,
  `access_token` varchar(255) NOT NULL,
  `birthday` varchar(255) NOT NULL,
  `birthyear` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL DEFAULT '사용자',
  `profile_image_url` varchar(255) NOT NULL,
  `provider` varchar(255) NOT NULL,
  `refresh_token` varchar(255) NOT NULL,
  `characteristic_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,1,0,1,'2024-02-08 01:18:23.191972',5,1,3298306760,'2024-02-08 01:18:23.191972','sample_accessToken1','0821','1996','male','sample introduction 1','sample_name1','sample_nickname1','imageURL','kakao','sample_refreshToken1',19),(1,1,1,5,'2024-02-08 01:18:49.089659',6,5,1236,'2024-02-08 03:20:40.603190','sample_accessToken2','0823','1932','male','\"농구를 좋아하는 모이루미입니다. 룸메이트 구하시는 분들 많이 채팅주세요!\"','sample_name2','\"모이모\"','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/b149e189-9e3b-47b6-a81b-ed3a0bba4344.jpg','kakao','sample_refreshToken2',20),(1,1,0,31,'2024-02-08 01:39:08.510480',7,11,1237,'2024-02-08 04:16:09.367818','sample_accessToken3','0827','1937','male','\"농구를 좋아하는 모이루미입니다. 룸메이트 구하시는 분들 많이 채팅주세요!\"','sample_name3','\"모이루미\"','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/3693c557-5558-4475-95c6-a0e35bfda802.jpg','kakao','sample_refreshToken3',22),(1,1,1,23,'2024-02-08 05:50:16.483981',13,1,3334196984,'2024-02-15 23:02:10','vIkbRvSavdE4-RLWN98yCBCdT_fM22UAzHIKKiWOAAABja0TEl_C3p98Pd5TpQ','0000','0000','male','ㅎ','황재언','황재언','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/760ecd49-433b-45ed-afd8-bf7d38e74409.png','kakao','O4nHkGtJM3zLEyhYfD16WWly2A6Rk_fywwsKKiWOAAABja0TElzC3p98Pd5TpQ',30),(1,1,1,23,'2024-02-12 15:43:17',17,1,3339392657,'2024-02-15 21:04:07','nAc5aQBk7zXIUquOUEkrMVbu2Rfbx3T97jgKPXKXAAABjaym6arMISgqRbFCUQ','0000','0000','male','ㅎㅇㅎㅇ','전새벽','전새벽','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/46660ba4-ade0-4442-bf38-2df414218614.png','kakao','F4ks0or1Yh2UnoZuHJSc9e7AWw398r74IJUKPXKXAAABjaym6afMISgqRbFCUQ',29),(1,1,1,23,'2024-02-13 16:55:58',18,1,3334175595,'2024-02-15 16:27:23','D9eyUbdBdIG0h-qCqSJ5wgyNIPlk7xovPhUKPXLrAAABjas3L5iIenTzhLqDRQ','0000','0000','male','바꿔요!','정성현','정성현','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/b0511388-1982-4c74-b4a7-7a7dea7064cc.png','kakao','USOOSunDjdVEFsruxQxo0xugHu9QwL9s3uEKPXLrAAABjas3L5WIenTzhLqDRQ',33),(1,1,1,23,'2024-02-14 04:31:40',19,1,123456,'2024-02-14 04:31:40','accessTokenSample1','0000','0000','male','introductionSample1','nameSample1','nicknameSample1','profileImageSample1','kakao','refreshTokenSample1',53),(1,1,1,23,'2024-02-14 04:31:40',20,1,123457,'2024-02-14 04:31:40','accessTokenSample2','0000','0000','male','introductionSample2','nameSample2','nicknameSample2','profileImageSample2','kakao','refreshTokenSample2',52),(1,1,1,23,'2024-02-14 04:31:40',21,1,123458,'2024-02-14 04:31:40','accessTokenSample3','0000','0000','male','introductionSample3','nameSample3','nicknameSample3','profileImageSample3','kakao','refreshTokenSample3',51),(1,1,1,23,'2024-02-14 04:31:40',22,1,123459,'2024-02-14 04:31:40','accessTokenSample4','0000','0000','male','introductionSample4','nameSample4','nicknameSample4','profileImageSample4','kakao','refreshTokenSample4',50),(1,1,1,23,'2024-02-14 04:31:40',23,1,123460,'2024-02-14 04:31:40','accessTokenSample5','0000','0000','male','introductionSample5','nameSample5','nicknameSample5','profileImageSample5','kakao','refreshTokenSample5',49),(1,1,1,23,'2024-02-14 04:31:40',24,1,123461,'2024-02-14 04:31:40','accessTokenSample6','0000','0000','male','introductionSample6','nameSample6','nicknameSample6','profileImageSample6','kakao','refreshTokenSample6',48),(1,1,1,23,'2024-02-14 04:31:40',25,1,123462,'2024-02-14 04:31:40','accessTokenSample7','0000','0000','male','introductionSample7','nameSample7','nicknameSample7','profileImageSample7','kakao','refreshTokenSample7',47),(1,1,1,23,'2024-02-14 04:31:40',26,1,123463,'2024-02-14 04:31:40','accessTokenSample8','0000','0000','male','introductionSample8','nameSample8','nicknameSample8','profileImageSample8','kakao','refreshTokenSample8',46),(1,1,1,23,'2024-02-14 04:31:40',27,1,123464,'2024-02-14 04:31:40','accessTokenSample9','0000','0000','male','introductionSample9','nameSample9','nicknameSample9','profileImageSample9','kakao','refreshTokenSample9',45),(1,1,1,23,'2024-02-14 04:31:40',28,1,123465,'2024-02-14 04:31:40','accessTokenSample10','0000','0000','male','introductionSample10','nameSample10','nicknameSample10','profileImageSample10','kakao','refreshTokenSample10',44),(1,1,1,23,'2024-02-14 04:31:40',29,1,123466,'2024-02-14 04:31:40','accessTokenSample11','0000','0000','male','introductionSample11','nameSample11','nicknameSample11','profileImageSample11','kakao','refreshTokenSample11',43),(1,1,1,23,'2024-02-14 04:31:40',30,1,123467,'2024-02-14 04:31:40','accessTokenSample12','0000','0000','male','introductionSample12','nameSample12','nicknameSample12','profileImageSample12','kakao','refreshTokenSample12',42),(1,1,1,23,'2024-02-14 04:31:40',31,1,123468,'2024-02-14 04:31:40','accessTokenSample13','0000','0000','male','introductionSample13','nameSample13','nicknameSample13','profileImageSample13','kakao','refreshTokenSample13',41),(1,1,1,23,'2024-02-14 04:31:40',32,1,123469,'2024-02-14 04:31:40','accessTokenSample14','0000','0000','male','introductionSample14','nameSample14','nicknameSample14','profileImageSample14','kakao','refreshTokenSample14',40),(1,1,1,23,'2024-02-14 04:31:40',33,1,123470,'2024-02-14 04:31:40','accessTokenSample15','0000','0000','male','introductionSample15','nameSample15','nicknameSample15','profileImageSample15','kakao','refreshTokenSample15',39),(1,1,1,23,'2024-02-14 04:31:40',34,1,123471,'2024-02-14 04:31:40','accessTokenSample16','0000','0000','male','introductionSample16','nameSample16','nicknameSample16','profileImageSample16','kakao','refreshTokenSample16',38),(1,1,1,23,'2024-02-14 04:31:40',35,1,123472,'2024-02-14 04:31:40','accessTokenSample17','0000','0000','male','introductionSample17','nameSample17','nicknameSample17','profileImageSample17','kakao','refreshTokenSample17',37),(1,1,1,23,'2024-02-14 04:31:40',36,1,123473,'2024-02-14 04:31:40','accessTokenSample18','0000','0000','male','introductionSample18','nameSample18','nicknameSample18','profileImageSample18','kakao','refreshTokenSample18',36),(1,1,1,23,'2024-02-14 04:31:40',37,1,123474,'2024-02-14 04:31:40','accessTokenSample19','0000','0000','male','introductionSample19','nameSample19','nicknameSample19','profileImageSample19','kakao','refreshTokenSample19',35),(1,1,1,23,'2024-02-14 04:31:40',38,1,123475,'2024-02-14 04:31:40','accessTokenSample20','0000','0000','male','introductionSample20','nameSample20','nicknameSample20','profileImageSample20','kakao','refreshTokenSample20',34),(1,1,1,23,'2024-02-15 09:27:43',39,1,3343502943,'2024-02-15 20:41:50','TxJIk-nT0Gd1FdUuk317WgTGC9Y7RUUNZAsKKiURAAABjaySc8cp9hBbJybEWQ','0000','0000','male','안녕하세요! 잘 배려해주시는 분이면 좋겠어요!','김예지','김예지','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/93c1304c-45ff-42e6-9390-c6310cce71c6.png','kakao','QKMV3SKKra8XoqvhL4pr6mzI638V0yBmW40KKiURAAABjaySc8Qp9hBbJybEWQ',59),(1,1,1,23,'2024-02-15 10:32:43',40,1,3343587197,'2024-02-15 10:33:02','tq8maQgkOQr-O2AVVToUY-WpdOREDEeEXAIKKw0gAAABjaplASzNsk3jZ7dWzg','0000','0000','male','싸피','김주현','김주현','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/6c08295a-e607-433c-b6ba-6ea0349d4adb.png','kakao','IstQ0uLJ0zS7Lxbzl3Tr04rZgf62KX97MRoKKw0gAAABjaplASjNsk3jZ7dWzg',NULL),(1,1,0,23,'2024-02-15 17:34:25',41,1,3345048637,'2024-02-15 17:38:26','mhsdQ3TWDMO8XazXPIukzgB0U63PqtLT89YKKiWQAAABjavo-mTgLMgnBn6ZSw','0000','0000','male','멍멍이를 엄청 좋아해요. 댕댕.','테스트','안드리아','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/ab6a939e-d511-4906-8af6-c547d765c044.png','kakao','JCa1UGXyimBbN309Pg7pi4ipzJCaeOth0j0KKiWQAAABjavo-mHgLMgnBn6ZSw',NULL),(1,1,1,23,'2024-02-15 20:51:05',42,1,3345337102,'2024-02-15 20:51:39','KKrIJTN_FoITM2Y7o3ThvRK1UDiiX9IxuVIKPXMXAAABjaybJXeQgW3aWXatGQ','0000','0000','female','이짱구','김이현','김이현','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/2a31b837-a0cf-4c1e-a417-989913624ca9.png','kakao','HCSPIwqxTtyBFbowVLHNx2PVjgJx9zT1PDUKPXMXAAABjaybJXSQgW3aWXatGQ',NULL),(1,1,1,23,'2024-02-15 21:19:05',43,1,3345380388,'2024-02-15 21:19:12','1wpW8v48nUgA_d6npbePIsAKdgpqxsokSMkKPXSXAAABjay0xAKUJG13ldIf8A','0000','0000','male','gkgk','테스트','테스트','https://moiroom.s3.ap-northeast-2.amazonaws.com/profileImage/b0697d51-a71a-469a-82f3-e246fd59cae6.png','kakao','h1Ipu8DUpgBrRJXcC-PA6AVB_NV88jCNY9MKPXSXAAABjay0w_-UJG13ldIf8A',53);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_chat_room`
--

DROP TABLE IF EXISTS `member_chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_chat_room` (
  `chat_room_id` bigint NOT NULL,
  `member_id` bigint NOT NULL,
  PRIMARY KEY (`chat_room_id`,`member_id`),
  KEY `FK5u0o0is7hqioiix0xu5eegd6h` (`member_id`),
  CONSTRAINT `FK5u0o0is7hqioiix0xu5eegd6h` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKmrcev29a2uuf5wsxkmtgto2uk` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_chat_room`
--

LOCK TABLES `member_chat_room` WRITE;
/*!40000 ALTER TABLE `member_chat_room` DISABLE KEYS */;
INSERT INTO `member_chat_room` VALUES (70,13),(69,17),(69,18),(71,18),(70,20),(71,43);
/*!40000 ALTER TABLE `member_chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_interest`
--

DROP TABLE IF EXISTS `member_interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_interest` (
  `percent` int NOT NULL,
  `interest_id` bigint NOT NULL,
  `member_id` bigint NOT NULL,
  PRIMARY KEY (`interest_id`,`member_id`),
  KEY `FK5kx0pu686yru7e3u31bl1epya` (`member_id`),
  CONSTRAINT `FK5kx0pu686yru7e3u31bl1epya` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKrjgps51m0xii2hvp16h0x7l00` FOREIGN KEY (`interest_id`) REFERENCES `interest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_interest`
--

LOCK TABLES `member_interest` WRITE;
/*!40000 ALTER TABLE `member_interest` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `metropolitan`
--

DROP TABLE IF EXISTS `metropolitan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metropolitan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `metropolitan`
--

LOCK TABLES `metropolitan` WRITE;
/*!40000 ALTER TABLE `metropolitan` DISABLE KEYS */;
INSERT INTO `metropolitan` VALUES (1,'서울'),(2,'부산'),(3,'대구'),(4,'인천'),(5,'광주'),(6,'울산'),(7,'세종'),(8,'경기'),(9,'강원'),(10,'충북'),(11,'충남'),(12,'전북'),(13,'전남'),(14,'경북'),(15,'경남'),(16,'제주');
/*!40000 ALTER TABLE `metropolitan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-16  1:00:29
