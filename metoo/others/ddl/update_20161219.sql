

/* 商户增加管理员 */
ALTER TABLE `merchant` ADD COLUMN `manager_id` INT;


/* 为已有的商户创建管理员帐号 email 生成规则为：mch{merchant_id}@metoo.com */
INSERT INTO `user` (`email`,`username`, `type`,`password`,`creation_time`)
 SELECT CONCAT('mch', id, '@metoo.com'), CONCAT('mch', id), 'MERCHANT_MANAGER', '202cb962ac59075b964b07152d234b70', now() FROM `merchant`;


/* 关联商户的管理员帐号 */
UPDATE `merchant` m SET m.`manager_id`=(SELECT id FROM `user` WHERE `type`='MERCHANT_MANAGER' AND CONVERT(substr(`username`,-1),SIGNED)=m.id) WHERE m.`manager_id` IS NULL;
