CREATE DATABASE `cloud_commodity` default charset utf8;
USE `cloud_commodity`;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `tb_administrator`;
CREATE TABLE `tb_administrator` (
	`administrator_id` int(3) NOT NULL AUTO_INCREMENT,
	`user_name` varchar(30) NOT NULL,
	`pass_word` varchar(30) NOT NULL,
	`nick_name` varchar(20) NOT NULL,
	`super_level` tinyint(1) DEFAULT 0,
	`phone` bigint(12) NOT NULL,
	PRIMARY KEY (`administrator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
	`user_id` int(8) NOT NULL AUTO_INCREMENT,
	`user_name` varchar(30) NOT NULL,
	`pass_word` varchar(30) NOT NULL,
	`phone` bigint(12) NOT NULL,
	`sex` varchar(6) NOT NULL,
    `avatar` varchar(100) DEFAULT NULL,
	`nick_name` varchar(10) NOT NULL,
	PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_cart`;
CREATE TABLE `tb_cart` (
	`cart_id` bigint(12) NOT NULL AUTO_INCREMENT,
	`user_id` int(8) NOT NULL,
	`amount` double(14, 2) NOT NULL,
	PRIMARY KEY (`cart_id`),
	KEY `FK_cart_user` (`user_id`),
	CONSTRAINT `FK_cart_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=100000000000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_cart_detail`;
CREATE TABLE `tb_cart_detail`(
  `cart_detail_id` bigint(14) NOT NULL AUTO_INCREMENT,
  `cart_id` bigint(12) NOT NULL,
	`good_id` int(10) NOT NULL,
	`count` int(5) NOT NULL,
	PRIMARY KEY (`cart_detail_id`),
	KEY `FK_cart_detail` (`cart_id`),
	KEY `FK_cart_good` (`good_id`),
	CONSTRAINT `FK_cart_detail` FOREIGN KEY (`cart_id`) REFERENCES `tb_cart` (`cart_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `FK_cart_good` FOREIGN KEY (`good_id`) REFERENCES `tb_good` (`good_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000000000000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_category_first`;
CREATE TABLE `tb_category_first` (
	`category_first_id` int(6) NOT NULL AUTO_INCREMENT,
	`category_name` varchar(10) NOT NULL,
	PRIMARY KEY (`category_first_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_category_second`;
CREATE TABLE `tb_category_second` (
	`category_second_id` int(8) NOT NULL AUTO_INCREMENT,
	`category_first_id` int(6) NOT NULL,
	`category_name` varchar(10) NOT NULL,
	`image` varchar(100) NOT NULL,
	PRIMARY KEY (`category_second_id`),
	KEY `FK_category_first` (`category_first_id`),
	CONSTRAINT `FK_category_first` FOREIGN KEY (`category_first_id`) REFERENCES `tb_category_first` (`category_first_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_good`;
CREATE TABLE `tb_good` (
	`good_id` int(10) NOT NULL AUTO_INCREMENT,
	`category_second_id` int(6) NOT NULL,
	`good_name` varchar(20) NOT NULL,
	`image` varchar(100) NOT NULL,
	`price` double(10, 2) NOT NULL,
	`original_price` double(10, 2) NOT NULL,
	`inventory` int(6) NOT NULL DEFAULT 0,
	`sold_count` int(10) NOT NULL DEFAULT 0,
	`spec` varchar(20) NOT NULL,
	`origin` varchar(10) NOT NULL,
	PRIMARY KEY (`good_id`),
	KEY `FK_cateogry_1` (`category_second_id`),
	CONSTRAINT `FK_category_1` FOREIGN KEY (`category_second_id`) REFERENCES `tb_category_second` (`category_second_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
	`address_id` int(10) NOT NULL AUTO_INCREMENT,
	`user_id` int(8) NOT NULL,
	`consignee` varchar(10) NOT NULL,
	`phone` bigint(12) NOT NULL,
	`city` varchar(20) NOT NULL,
	`address` varchar(30) NOT NULL,
	`street_number` varchar(20) NOT NULL,
	`is_default` tinyint(1) DEFAULT 0,
	PRIMARY KEY (`address_id`),
	KEY `FK_user_address` (`user_id`),
	CONSTRAINT `FK_user_address` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
	`order_id` bigint(12) NOT NULL AUTO_INCREMENT,
	`user_id` int(8) NOT NULL,
	`address_id` int(10) NOT NULL,
	`amount` double(10, 2) NOT NULL,
	`create_time` DATETIME NOT NULL,
	`remarks` varchar(100) NOT NULL,
	`status` int(1) DEFAULT 0,
	PRIMARY KEY (`order_id`),
	KEY `FK_order_user` (`user_id`),
	KEY `FK_order_address` (`address_id`),
	CONSTRAINT `FK_order_user` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `FK_order_address` FOREIGN KEY (`address_id`) REFERENCES `tb_address` (`address_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=100000000000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_order_detail`;
CREATE TABLE `tb_order_detail` (
	`order_detail_id` bigint(12) NOT NULL AUTO_INCREMENT,
	`order_id` bigint(12) NOT NULL,
	`good_id` int(10) NOT NULL,
	`count` int(10) NOT NULL,
	PRIMARY KEY (`order_detail_id`),
	KEY `FK_detail_order` (`order_id`),
	KEY `FK_detail_good` (`good_id`),
	CONSTRAINT `FK_detail_order` FOREIGN KEY (`order_id`) REFERENCES `tb_order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `FK_detail_good` FOREIGN KEY (`good_id`) REFERENCES `tb_good` (`good_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=100000000000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_adv_swiper`;
CREATE TABLE `tb_adv_swiper` (
  `adv_swiper_id` int(4) NOT NULL AUTO_INCREMENT,
  `category_second_id` int(6) NOT NULL,
  `category_name` varchar(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `image` varchar(100) NOT NULL,
  PRIMARY KEY (`adv_swiper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

INSERT INTO `tb_user` VALUES (null, 'gre_yu@163.com', '111111', '15006103021', '男', null, 'greyu');
INSERT INTO `tb_user` VALUES (null, 'fun@163.com', '379108', '17847974686', '女', null, 'fanta');
INSERT INTO `tb_user` VALUES (null, 'laoge@126.com', '958302', '13225998888', '男', null, '阿尔吉斯');
INSERT INTO `tb_user` VALUES (null, 'fangshui@163.com', 'jgoa7128', '18391837193', '男', null, '弗洛伊德');
INSERT INTO `tb_user` VALUES (null, 'shenmi@163.com', 'cma1799', '17737926197', '女', null, '苦苦');
INSERT INTO `tb_user` VALUES (null, 'gaoxiao@163.com', 'haj8888', '17946962222', '女', null, '天天');
INSERT INTO `tb_user` VALUES (null, 'zhongcai@163.com', 'joah108', '15289241111', '男', null, '仲裁');

INSERT INTO `tb_administrator` VALUES (null, 'admin', 'admin', '超级管理员', 1, '17704623923');
INSERT INTO `tb_administrator` VALUES (null, 'test111', '111111', '管理员', 0, '17704623923');
INSERT INTO `tb_administrator` VALUES (null, 'furong', 'furaondj', '金桐仓库管理员', 0, '15729831723');
INSERT INTO `tb_administrator` VALUES (null, 'jintong', 'jintong66', '福荣仓库管理员', 0, '17432313728');

INSERT INTO `tb_category_first` VALUES (null, '禽鱼肉类');
INSERT INTO `tb_category_first` VALUES (null, '精品水果');
INSERT INTO `tb_category_first` VALUES (null, '优选蔬菜');
INSERT INTO `tb_category_first` VALUES (null, '粮油干货');
INSERT INTO `tb_category_first` VALUES (null, '冷餐冷冻');
INSERT INTO `tb_category_first` VALUES (null, '中外名酒');
INSERT INTO `tb_category_first` VALUES (null, '饮料冲调');
INSERT INTO `tb_category_first` VALUES (null, '休闲零食');
INSERT INTO `tb_category_first` VALUES (null, '天天鲜食');
INSERT INTO `tb_category_first` VALUES (null, '个护母婴');
INSERT INTO `tb_category_first` VALUES (null, '家具生活');
INSERT INTO `tb_category_first` VALUES (null, '礼品卡券');
