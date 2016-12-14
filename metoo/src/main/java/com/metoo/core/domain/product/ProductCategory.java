package com.metoo.core.domain.product;

import com.metoo.core.domain.Domain;
import com.metoo.core.domain.merchant.Merchant;

import javax.persistence.*;

/**
 * 商品分类
 *
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@Table(name = "product_category")
public class ProductCategory extends Domain {

    @JoinColumn(name = "merchant_id")
    @ManyToOne(targetEntity = Merchant.class)
    private Merchant merchant;

    /**
     * 分类名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    public ProductCategory() {
    }

    public ProductCategory(Merchant merchant) {
        this.merchant = merchant;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
