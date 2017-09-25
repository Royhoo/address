/* 查询重复的村镇数据 */
SELECT place_code,COUNT(*) AS COUNT FROM spider_address_town_village GROUP BY place_code HAVING COUNT>1;
/* 删除重复的村镇数据 */
DELETE FROM spider_address_town_village WHERE id NOT IN (SELECT minid FROM (SELECT MIN(id) AS minid FROM spider_address_town_village GROUP BY place_code) b);

/* 查询重复道路数据 */
SELECT city, road_name,COUNT(*) AS COUNT FROM spider_road GROUP BY city, road_name HAVING COUNT>1;
/* 删除重复道路 */
DELETE FROM spider_road WHERE id NOT IN (SELECT minid FROM (SELECT MIN(id) AS minid FROM spider_road GROUP BY city, road_name) b);

/* 区划编码补全为12位 */
UPDATE spider_address_contry_only SET placeCode = CONCAT(placeCode, "000000");
/* 将一至五级数据写入app_standard_place */
INSERT INTO `app_standard_place`(CODE, NAME) SELECT placeCode, placeName FROM `spider_address_contry_only` ORDER BY id;
INSERT INTO `app_standard_place`(CODE, NAME) SELECT place_code, place_name FROM `spider_address_town_village` ORDER BY id;


