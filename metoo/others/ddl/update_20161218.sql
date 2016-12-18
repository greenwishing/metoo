

/* 商品增加销售数量 */

ALTER TABLE `product` ADD COLUMN `sales_volume` INT DEFAULT 0;

UPDATE `product` SET `sales_volume` = 0 WHERE `sales_volume` IS NULL;
