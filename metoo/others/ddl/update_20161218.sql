

/* 商户增加销售数量 */

ALTER TABLE `merchant` ADD COLUMN `sales_volume` INT DEFAULT 0;

UPDATE `merchant` SET `sales_volume` = 0 WHERE `sales_volume` IS NULL;
