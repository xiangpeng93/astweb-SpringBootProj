/*
Navicat MySQL Data Transfer

Source Server         : funmaker.tech
Source Server Version : 50556
Source Host           : 47.75.127.61:3306
Source Database       : ASTSpace

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-07-20 08:46:39
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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for comments_info
-- ----------------------------
DROP TABLE IF EXISTS `comments_info`;
CREATE TABLE `comments_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `communityId` int(11) DEFAULT NULL,
  `authorName` varchar(255) DEFAULT NULL,
  `publishDate` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `commentBody` longtext,
  `commentFavour` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for community_info
-- ----------------------------
DROP TABLE IF EXISTS `community_info`;
CREATE TABLE `community_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `communityHead` longtext,
  `communityBody` longtext,
  `communityAuthor` varchar(255) DEFAULT NULL,
  `communityBrowersCount` int(10) DEFAULT '0',
  `communityPublishDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `communityGroup` varchar(255) DEFAULT NULL,
  `communityPrivilege` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;
