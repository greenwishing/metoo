
-- ----------------------------
-- Table structure for user 管理员/用户
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255) UNIQUE KEY,
  `username` varchar(255),
  `type` varchar(255),
  `password` varchar(255),
  `creation_time` datetime
);

-- ----------------------------
-- Table structure for merchant 商户
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `picture` varchar(255),
  `business_type` varchar(255),
  `features` int,
  `introduction` varchar(255),
  `level` int,
  `specialty` varchar(255),
  `address` varchar(255),
  `contact_phone` varchar(255)
);

-- ----------------------------
-- Table structure for order 订单
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `merchant_id` bigint,
  `product_id` bigint,
  `user_id` bigint,
  `type` varchar(31),
  `days` int,
  `quantity` int,
  `booking_date` date,
  `creation_time` datetime,
  `telephone` varchar(255),
  `username` varchar(255)
);

-- ----------------------------
-- Table structure for product_category 商品分类
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `description` text
);

-- ----------------------------
-- Table structure for product 商品
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `merchant_id` bigint,
  `category_id` bigint,
  `type` varchar(31),
  `name` varchar(255),
  `price` decimal(10,2),
  `marketing_price` decimal(10,2),
  `breakfast` bit,
  `window` bit,
  `description` varchar(255),
  `article` text,
  `expiry_date` date,
  `notices` text
);

-- ----------------------------
-- Table structure for feedback 反馈
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `user_id` bigint,
  `creation_time` datetime,
  `business_type` varchar(255),
  `description` text,
  `username` varchar(255),
  `telephone` varchar(255),
  `email` varchar(255)
);
