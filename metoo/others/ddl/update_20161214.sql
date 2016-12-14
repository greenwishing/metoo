

/* 商品分类增加商户ID字段 */
alter table product_category add column merchant_id int after id;