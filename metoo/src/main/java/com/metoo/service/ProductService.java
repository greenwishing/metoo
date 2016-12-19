package com.metoo.service;

import com.metoo.dto.product.ProductCategoryDTO;
import com.metoo.dto.product.ProductDTO;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/19
 */
public interface ProductService {

    List<ProductDTO> loadProducts(Long merchantId);

    List<ProductCategoryDTO> loadProductCategories(Long merchantId);

    void saveOrUpdateProduct(ProductDTO productDTO);

    ProductDTO loadProductById(Long id);

    void toggleProductStatus(Long id);

    ProductCategoryDTO loadProductCategoryById(Long id);

    void saveOrUpdateProductCategory(ProductCategoryDTO productCategoryDTO);

    void toggleProductCategoryStatus(Long id);
}
