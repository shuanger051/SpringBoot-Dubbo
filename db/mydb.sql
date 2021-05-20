/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.3.199
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : 192.168.3.199:3306
 Source Schema         : mydb

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 04/02/2021 18:06:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for um_t_role
-- ----------------------------
DROP TABLE IF EXISTS `um_t_role`;
CREATE TABLE `um_t_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_time` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of um_t_role
-- ----------------------------
INSERT INTO `um_t_role` VALUES (1, '管理员拥有所有接口操作权限', 1611301928, '管理员', 'ADMIN');
INSERT INTO `um_t_role` VALUES (2, '普通拥有查看用户列表与修改密码权限，不具备对用户增删改权限', 1611301928, '普通用户', 'USER');

-- ----------------------------
-- Table structure for um_t_role_user
-- ----------------------------
DROP TABLE IF EXISTS `um_t_role_user`;
CREATE TABLE `um_t_role_user`  (
  `role_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  UNIQUE INDEX `UK_o2eh9w9ha9qh5557mfyw1gxv1`(`user_id`) USING BTREE,
  INDEX `FK1pk6e2w79ro1xo8uus7q32pnf`(`role_id`) USING BTREE,
  CONSTRAINT `FK1pk6e2w79ro1xo8uus7q32pnf` FOREIGN KEY (`role_id`) REFERENCES `um_t_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKh2d913l31o8vjsgpfkh10bkyw` FOREIGN KEY (`user_id`) REFERENCES `um_t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of um_t_role_user
-- ----------------------------
INSERT INTO `um_t_role_user` VALUES (1, 1);

-- ----------------------------
-- Table structure for um_t_user
-- ----------------------------
DROP TABLE IF EXISTS `um_t_user`;
CREATE TABLE `um_t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_time` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of um_t_user
-- ----------------------------
INSERT INTO `um_t_user` VALUES (1, 'admin', '系统默认管理员', '123456', '小小丰', NULL);

-- ----------------------------
-- Table structure for user_point
-- ----------------------------
DROP TABLE IF EXISTS `user_point`;
CREATE TABLE `user_point`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `points` bigint(20) NOT NULL DEFAULT 0 COMMENT '积分',
  `user_level` int(1) NOT NULL DEFAULT 1 COMMENT '用户等级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_point
-- ----------------------------
INSERT INTO `user_point` VALUES (1, 1, 10010, 1);

SET FOREIGN_KEY_CHECKS = 1;
