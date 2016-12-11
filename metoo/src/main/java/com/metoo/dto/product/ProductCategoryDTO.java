package com.metoo.dto.product;

import com.metoo.core.domain.product.ProductCategory;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class ProductCategoryDTO {

    private Long id;
    private String name;
    private String description;

    public ProductCategoryDTO() {
    }

    public ProductCategoryDTO(ProductCategory category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
