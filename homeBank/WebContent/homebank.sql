/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50200
Source Host           : 127.0.0.1:3306
Source Database       : homebank

Target Server Type    : MYSQL
Target Server Version : 50200
File Encoding         : 65001

Date: 2015-08-18 01:18:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for buget
-- ----------------------------
DROP TABLE IF EXISTS `buget`;
CREATE TABLE `buget` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `month` char(6) NOT NULL,
  `value` varchar(10) NOT NULL,
  `unit` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of buget
-- ----------------------------

-- ----------------------------
-- Table structure for datadict
-- ----------------------------
DROP TABLE IF EXISTS `datadict`;
CREATE TABLE `datadict` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `catalog` varchar(50) DEFAULT NULL COMMENT '数据类型',
  `code` varchar(50) DEFAULT NULL COMMENT '数据代码',
  `codename` varchar(200) DEFAULT NULL COMMENT '数据含义',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of datadict
-- ----------------------------
INSERT INTO `datadict` VALUES ('1', 'root', 'payout', '支出');
INSERT INTO `datadict` VALUES ('2', 'root', 'income', '收入');
INSERT INTO `datadict` VALUES ('3', 'root', 'currency', '币种');
INSERT INTO `datadict` VALUES ('4', 'root', 'card', '卡类别');
INSERT INTO `datadict` VALUES ('40', 'payout', 'FZ', '房租');
INSERT INTO `datadict` VALUES ('38', 'card', 'BJYH', '北京银行');
INSERT INTO `datadict` VALUES ('33', 'currency', 'MY', '美元');
INSERT INTO `datadict` VALUES ('32', 'currency', 'RMB', '元');
INSERT INTO `datadict` VALUES ('39', 'card', 'ZSYH', '招商银行');
INSERT INTO `datadict` VALUES ('41', 'payout', 'CS', '超市');
INSERT INTO `datadict` VALUES ('42', 'payout', 'MC', '买菜');
INSERT INTO `datadict` VALUES ('43', 'income', 'GZ', '工资');
INSERT INTO `datadict` VALUES ('44', 'income', 'GWK', '购物卡');
INSERT INTO `datadict` VALUES ('45', 'payout', 'MYF', '买衣服');
INSERT INTO `datadict` VALUES ('46', 'payout', 'MSG', '买水果');
INSERT INTO `datadict` VALUES ('48', 'payout', 'JC', '借出');
INSERT INTO `datadict` VALUES ('49', 'income', 'JR', '借入');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` decimal(10,0) NOT NULL,
  `menuname` varchar(50) NOT NULL,
  `url` varchar(100) DEFAULT NULL,
  `parentid` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '基础信息管理', null, '1');
INSERT INTO `menu` VALUES ('2', '基础数据', 'jsp/datadict.jsp', '1');
INSERT INTO `menu` VALUES ('3', '收支管理', null, '3');
INSERT INTO `menu` VALUES ('4', '收入管理', 'jsp/income.jsp', '3');
INSERT INTO `menu` VALUES ('5', '支出管理', 'jsp/payout.jsp', '3');
INSERT INTO `menu` VALUES ('6', '预算管理', null, '6');
INSERT INTO `menu` VALUES ('7', '预算设定', 'jsp/buget.jsp', '6');

-- ----------------------------
-- Table structure for payments
-- ----------------------------
DROP TABLE IF EXISTS `payments`;
CREATE TABLE `payments` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `value` varchar(10) NOT NULL COMMENT '收支值',
  `name` varchar(200) DEFAULT NULL COMMENT '收支名称',
  `paymenttype` char(1) NOT NULL COMMENT '收或支',
  `unit` varchar(50) NOT NULL COMMENT '值的单位',
  `descript` varchar(1000) DEFAULT NULL COMMENT '描述',
  `day` char(10) NOT NULL COMMENT '收支时间',
  `crttime` datetime DEFAULT NULL COMMENT '记录时间',
  `type` varchar(50) NOT NULL COMMENT '收支类型',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payments
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `isValid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('6f60af2d074141ccb4e835906fdefe66', 'admin', '4392c4f26c8de6034f758c61331d6e60', '超级管理员', '2015-07-27 14:08:12', null, 'DEFAULT', 'Y');

-- ----------------------------
-- Table structure for user_login
-- ----------------------------
DROP TABLE IF EXISTS `user_login`;
CREATE TABLE `user_login` (
  `id` varchar(32) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `ip_address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_login
-- ----------------------------

-- ----------------------------
-- View structure for vbuget
-- ----------------------------
DROP VIEW IF EXISTS `vbuget`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `vbuget` AS (select `t1`.`id` AS `id`,`t1`.`month` AS `month`,`t1`.`value` AS `value`,`t1`.`unit` AS `unit`,`t2`.`codename` AS `unitname` from (`buget` `t1` left join `datadict` `t2` on(((`t1`.`unit` = `t2`.`code`) and (`t2`.`catalog` = 'currency'))))) ;

-- ----------------------------
-- View structure for vpayments
-- ----------------------------
DROP VIEW IF EXISTS `vpayments`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `vpayments` AS (select `t`.`id` AS `id`,`t`.`value` AS `value`,`t`.`name` AS `name`,`t`.`paymenttype` AS `paymenttype`,`t`.`unit` AS `unit`,`t`.`descript` AS `descript`,`t`.`day` AS `day`,`t`.`crttime` AS `crttime`,`t`.`type` AS `type`,`t1`.`codename` AS `unitname`,`t2`.`codename` AS `typename` from ((`payments` `t` left join `datadict` `t1` on(((`t`.`unit` = `t1`.`code`) and (`t1`.`catalog` = 'currency')))) left join `datadict` `t2` on(((`t`.`type` = `t2`.`code`) and (`t2`.`catalog` = (case when (`t`.`paymenttype` = '2') then 'payout' else 'income' end)))))) ;
