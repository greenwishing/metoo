package com.metoo.core.domain.product;

import com.metoo.core.domain.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商品分类
 *
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@Table(name = "product_category")
public class ProductCategory extends Domain {

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
}
