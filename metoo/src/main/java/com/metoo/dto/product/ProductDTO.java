package com.metoo.dto.product;

import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.product.*;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.utils.JodaUtils;
import com.metoo.utils.JsonUtils;
import com.metoo.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class ProductDTO {

    private Long id;
    private String type;
    private MerchantDTO merchant = new MerchantDTO();
    private ProductCategoryDTO category = new ProductCategoryDTO();
    private String name;
    private String description;
    private String price;
    private String marketingPrice;

    // food
    private String expiryDate;
    private List<ProductNotice> notices = new ArrayList<>();
    private String article;

    // hotel
    private boolean hasBreakfast;
    private boolean hasWindow;

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        Merchant merchant = product.getMerchant();
        ProductCategory category = product.getCategory();

        this.type = product.getClass().getSimpleName();
        this.merchant = new MerchantDTO(merchant);
        this.category = new ProductCategoryDTO(category);
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = NumberUtils.toRealString(product.getPrice());
        this.marketingPrice = NumberUtils.toRealString(product.getMarketingPrice());

        if (product instanceof Food) {
            Food food = (Food) product;
            this.expiryDate = JodaUtils.localDateToString(food.getExpiryDate());
            String notices = food.getNotices();
            this.notices = JsonUtils.toBeanList(notices, ProductNotice.class);
            this.article = food.getArticle();
        } else if (product instanceof Hotel) {
            Hotel hotel = (Hotel) product;
            this.hasBreakfast = hotel.isHasBreakfast();
            this.hasWindow = hotel.isHasWindow();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MerchantDTO getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantDTO merchant) {
        this.merchant = merchant;
    }

    public ProductCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(ProductCategoryDTO category) {
        this.category = category;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarketingPrice() {
        return marketingPrice;
    }

    public void setMarketingPrice(String marketingPrice) {
        this.marketingPrice = marketingPrice;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<ProductNotice> getNotices() {
        return notices;
    }

    public void setNotices(List<ProductNotice> notices) {
        this.notices = notices;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public boolean isHasBreakfast() {
        return hasBreakfast;
    }

    public void setHasBreakfast(boolean hasBreakfast) {
        this.hasBreakfast = hasBreakfast;
    }

    public boolean isHasWindow() {
        return hasWindow;
    }

    public void setHasWindow(boolean hasWindow) {
        this.hasWindow = hasWindow;
    }

    public static List<ProductDTO> toDTOs(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO(product);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }
}
