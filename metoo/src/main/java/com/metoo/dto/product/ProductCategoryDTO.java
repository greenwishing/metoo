package com.metoo.dto.product;

import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.product.ProductCategory;
import com.metoo.dto.StatefulDTO;
import com.metoo.dto.merchant.MerchantDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class ProductCategoryDTO extends StatefulDTO {

    private MerchantDTO merchant = new MerchantDTO();
    private String name;
    private String description;

    public ProductCategoryDTO() {
    }

    public ProductCategoryDTO(ProductCategory category) {
        super(category);
        Merchant merchant = category.getMerchant();
        this.merchant = new MerchantDTO(merchant);
        this.name = category.getName();
        this.description = category.getDescription();
    }

    public MerchantDTO getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantDTO merchant) {
        this.merchant = merchant;
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

    public static List<ProductCategoryDTO> toDTOs(List<ProductCategory> categories) {
        List<ProductCategoryDTO> categoryDTOs = new ArrayList<>();
        for (ProductCategory category : categories) {
            ProductCategoryDTO categoryDTO = new ProductCategoryDTO(category);
            categoryDTOs.add(categoryDTO);
        }
        return categoryDTOs;
    }
}
