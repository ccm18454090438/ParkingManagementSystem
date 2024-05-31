/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : cpm_parking

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 06/04/2024 23:10:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for parking
-- ----------------------------
DROP TABLE IF EXISTS `parking`;
CREATE TABLE `parking`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `parkinglotid` int NULL DEFAULT NULL COMMENT '停车场id',
  `memberid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员id',
  `carplate` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `intime` datetime NULL DEFAULT NULL COMMENT '入场时间',
  `outtime` datetime NULL DEFAULT '2099-12-31 23:59:59' COMMENT '出场时间',
  `parkinglot_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停车场名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parking
-- ----------------------------
INSERT INTO `parking` VALUES (1, 1, '1', '皖A11111', '2024-03-27 20:12:08', '2024-03-27 23:42:18', '中科大东区停车场');
INSERT INTO `parking` VALUES (2, 1, '2', '皖A22222', '2024-03-25 22:13:09', '2024-03-28 16:25:00', '中科大东区停车场');
INSERT INTO `parking` VALUES (3, 3, '4', '皖A44444', '2024-03-24 22:13:47', '2024-03-25 08:46:13', '中科大南区停车场');
INSERT INTO `parking` VALUES (4, 4, '3', '皖A33333', '2024-03-26 22:15:27', '2024-03-26 23:09:15', '中科大北区停车场');
INSERT INTO `parking` VALUES (5, 4, '6', '皖A66666', '2024-03-26 22:16:02', '2024-03-27 09:17:32', '中科大北区停车场');
INSERT INTO `parking` VALUES (6, 4, '7', '皖A77777', '2024-03-22 22:16:54', '2024-03-25 10:12:23', '中科大北区停车场');
INSERT INTO `parking` VALUES (55, 2, '', '皖A12312', '2024-04-01 06:41:04', '2024-12-31 23:59:59', '中科大西区停车场');
INSERT INTO `parking` VALUES (56, 3, NULL, '皖B11222', '2024-04-01 07:28:55', '2024-12-31 23:59:59', '中科大南区停车场');
INSERT INTO `parking` VALUES (57, 3, '4', '皖A44444', '2024-04-01 15:28:55', '2024-04-01 16:41:13', '中科大南区停车场');
INSERT INTO `parking` VALUES (58, 1, '1', '皖A11111', '2024-03-26 22:16:02', '2024-12-31 23:59:59', '中科大东区停车场');
INSERT INTO `parking` VALUES (59, 3, '4', '皖A44444', '2024-03-26 23:16:02', '2024-12-31 23:59:59', '中科大南区停车场');

SET FOREIGN_KEY_CHECKS = 1;
