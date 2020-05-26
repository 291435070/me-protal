/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.241
Source Server Version : 50638
Source Host           : 192.168.0.241:3306
Source Database       : protal

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2020-05-26 18:50:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_blog
-- ----------------------------
INSERT INTO `t_blog` VALUES ('2', '测试数据2', 'https://www.baidu.com', '2020-05-26 17:50:56');
INSERT INTO `t_blog` VALUES ('3', '测试数据3', 'https://www.baidu.com', '2020-05-26 17:50:59');
INSERT INTO `t_blog` VALUES ('4', '测试数据4', 'https://www.baidu.com', '2020-05-26 18:02:16');
INSERT INTO `t_blog` VALUES ('5', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:04:40');
INSERT INTO `t_blog` VALUES ('6', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:05:23');
INSERT INTO `t_blog` VALUES ('7', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:05:23');
INSERT INTO `t_blog` VALUES ('8', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:05:23');
INSERT INTO `t_blog` VALUES ('9', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:05:24');
INSERT INTO `t_blog` VALUES ('10', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:05:24');
INSERT INTO `t_blog` VALUES ('11', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:05:24');
INSERT INTO `t_blog` VALUES ('12', '这是个文件', '文件在哪里。。。', '2020-05-26 18:05:25');
INSERT INTO `t_blog` VALUES ('13', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:05:27');
INSERT INTO `t_blog` VALUES ('14', '这是个文件', '文件在哪里。。。', '2020-05-26 18:05:28');
INSERT INTO `t_blog` VALUES ('16', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:25:31');
INSERT INTO `t_blog` VALUES ('22', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:29:19');
INSERT INTO `t_blog` VALUES ('23', '测试数据5', 'https://www.baidu.com', '2020-05-26 18:29:53');
