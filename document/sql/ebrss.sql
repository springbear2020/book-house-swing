/*
 Navicat Premium Data Transfer

 Source Server         : root-localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : ebrss

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 29/06/2022 07:06:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Create the database
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `ebrss`;

USE `ebrss`;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bookId` int NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `isbn` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书isbn',
  `title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书名',
  `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者',
  `price` double NOT NULL COMMENT '单价',
  `publisher` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '出版社',
  `edition` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '版次',
  `keyword` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关键词',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书保存路径',
  `category` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别',
  `bookState` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书状态编号',
  PRIMARY KEY (`bookId`) USING BTREE,
  UNIQUE INDEX `idx_isbn`(`isbn` ASC) USING BTREE,
  INDEX `fk_category`(`category` ASC) USING BTREE,
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`category`) REFERENCES `category` (`category`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '9787111508243', 'Java并发编程的艺术', '方腾飞 / 魏鹏 / 程晓明', 59, '机械工业出版社', '2021年12月第1版', '9787111508243', 'Java并发编程的艺术.pdf', 'T', 'on');

-- ----------------------------
-- Table structure for bookstate
-- ----------------------------
DROP TABLE IF EXISTS `bookstate`;
CREATE TABLE `bookstate`  (
  `bookStateId` int NOT NULL COMMENT '图书状态编号',
  `bookState` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书状态',
  PRIMARY KEY (`bookStateId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bookstate
-- ----------------------------
INSERT INTO `bookstate` VALUES (0, 'on');
INSERT INTO `bookstate` VALUES (1, 'off');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `categoryId` int NOT NULL AUTO_INCREMENT COMMENT '类别编号',
  `category` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别',
  `description` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别描述',
  PRIMARY KEY (`categoryId`) USING BTREE,
  UNIQUE INDEX `idx_category`(`category` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, 'A', '马列主义、毛泽东思想、邓小平理论');
INSERT INTO `category` VALUES (2, 'B', '哲学、宗教');
INSERT INTO `category` VALUES (3, 'C', '社会科学总论');
INSERT INTO `category` VALUES (4, 'D', '政治、法律');
INSERT INTO `category` VALUES (5, 'E', '军事');
INSERT INTO `category` VALUES (6, 'F', '经济');
INSERT INTO `category` VALUES (7, 'G', '文化、科学、教育、体育');
INSERT INTO `category` VALUES (8, 'H', '语言、文字');
INSERT INTO `category` VALUES (9, 'I', '文学');
INSERT INTO `category` VALUES (10, 'J', '艺术');
INSERT INTO `category` VALUES (11, 'K', '历史、地理');
INSERT INTO `category` VALUES (12, 'L', '');
INSERT INTO `category` VALUES (13, 'M', '');
INSERT INTO `category` VALUES (14, 'N', '自然科学总论');
INSERT INTO `category` VALUES (15, 'O', '数理科学和化学');
INSERT INTO `category` VALUES (16, 'P', '天文学、地球科学');
INSERT INTO `category` VALUES (17, 'Q', '生物科学');
INSERT INTO `category` VALUES (18, 'R', '医药、卫生');
INSERT INTO `category` VALUES (19, 'S', '农业科学');
INSERT INTO `category` VALUES (20, 'T', '工业技术');
INSERT INTO `category` VALUES (21, 'U', '交通运输');
INSERT INTO `category` VALUES (22, 'V', '航空、航天');
INSERT INTO `category` VALUES (23, 'W', '');
INSERT INTO `category` VALUES (24, 'X', '环境科学、劳动保护科学(安全科学)');
INSERT INTO `category` VALUES (25, 'Y', '');
INSERT INTO `category` VALUES (26, 'Z', '综合性图书');

-- ----------------------------
-- Table structure for code
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code`  (
  `codeId` int NOT NULL AUTO_INCREMENT COMMENT '注册码编号',
  `registerCode` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '注册码',
  `codeState` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'unused' COMMENT '注册码状态',
  PRIMARY KEY (`codeId`) USING BTREE,
  UNIQUE INDEX `idx_code`(`registerCode` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of code
-- ----------------------------
INSERT INTO `code` VALUES (1, 'R2V71M', 'used');
INSERT INTO `code` VALUES (2, 'R49HV2', 'unused');
INSERT INTO `code` VALUES (3, 'Y88S22', 'unused');
INSERT INTO `code` VALUES (4, '8LQZ4J', 'unused');
INSERT INTO `code` VALUES (5, 'EMEXM7', 'unused');
INSERT INTO `code` VALUES (6, 'SDA7NW', 'unused');
INSERT INTO `code` VALUES (7, 'H0IHNA', 'unused');
INSERT INTO `code` VALUES (8, 'YUYWLG', 'unused');
INSERT INTO `code` VALUES (9, '93Z3BS', 'unused');
INSERT INTO `code` VALUES (10, 'RAOA41', 'unused');
INSERT INTO `code` VALUES (11, 'XMR2JM', 'unused');
INSERT INTO `code` VALUES (12, 'W7IMXU', 'unused');
INSERT INTO `code` VALUES (13, 'KJECC5', 'unused');
INSERT INTO `code` VALUES (14, 'YH0L0X', 'unused');
INSERT INTO `code` VALUES (15, 'NDHTLH', 'unused');
INSERT INTO `code` VALUES (16, '4SDQHI', 'unused');
INSERT INTO `code` VALUES (17, '574AOL', 'unused');
INSERT INTO `code` VALUES (18, 'IW7GJ6', 'unused');
INSERT INTO `code` VALUES (19, '1JQCO2', 'unused');
INSERT INTO `code` VALUES (20, 'IJIEMS', 'unused');
INSERT INTO `code` VALUES (21, 'XAK04M', 'unused');
INSERT INTO `code` VALUES (22, '63I82M', 'unused');
INSERT INTO `code` VALUES (23, 'SZSISJ', 'unused');
INSERT INTO `code` VALUES (24, '77X5YJ', 'unused');
INSERT INTO `code` VALUES (25, '788DAL', 'unused');
INSERT INTO `code` VALUES (26, '7IU66E', 'unused');
INSERT INTO `code` VALUES (27, 'DJAUTO', 'unused');
INSERT INTO `code` VALUES (28, 'SVRMWC', 'unused');
INSERT INTO `code` VALUES (29, '8RT08A', 'unused');
INSERT INTO `code` VALUES (30, 'PMKG2G', 'unused');
INSERT INTO `code` VALUES (31, 'UWR64R', 'unused');
INSERT INTO `code` VALUES (32, 'EMEDH9', 'unused');
INSERT INTO `code` VALUES (33, 'L8ECJQ', 'unused');
INSERT INTO `code` VALUES (34, 'MR2CFN', 'unused');
INSERT INTO `code` VALUES (35, '7VOFTG', 'unused');
INSERT INTO `code` VALUES (36, 'BMIU7Y', 'unused');
INSERT INTO `code` VALUES (37, 'KWSA77', 'unused');
INSERT INTO `code` VALUES (38, '85MZNR', 'unused');
INSERT INTO `code` VALUES (39, 'PRV4B2', 'unused');
INSERT INTO `code` VALUES (40, 'CBA2AL', 'unused');
INSERT INTO `code` VALUES (41, 'QE0NVQ', 'unused');
INSERT INTO `code` VALUES (42, 'O6OC71', 'unused');
INSERT INTO `code` VALUES (43, '779PVN', 'unused');
INSERT INTO `code` VALUES (44, '5MVIZD', 'unused');
INSERT INTO `code` VALUES (45, 'FBDWGH', 'unused');
INSERT INTO `code` VALUES (46, 'LFHU5X', 'unused');
INSERT INTO `code` VALUES (47, '2GCY71', 'unused');
INSERT INTO `code` VALUES (48, '69KSAV', 'unused');
INSERT INTO `code` VALUES (49, 'U2PV93', 'unused');
INSERT INTO `code` VALUES (50, 'NY9SGN', 'unused');
INSERT INTO `code` VALUES (51, 'KJJSV3', 'unused');
INSERT INTO `code` VALUES (52, 'EHZSWD', 'unused');
INSERT INTO `code` VALUES (53, 'FO8K7F', 'unused');
INSERT INTO `code` VALUES (54, 'WBU1QB', 'unused');
INSERT INTO `code` VALUES (55, '17W8DM', 'unused');
INSERT INTO `code` VALUES (56, '1E7GKP', 'unused');
INSERT INTO `code` VALUES (57, 'Y3EIH6', 'unused');
INSERT INTO `code` VALUES (58, '96R3O1', 'unused');
INSERT INTO `code` VALUES (59, '1K442C', 'unused');
INSERT INTO `code` VALUES (60, 'JK7F6F', 'unused');
INSERT INTO `code` VALUES (61, '2V9O42', 'unused');
INSERT INTO `code` VALUES (62, 'O1AZOS', 'unused');
INSERT INTO `code` VALUES (63, 'VFSNBR', 'unused');
INSERT INTO `code` VALUES (64, '1XA962', 'unused');
INSERT INTO `code` VALUES (65, 'N901P5', 'unused');
INSERT INTO `code` VALUES (66, 'K6426D', 'unused');
INSERT INTO `code` VALUES (67, '9V1NEA', 'unused');
INSERT INTO `code` VALUES (68, 'V9PNAZ', 'unused');
INSERT INTO `code` VALUES (69, 'N3P8L5', 'unused');
INSERT INTO `code` VALUES (70, 'F3V1SQ', 'unused');
INSERT INTO `code` VALUES (71, '3F9AQ7', 'unused');
INSERT INTO `code` VALUES (72, '2GZ23C', 'unused');
INSERT INTO `code` VALUES (73, 'FX8Q5A', 'unused');
INSERT INTO `code` VALUES (74, 'BBSOQC', 'unused');
INSERT INTO `code` VALUES (75, 'YQDTQY', 'unused');
INSERT INTO `code` VALUES (76, 'L27XWP', 'unused');
INSERT INTO `code` VALUES (77, '64QUN2', 'unused');
INSERT INTO `code` VALUES (78, 'P0JT9S', 'unused');
INSERT INTO `code` VALUES (79, 'ECHB9W', 'unused');
INSERT INTO `code` VALUES (80, 'SMCKVQ', 'unused');
INSERT INTO `code` VALUES (81, 'D28YZO', 'unused');
INSERT INTO `code` VALUES (82, 'VFUNOF', 'unused');
INSERT INTO `code` VALUES (83, 'DJ0OWQ', 'unused');
INSERT INTO `code` VALUES (84, 'CDW8F0', 'unused');
INSERT INTO `code` VALUES (85, 'VH6JMB', 'unused');
INSERT INTO `code` VALUES (86, 'GICHPG', 'unused');
INSERT INTO `code` VALUES (87, '8QCZAS', 'unused');
INSERT INTO `code` VALUES (88, 'P3F2EF', 'unused');
INSERT INTO `code` VALUES (89, 'FHYVVR', 'unused');
INSERT INTO `code` VALUES (90, 'B7SANR', 'unused');
INSERT INTO `code` VALUES (91, 'Z3I1F9', 'unused');
INSERT INTO `code` VALUES (92, 'BDSJT4', 'unused');
INSERT INTO `code` VALUES (93, '1DXIN3', 'unused');
INSERT INTO `code` VALUES (94, 'MHWBLK', 'unused');
INSERT INTO `code` VALUES (95, 'VMT5V6', 'unused');
INSERT INTO `code` VALUES (96, '3648ZO', 'unused');
INSERT INTO `code` VALUES (97, 'DE37VS', 'unused');
INSERT INTO `code` VALUES (98, 'JBCBHT', 'unused');
INSERT INTO `code` VALUES (99, 'IV7W09', 'unused');
INSERT INTO `code` VALUES (100, 'RINRWZ', 'unused');

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `isbn` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书isbn',
  `title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书名',
  `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者',
  `keyword` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关键字',
  `category` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别',
  `time` date NOT NULL COMMENT '收藏时间',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  PRIMARY KEY (`isbn`, `username`) USING BTREE,
  INDEX `fk_username`(`username` ASC) USING BTREE,
  CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of favorite
-- ----------------------------
INSERT INTO `favorite` VALUES ('9787111508243', 'Java并发编程的艺术', '方腾飞 / 魏鹏 / 程晓明', '9787111508243', 'T', '2022-06-28', 'admin');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `recordId` int NOT NULL AUTO_INCREMENT COMMENT '下载记录编号',
  `title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书名',
  `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者',
  `time` date NOT NULL COMMENT '下载时间',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  PRIMARY KEY (`recordId`) USING BTREE,
  INDEX `fk_username`(`username` ASC) USING BTREE,
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, 'Java并发编程的艺术', '方腾飞 / 魏鹏 / 程晓明', '2022-06-28', 'admin');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别',
  `idCard` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证号',
  `phone` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `mail` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `registerCode` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户注册码',
  `userStateId` int NOT NULL DEFAULT 0 COMMENT '用户状态编号',
  PRIMARY KEY (`userId`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `fk_code`(`registerCode` ASC) USING BTREE,
  INDEX `fk_userStateId`(`userStateId` ASC) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`registerCode`) REFERENCES `code` (`registerCode`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`userStateId`) REFERENCES `userstate` (`userStateId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', 'Admin', 'M', '123456789987654321', '12345678911', 'admin@admin.com', 'R2V71M', 0);

-- ----------------------------
-- Table structure for userstate
-- ----------------------------
DROP TABLE IF EXISTS `userstate`;
CREATE TABLE `userstate`  (
  `userStateId` int NOT NULL COMMENT '用户状态编号',
  `userState` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户状态',
  PRIMARY KEY (`userStateId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of userstate
-- ----------------------------
INSERT INTO `userstate` VALUES (0, 'normal');
INSERT INTO `userstate` VALUES (1, 'freezing');

-- ----------------------------
-- View structure for view_book
-- ----------------------------
DROP VIEW IF EXISTS `view_book`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_book` AS select `book`.`isbn` AS `isbn`,`book`.`title` AS `title`,`book`.`author` AS `author`,`book`.`keyword` AS `keyword`,`book`.`category` AS `category`,`book`.`bookState` AS `bookstate` from `book`;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `update_code_state`;
delimiter ;;
CREATE TRIGGER `update_code_state` AFTER INSERT ON `user` FOR EACH ROW BEGIN
   UPDATE `Code` SET `codeState` = 'used' WHERE new.`registerCode` = `registerCode`;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
