CREATE DATABASE place;
CREATE TABLE `place`.`spider_address_contry_only`(
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `placeCode` VARCHAR(12) NOT NULL COMMENT '区划编码',
  `placeName` VARCHAR(100) NOT NULL COMMENT '地名',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8
  COMMENT='爬去的三级以内区划数据';
