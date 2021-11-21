/*
 Navicat Premium Data Transfer

 Source Server         : dev
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : forex_common

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 21/11/2021 18:35:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_exchange_rate
-- ----------------------------
DROP TABLE IF EXISTS `t_exchange_rate`;
CREATE TABLE `t_exchange_rate`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `source` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `dest` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `rate` decimal(30, 6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_exchange_rate
-- ----------------------------
INSERT INTO `t_exchange_rate` VALUES (1, 'CNY', 'USD', 0.142857);
INSERT INTO `t_exchange_rate` VALUES (2, 'USD', 'CNY', 7.000000);

SET FOREIGN_KEY_CHECKS = 1;
