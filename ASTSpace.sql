/*
Navicat MySQL Data Transfer

Source Server         : MySqlServer
Source Server Version : 50544
Source Host           : 10.67.129.123:3306
Source Database       : ASTSpace

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2018-07-16 20:24:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for actives_info
-- ----------------------------
DROP TABLE IF EXISTS `actives_info`;
CREATE TABLE `actives_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `activeHead` longtext,
  `activeBody` longtext,
  `activeAuthor` varchar(255) DEFAULT NULL,
  `activeCount` int(255) DEFAULT NULL,
  `activeUserCount` int(10) DEFAULT '0',
  `activeBrowersCount` int(10) DEFAULT '0',
  `activePublishDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `activeTypeName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for joins_info
-- ----------------------------
DROP TABLE IF EXISTS `joins_info`;
CREATE TABLE `joins_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `userAge` varchar(255) DEFAULT NULL,
  `activeId` int(11) DEFAULT NULL,
  `activeName` varchar(255) DEFAULT NULL,
  `userPhone` varchar(255) DEFAULT NULL,
  `joinDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `payNum` varchar(255) DEFAULT '0',
  `payOrder` varchar(255) DEFAULT '0',
  `otherInfo` varchar(255) DEFAULT NULL,
  `activeSession` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users_info
-- ----------------------------
DROP TABLE IF EXISTS `users_info`;
CREATE TABLE `users_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `passWord` varchar(255) DEFAULT NULL,
  `userSex` varchar(255) DEFAULT NULL,
  `userAge` varchar(255) DEFAULT NULL,
  `userRegisterDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `userHomeRegion` varchar(255) DEFAULT NULL,
  `emailAddr` varchar(255) DEFAULT NULL,
  `userPhoneNum` varchar(255) DEFAULT NULL,
  `userPrivilege` varchar(255) DEFAULT NULL,
  `otherInfo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
