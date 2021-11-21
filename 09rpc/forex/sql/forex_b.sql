/*
 Navicat Premium Data Transfer

 Source Server         : dev
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : forex_b

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 21/11/2021 18:35:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_cny_account
-- ----------------------------
DROP TABLE IF EXISTS `t_cny_account`;
CREATE TABLE `t_cny_account`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(30, 6) NOT NULL,
  `frozen_balance` decimal(30, 6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cny_account
-- ----------------------------
INSERT INTO `t_cny_account` VALUES (1, 2, 10140.000000, 0.000000);

-- ----------------------------
-- Table structure for t_usd_account
-- ----------------------------
DROP TABLE IF EXISTS `t_usd_account`;
CREATE TABLE `t_usd_account`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance` decimal(30, 6) NOT NULL,
  `frozen_balance` decimal(30, 6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_usd_account
-- ----------------------------
INSERT INTO `t_usd_account` VALUES (1, 2, 9979.999999, 0.000000);

SET FOREIGN_KEY_CHECKS = 1;
