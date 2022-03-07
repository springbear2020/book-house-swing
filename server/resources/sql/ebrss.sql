-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: ebrss
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Current Database: `ebrss`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ebrss` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ebrss`;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `bookId` int NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `isbn` char(16) NOT NULL COMMENT '图书isbn',
  `title` varchar(32) NOT NULL COMMENT '书名',
  `author` varchar(32) NOT NULL COMMENT '作者',
  `price` double NOT NULL COMMENT '单价',
  `publisher` varchar(32) NOT NULL COMMENT '出版社',
  `edition` varchar(32) NOT NULL COMMENT '版次',
  `keyword` varchar(32) NOT NULL COMMENT '关键词',
  `url` varchar(64) NOT NULL COMMENT '图书保存路径',
  `category` char(1) NOT NULL COMMENT '类别',
  `bookState` char(8) NOT NULL COMMENT '图书状态编号',
  PRIMARY KEY (`bookId`),
  UNIQUE KEY `idx_isbn` (`isbn`),
  KEY `fk_category` (`category`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`category`) REFERENCES `category` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'9787111213826','Java编程思想(第4版)','陈昊鹏 译',108,'机械工业出版社','2020年10月第1版','Java语言程序设计','d:\\book\\Java编程思想(第4版).pdf','T','on'),(2,'0131872486','Thing in Java(Fourth Edition)','Bruce Eckel',0,'President, MindView, Inc. ','2006年1月第1版','Java语言程序设计','d:\\book\\Thing in Java(Fourth Edition).pdf','T','on'),(3,'97871113700048','Java并发编程实战','童云兰 译',69,'机械工业出版社','2012年2月第1版','Java、Java并发编程、并发编程','d:\\book\\Java并发编程实战.pdf','T','on'),(4,'9787111508243','Java并发编程的艺术','方腾飞 魏鹏 程晓明',0,'机械工业出版社','1970年1月第1版','Java、Java并发编程、并发编程','d:\\book\\Java并发编程的艺术.pdf','T','on'),(5,'9787111547426','Java核心技术卷I基础知识(原书第10版)','周立新 陈波 叶乃文 杜永萍 译',119,'机械工业出版社','2016年9月第1版','Java程序设计、计算机科学','d:\\book\\Java核心技术卷I基础知识(原书第10版).pdf','T','on'),(6,'9787111349662','深入理解Java虚拟机JVM高级特性与最佳实践','周志明',69,'机械工业出版社','2011年9月第1版','Java语言程序设计、JVM、Java虚拟机','d:\\book\\深入理解Java虚拟机JVM高级特性与最佳实践.pdf','T','on');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookstate`
--

DROP TABLE IF EXISTS `bookstate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookstate` (
  `bookStateId` int NOT NULL COMMENT '图书状态编号',
  `bookState` char(8) NOT NULL COMMENT '图书状态',
  PRIMARY KEY (`bookStateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookstate`
--

LOCK TABLES `bookstate` WRITE;
/*!40000 ALTER TABLE `bookstate` DISABLE KEYS */;
INSERT INTO `bookstate` VALUES (0,'on'),(1,'off');
/*!40000 ALTER TABLE `bookstate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `categoryId` int NOT NULL AUTO_INCREMENT COMMENT '类别编号',
  `category` char(1) NOT NULL COMMENT '类别',
  `description` varchar(32) NOT NULL COMMENT '类别描述',
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `idx_category` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'A','马列主义、毛泽东思想、邓小平理论'),(2,'B','哲学、宗教'),(3,'C','社会科学总论'),(4,'D','政治、法律'),(5,'E','军事'),(6,'F','经济'),(7,'G','文化、科学、教育、体育'),(8,'H','语言、文字'),(9,'I','文学'),(10,'J','艺术'),(11,'K','历史、地理'),(12,'L',''),(13,'M',''),(14,'N','自然科学总论'),(15,'O','数理科学和化学'),(16,'P','天文学、地球科学'),(17,'Q','生物科学'),(18,'R','医药、卫生'),(19,'S','农业科学'),(20,'T','工业技术'),(21,'U','交通运输'),(22,'V','航空、航天'),(23,'W',''),(24,'X','环境科学、劳动保护科学(安全科学)'),(25,'Y',''),(26,'Z','综合性图书');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `code`
--

DROP TABLE IF EXISTS `code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `code` (
  `codeId` int NOT NULL AUTO_INCREMENT COMMENT '注册码编号',
  `registerCode` char(8) NOT NULL COMMENT '注册码',
  `codeState` char(8) NOT NULL DEFAULT 'unused' COMMENT '注册码状态',
  PRIMARY KEY (`codeId`),
  UNIQUE KEY `idx_code` (`registerCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code`
--

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
/*!40000 ALTER TABLE `code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite` (
  `isbn` char(16) NOT NULL COMMENT '图书isbn',
  `title` varchar(32) NOT NULL COMMENT '书名',
  `author` varchar(32) NOT NULL COMMENT '作者',
  `keyword` varchar(32) NOT NULL COMMENT '关键字',
  `category` char(1) NOT NULL COMMENT '类别',
  `time` date NOT NULL COMMENT '收藏时间',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  PRIMARY KEY (`isbn`,`username`),
  KEY `fk_username` (`username`),
  CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`),
  CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record` (
  `recordId` int NOT NULL AUTO_INCREMENT COMMENT '下载记录编号',
  `title` varchar(32) NOT NULL COMMENT '书名',
  `author` varchar(32) NOT NULL COMMENT '作者',
  `time` date NOT NULL COMMENT '下载时间',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  PRIMARY KEY (`recordId`),
  KEY `fk_username` (`username`),
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `sex` char(1) NOT NULL COMMENT '性别',
  `idCard` char(32) NOT NULL COMMENT '身份证号',
  `phone` char(16) NOT NULL COMMENT '手机号',
  `mail` varchar(32) NOT NULL COMMENT '邮箱',
  `registerCode` char(8) NOT NULL COMMENT '用户注册码',
  `userStateId` int NOT NULL DEFAULT '0' COMMENT '用户状态编号',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `idx_username` (`username`),
  KEY `fk_code` (`registerCode`),
  KEY `fk_userStateId` (`userStateId`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`registerCode`) REFERENCES `code` (`registerCode`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`userStateId`) REFERENCES `userstate` (`userStateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`admin`@`localhost`*/ /*!50003 TRIGGER `update_code_state` AFTER INSERT ON `user` FOR EACH ROW BEGIN
   UPDATE `Code` SET `codeState` = 'used' WHERE new.`registerCode` = `registerCode`;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `userstate`
--

DROP TABLE IF EXISTS `userstate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userstate` (
  `userStateId` int NOT NULL COMMENT '用户状态编号',
  `userState` char(8) NOT NULL COMMENT '用户状态',
  PRIMARY KEY (`userStateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userstate`
--

LOCK TABLES `userstate` WRITE;
/*!40000 ALTER TABLE `userstate` DISABLE KEYS */;
INSERT INTO `userstate` VALUES (0,'normal'),(1,'freezing');
/*!40000 ALTER TABLE `userstate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `view_book`
--

DROP TABLE IF EXISTS `view_book`;
/*!50001 DROP VIEW IF EXISTS `view_book`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_book` AS SELECT 
 1 AS `isbn`,
 1 AS `title`,
 1 AS `author`,
 1 AS `keyword`,
 1 AS `category`,
 1 AS `bookState`*/;
SET character_set_client = @saved_cs_client;

--
-- Current Database: `ebrss`
--

USE `ebrss`;

--
-- Final view structure for view `view_book`
--

/*!50001 DROP VIEW IF EXISTS `view_book`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_book` AS select `book`.`isbn` AS `isbn`,`book`.`title` AS `title`,`book`.`author` AS `author`,`book`.`keyword` AS `keyword`,`book`.`category` AS `category`,`book`.`bookState` AS `bookState` from `book` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-07 15:12:16
