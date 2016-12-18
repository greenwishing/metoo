package com.metoo.service;

import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.product.ProductCategoryDTO;
import com.metoo.dto.product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/12
 */
public interface MerchantService {
    List<MerchantDTO> loadAll();

    MerchantDTO loadById(Long id);

    void saveOrUpdateMerchant(MerchantDTO merchantDTO);

    void toggleMerchantStatus(Long id);

    List<ProductDTO> loadMerchantProducts(Long merchantId);

    List<ProductCategoryDTO> loadProductCategories(Long merchantId);

    void saveOrUpdateProduct(ProductDTO productDTO);

    ProductDTO loadProductById(Long id);

    void toggleProductStatus(Long id);

    ProductCategoryDTO loadProductCategoryById(Long id);

    void saveOrUpdateProductCategory(ProductCategoryDTO productCategoryDTO);

    void toggleProductCategoryStatus(Long id);

    List<MerchantDTO> loadMerchantSaleRanking(int top);

    Page<MerchantDTO> loadMerchantByPage(MerchantBusinessType businessType, Pageable page);
}
