

-- ----------------------------
-- Table structure for user 商户会员
-- ----------------------------
DROP TABLE IF EXISTS `merchant_user`;
CREATE TABLE `merchant_user` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `merchant_id` int,
  `user_id` int,
  `creation_time` datetime,
  `status` int default 1
);

/* 初始化已下过单的商户会员 */
INSERT INTO `merchant_user` (`merchant_id`, `user_id`, `creation_time`, `status`)
    SELECT `merchant_id`, `user_id`, min(`creation_time`), 1 FROM `order` WHERE `user_id` IS NOT NULL GROUP BY `merchant_id`, `user_id`;
