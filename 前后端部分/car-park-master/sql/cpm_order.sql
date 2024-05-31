/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : cpm_order

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 09/04/2024 23:01:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cpm_order
-- ----------------------------
DROP TABLE IF EXISTS `cpm_order`;
CREATE TABLE `cpm_order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `amount` double(20, 2) NOT NULL DEFAULT 0.00 COMMENT '收入金额/元',
  `method` int NULL DEFAULT 0 COMMENT '收入方式（0现金，1支付宝，2微信，3会员卡）',
  `type` int NULL DEFAULT 0 COMMENT '收入类型（临停：0，充值：1月卡，2季卡，3年卡）',
  `carplate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `memberid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员id',
  `time` datetime NULL DEFAULT NULL COMMENT '收入时间',
  `duration` bigint NULL DEFAULT NULL COMMENT '停车时长/s',
  `parkingid` int NULL DEFAULT NULL COMMENT '停车记录id',
  `parkinglotid` int NULL DEFAULT NULL COMMENT '停车场id',
  `parkinglot_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停车场名',
  `trade_no` bigint NULL DEFAULT NULL COMMENT '交易id',
  `status` int NULL DEFAULT NULL COMMENT '是否支付，0未支付，1已支付',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cpm_order
-- ----------------------------
INSERT INTO `cpm_order` VALUES (3, 20.00, 1, 0, '皖A12312', '', '2024-04-01 06:10:26', 14400, 55, 2, '中科大西区停车场', NULL, 1);
INSERT INTO `cpm_order` VALUES (4, 10.00, 2, 3, '皖A11111', '1', '2024-03-27 22:12:38', 7200, 1, 1, '中科大东区停车场', NULL, 1);
INSERT INTO `cpm_order` VALUES (5, 5.00, 1, 3, '皖A33333', '3', '2024-03-26 22:15:27', 3600, 4, 4, '中科大北区停车场', NULL, 0);
INSERT INTO `cpm_order` VALUES (8, 15.00, 1, 1, '皖A66666', '6', '2024-04-08 15:45:31', 9000, NULL, 5, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
