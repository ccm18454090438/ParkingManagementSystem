/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : cpm_member

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 06/04/2024 23:10:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '停车场卡信息表',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `type` int NULL DEFAULT NULL COMMENT '卡类型(1是季卡，2是月卡，3是年卡）',
  `dayleft` int NOT NULL DEFAULT 0 COMMENT '剩余天数',
  `createtime` date NULL DEFAULT NULL COMMENT '创建日期',
  `carplate` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `basedate` date NULL DEFAULT NULL COMMENT '当前日期-基准日期=已用天数（<= 剩余天数为可用）',
  `parkinglotid` int NULL DEFAULT NULL COMMENT '停车场id',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `parkinglot_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停车场名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (1, 'jade', 2, 90, '2024-03-28', '皖A11110', '2024-03-28', 1, '18912341234', '中科大东区停车场');
INSERT INTO `member` VALUES (2, 'rose', 1, 30, '2024-03-28', '皖A22222', '2024-03-28', 1, '17788889999', '中科大东区停车场');
INSERT INTO `member` VALUES (3, 'tom', 3, 365, '2024-03-28', '皖A33333', '2024-03-28', 2, '16688886666', '中科大西区停车场');
INSERT INTO `member` VALUES (4, 'henry', 2, 118, '2024-03-26', '皖A44444', '2024-03-26', 1, '15577779999', '中科大东区停车场');
INSERT INTO `member` VALUES (5, 'nick', 3, 360, '2024-03-23', '皖A55555', '2024-03-23', 4, '16677775555', '中科大北区停车场');
INSERT INTO `member` VALUES (6, 'smith', 1, 20, '2024-03-18', '皖A66666', '2024-03-18', 4, '19956785678', '中科大北区停车场');
INSERT INTO `member` VALUES (7, 'marry', 2, 120, '2024-03-28', '皖A77777', '2024-03-28', 2, '18812341234', '中科大西区停车场');
INSERT INTO `member` VALUES (22, 'jeff', 2, 120, '2024-03-31', '浙A12345', '2024-03-31', 1, '18912341234', '中科大东区停车场');
INSERT INTO `member` VALUES (23, 'henry', 2, 120, '2024-03-31', '浙A12345', '2024-03-31', 1, '18912341234', '中科大东区停车场');
INSERT INTO `member` VALUES (32, 'levis', 2, 120, '2024-04-05', '沪A33006', '2024-04-05', NULL, '18899990000', '中科大高新区停车场');
INSERT INTO `member` VALUES (35, 'lee', 3, 365, '2024-04-05', '苏A58888', '2024-04-05', NULL, '17799991111', '中科大中区停车场');

SET FOREIGN_KEY_CHECKS = 1;
