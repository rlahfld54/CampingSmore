SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `best_item` cascade;
CREATE TABLE IF NOT EXISTS `best_item` (
    `iitem` bigint(15) unsigned NOT NULL,
    `month_like` date NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`iitem`),
    CONSTRAINT `best_item_ibfk_1` FOREIGN KEY (`iitem`) REFERENCES `item` (`iitem`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `board` cascade;
CREATE TABLE IF NOT EXISTS `board` (
    `iboard` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iuser` bigint(15) unsigned NOT NULL,
    `icategory` bigint(15) unsigned NOT NULL,
    `title` varchar(20) NOT NULL,
    `ctnt` varchar(300) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `del_yn` tinyint(1) NOT NULL DEFAULT 1,
    `board_view` bigint(20) unsigned NOT NULL DEFAULT 0,
    PRIMARY KEY (`iboard`),
    KEY `iuser` (`iuser`),
    KEY `icategory` (`icategory`),
    CONSTRAINT `board_ibfk_1` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`),
    CONSTRAINT `board_ibfk_3` FOREIGN KEY (`icategory`) REFERENCES `board_category` (`icategory`)
    ) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `board_category` cascade;
CREATE TABLE IF NOT EXISTS `board_category` (
    `icategory` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(20) NOT NULL,
    PRIMARY KEY (`icategory`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `board_image` cascade;
CREATE TABLE IF NOT EXISTS `board_image` (
    `iboardpic` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iboard` bigint(15) unsigned NOT NULL,
    `pic` varchar(200) NOT NULL,
    PRIMARY KEY (`iboardpic`),
    KEY `iboard` (`iboard`),
    CONSTRAINT `board_image_ibfk_1` FOREIGN KEY (`iboard`) REFERENCES `board` (`iboard`)
    ) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `cart` cascade;
CREATE TABLE IF NOT EXISTS `cart` (
    `icart` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iuser` bigint(15) unsigned NOT NULL,
    `iitem` bigint(15) unsigned NOT NULL,
    `quantity` tinyint(2) NOT NULL DEFAULT 1,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`icart`),
    KEY `iitem` (`iitem`),
    KEY `iuser` (`iuser`),
    CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`iitem`) REFERENCES `item` (`iitem`),
    CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`)
    ) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `comment` cascade;
CREATE TABLE IF NOT EXISTS `comment` (
    `icomment` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iboard` bigint(15) unsigned NOT NULL,
    `iuser` bigint(15) unsigned NOT NULL,
    `ctnt` varchar(100) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `del_yn` tinyint(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (`icomment`),
    KEY `iboard` (`iboard`),
    KEY `iuser` (`iuser`),
    CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`iboard`) REFERENCES `board` (`iboard`),
    CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`),
    CONSTRAINT `del_yn` CHECK (`del_yn` in ('0','1'))
    ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `item` cascade;
CREATE TABLE IF NOT EXISTS `item` (
    `iitem` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iitem_category` bigint(15) unsigned NOT NULL,
    `name` varchar(100) NOT NULL,
    `price` int(10) unsigned NOT NULL,
    `info` varchar(255) DEFAULT NULL,
    `pic` varchar(100) DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `del_yn` tinyint(1) DEFAULT 1 CHECK (`del_yn` in ('0','1')),
    PRIMARY KEY (`iitem`),
    KEY `iitem_category` (`iitem_category`),
    CONSTRAINT `item_ibfk_1` FOREIGN KEY (`iitem_category`) REFERENCES `item_category` (`iitem_category`)
    ) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `item_category` cascade;
CREATE TABLE IF NOT EXISTS `item_category` (
    `iitem_category` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`iitem_category`)
    ) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `item_detail_pic` cascade;
CREATE TABLE IF NOT EXISTS `item_detail_pic` (
    `idetail` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iitem` bigint(15) unsigned NOT NULL,
    `pic` text NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`idetail`),
    KEY `iitem` (`iitem`),
    CONSTRAINT `item_detail_pic_ibfk_1` FOREIGN KEY (`iitem`) REFERENCES `item` (`iitem`)
    ) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `order` cascade;
CREATE TABLE IF NOT EXISTS `order` (
    `iorder` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iuser` bigint(15) unsigned NOT NULL,
    `address` varchar(100) NOT NULL,
    `address_detail` varchar(100) DEFAULT NULL,
    `total_price` bigint(11) unsigned NOT NULL,
    `shipping_price` int(10) unsigned NOT NULL DEFAULT 3000,
    `shipping_memo` varchar(100) DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `del_yn` tinyint(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (`iorder`),
    KEY `iuser` (`iuser`),
    CONSTRAINT `order_ibfk_1` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`)
    ) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `order_item` cascade;
CREATE TABLE IF NOT EXISTS `order_item` (
    `iorder` bigint(15) unsigned NOT NULL,
    `iitem` bigint(15) unsigned NOT NULL,
    `price` int(10) unsigned NOT NULL,
    `quantity` tinyint(2) unsigned NOT NULL DEFAULT 1,
    `total_price` bigint(20) unsigned NOT NULL,
    `refund` tinyint(1) NOT NULL DEFAULT 0 CHECK (`refund` in ('0','1','2')),
    `del_yn` tinyint(1) NOT NULL DEFAULT 1,
    KEY `iorder` (`iorder`),
    KEY `iitem` (`iitem`),
    CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`iorder`) REFERENCES `order` (`iorder`),
    CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`iitem`) REFERENCES `item` (`iitem`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `review` cascade;
CREATE TABLE IF NOT EXISTS `review` (
    `ireview` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iuser` bigint(15) unsigned NOT NULL,
    `iorder` bigint(15) unsigned NOT NULL,
    `iitem` bigint(15) unsigned NOT NULL,
    `review_ctnt` varchar(200) NOT NULL,
    `pic` varchar(200) DEFAULT NULL,
    `star_rating` tinyint(1) NOT NULL DEFAULT 5 CHECK (`star_rating` in (1,2,3,4,5)),
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `review_like` int(10) unsigned DEFAULT NULL,
    PRIMARY KEY (`ireview`),
    KEY `iuser` (`iuser`),
    KEY `iorder` (`iorder`),
    KEY `iitem` (`iitem`),
    CONSTRAINT `review_ibfk_1` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`),
    CONSTRAINT `review_ibfk_2` FOREIGN KEY (`iorder`) REFERENCES `order` (`iorder`),
    CONSTRAINT `review_ibfk_3` FOREIGN KEY (`iitem`) REFERENCES `item` (`iitem`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `shipping_address` cascade;
CREATE TABLE IF NOT EXISTS `shipping_address` (
    `iaddress` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iuser` bigint(15) unsigned NOT NULL,
    `address` varchar(100) NOT NULL,
    `address_detail` varchar(100) DEFAULT NULL,
    `name` varchar(20) NOT NULL,
    `phone` varchar(11) NOT NULL,
    PRIMARY KEY (`iaddress`),
    KEY `iuser` (`iuser`),
    CONSTRAINT `shipping_address_ibfk_1` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `user` cascade;
CREATE TABLE IF NOT EXISTS `user` (
    `iuser` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(20) NOT NULL,
    `password` varchar(255) NOT NULL,
    `email` varchar(100) NOT NULL,
    `name` varchar(20) NOT NULL,
    `birth_date` varchar(10) NOT NULL,
    `phone` varchar(11) NOT NULL,
    `gender` tinyint(1) NOT NULL DEFAULT 0,
    `user_address` varchar(100) NOT NULL,
    `user_address_detail` varchar(100) DEFAULT NULL,
    `role` varchar(10) NOT NULL DEFAULT '0',
    `pic` varchar(100) DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `del_yn` tinyint(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (`iuser`),
    UNIQUE KEY `user_id` (`user_id`),
    CONSTRAINT `gender` CHECK (`gender` in (0,1))
    ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `user_token` cascade;
CREATE TABLE IF NOT EXISTS `user_token` (
    `iuser` bigint(20) unsigned NOT NULL,
    `ip` varchar(15) NOT NULL,
    `access_token` varchar(200) NOT NULL,
    `refresh_token` varchar(200) NOT NULL,
    `created_at` datetime DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`iuser`,`ip`) USING BTREE,
    CONSTRAINT `user_token_ibfk_1` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `wishlist` cascade;
CREATE TABLE IF NOT EXISTS `wishlist` (
    `iwish` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
    `iuser` bigint(15) unsigned NOT NULL,
    `iitem` bigint(15) unsigned NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `del_yn` tinyint(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (`iwish`),
    KEY `iuser` (`iuser`),
    KEY `iitem` (`iitem`),
    CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`iuser`) REFERENCES `user` (`iuser`),
    CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`iitem`) REFERENCES `item` (`iitem`),
    CONSTRAINT `del_yn` CHECK (`del_yn` in (0,1))
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
SET foreign_key_checks = 1;