/* 查询重复的村镇数据 */
SELECT place_code,COUNT(*) AS COUNT FROM spider_address_town_village GROUP BY place_code HAVING COUNT>1;
/* 删除重复的村镇数据 */
DELETE FROM spider_address_town_village WHERE id NOT IN (SELECT minid FROM (SELECT MIN(id) AS minid FROM spider_address_town_village GROUP BY place_code) b);

/* 查询重复道路数据 */
SELECT city, road_name,COUNT(*) AS COUNT FROM spider_road GROUP BY city, road_name HAVING COUNT>1;
/* 删除重复道路 */
DELETE FROM spider_road WHERE id NOT IN (SELECT minid FROM (SELECT MIN(id) AS minid FROM spider_road GROUP BY city, road_name) b);

