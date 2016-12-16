

/* 主要表增加状态，所有数据不物理删除，仅标记为删除，删除的记录不再显示 */

ALTER TABLE `user` add COLUMN `status` int DEFAULT 1;
ALTER TABLE  `merchant` add COLUMN `status` int DEFAULT 1;
ALTER TABLE `product_category` add COLUMN `status` int DEFAULT 1;
ALTER TABLE `product` add COLUMN `status` int DEFAULT 1;
ALTER TABLE `feedback` add COLUMN `status` int DEFAULT 1;

