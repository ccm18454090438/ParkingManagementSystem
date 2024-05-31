/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : cpm_parkinglot

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 06/04/2024 23:10:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for parkinglot
-- ----------------------------
DROP TABLE IF EXISTS `parkinglot`;
CREATE TABLE `parkinglot`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `total` int NOT NULL COMMENT '车位总数',
  `car_amount` int NULL DEFAULT NULL COMMENT '已停车数量',
  `left_amount` int NULL DEFAULT NULL COMMENT '剩余车位数',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停车场名',
  `administrator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员联系方式',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停车场位置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 268 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parkinglot
-- ----------------------------
INSERT INTO `parkinglot` VALUES (1, 200, 2, 198, '中科大东区停车场', '张三', '18888888888', '中科大东校区');
INSERT INTO `parkinglot` VALUES (2, 150, 0, 150, '中科大西区停车场', '李四', '16666666666', '中科大西校区');
INSERT INTO `parkinglot` VALUES (3, 100, 1, 99, '中科大南区停车场', '王五', '19999999999', '中科大南校区');
INSERT INTO `parkinglot` VALUES (4, 60, 3, 57, '中科大北区停车场', '赵六', '17777777777', '中科大北校区');
INSERT INTO `parkinglot` VALUES (5, 80, 1, 79, '中科大中区停车场', '陈七', '18890909090', '中科大中校区');
INSERT INTO `parkinglot` VALUES (6, 300, 0, 300, '中科大高新区停车场', '孔九', '166000011111', '中科大高新校区');

SET FOREIGN_KEY_CHECKS = 1;
