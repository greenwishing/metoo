
/* 订单增加状态字段 */
ALTER TABLE `order` ADD COLUMN `status`  varchar(255);


/* 订单编号使用自增ID，默认从 10000 开始 */
ALTER TABLE `order` AUTO_INCREMENT=10000;
