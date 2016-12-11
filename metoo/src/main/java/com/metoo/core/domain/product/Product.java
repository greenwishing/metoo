package com.metoo.core.domain.product;

import com.metoo.core.domain.Domain;
import com.metoo.core.domain.merchant.Merchant;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品
 *
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 单表记录继承结构
@DiscriminatorColumn(name = "type") // 鉴别器字段
public abstract class Product extends Domain {

    /**
     * 商品所属商户
     */
    @JoinColumn(name ="merchant_id")
    @ManyToOne(targetEntity = Merchant.class)
    private Merchant merchant;

    /**
     * 商品分类
     */
    @JoinColumn(name = "category_id")
    @ManyToOne(targetEntity = ProductCategory.class)
    private ProductCategory category;

    /**
     * 商品名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 简介
     */
    @Column(name = "description")
    private String description;

    /**
     * 单价
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 市场参考价格
     */
    @Column(name = "marketing_price")
    private BigDecimal marketingPrice;

    public Product() {
    }

    protected Product(Merchant merchant, ProductCategory category) {
        this.merchant = merchant;
        this.category = category;
    }

    public void update(String name, String description, BigDecimal price, BigDecimal marketingPrice) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.marketingPrice = marketingPrice;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getMarketingPrice() {
        return marketingPrice;
    }
}
