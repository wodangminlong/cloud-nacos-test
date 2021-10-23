/*
 Navicat Premium Data Transfer

 Source Server         : 100
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 192.168.10.100:3306
 Source Schema         : dev_test

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 23/10/2021 12:06:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_test
-- ----------------------------
DROP TABLE IF EXISTS `tb_test`;
CREATE TABLE `tb_test`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'name',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'test' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_test
-- ----------------------------
INSERT INTO `tb_test` VALUES (1, '知足常乐');

SET FOREIGN_KEY_CHECKS = 1;
