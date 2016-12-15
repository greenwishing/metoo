package com.metoo.service.impl;

import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.merchant.MerchantRepository;
import com.metoo.core.domain.product.*;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.product.ProductCategoryDTO;
import com.metoo.dto.product.ProductDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.exception.MetooFormException;
import com.metoo.service.MerchantService;
import com.metoo.utils.JodaUtils;
import com.metoo.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/12
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private ProductRepository<Product> productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<MerchantDTO> loadAll() {
        List<Merchant> merchants = merchantRepository.findAll();
        return MerchantDTO.toDTOs(merchants);
    }

    @Override
    public MerchantDTO loadById(Long id) {
        Merchant merchant = merchantRepository.findOne(id);
        return new MerchantDTO(merchant);
    }

    @Override
    public void saveOrUpdateMerchant(MerchantDTO merchantDTO) {
        Long id = merchantDTO.getId();
        Merchant merchant;
        if (id != null) {
            merchant = merchantRepository.findOne(id);
        } else {
            merchant = new Merchant();
        }
        merchantDTO.update(merchant);
        merchantRepository.save(merchant);
    }

    @Override
    public void removeMerchantById(Long id) {
        Long merchantInUse = merchantRepository.checkMerchantInUse(id);
        if (merchantInUse > 0) {
            throw new MetooFormException(ErrorMap.ALREADY_IN_USE_MERCHANT);
        }
        merchantRepository.delete(id);
    }

    @Override
    public List<ProductDTO> loadMerchantProducts(Long merchantId, Class productClass) {
        List<Product> products = merchantRepository.loadMerchantProducts(merchantId, productClass.getSimpleName());
        return ProductDTO.toDTOs(products);
    }

    @Override
    public List<ProductCategoryDTO> loadProductCategories(Long merchantId) {
        List<ProductCategory> categories = productCategoryRepository.findByMerchantId(merchantId);
        return ProductCategoryDTO.toDTOs(categories);
    }

    @Override
    public void saveOrUpdateProduct(ProductDTO productDTO) {
        Long id = productDTO.getId();
        Product product;
        Long categoryId = productDTO.getCategory().getId();
        if (categoryId == null) {
            throw new MetooFormException(ErrorMap.INVALID_PRODUCT_CATEGORY_ID);
        }
        String priceStr = productDTO.getPrice();
        if (!NumberUtils.isPositiveBigDecimal(priceStr)) {
            throw new MetooFormException(ErrorMap.INVALID_PRICE);
        }
        String marketingPriceStr = productDTO.getMarketingPrice();
        if (!NumberUtils.isPositiveBigDecimal(marketingPriceStr)) {
            throw new MetooFormException(ErrorMap.INVALID_MARKETING_PRICE);
        }
        if (id != null) {
            product = productRepository.findOne(id);
        } else {
            Long merchantId = productDTO.getMerchant().getId();
            if (merchantId == null) {
                throw new MetooFormException(ErrorMap.INVALID_MERCHANT_ID);
            }
            Merchant merchant = merchantRepository.findOne(merchantId);
            switch (merchant.getBusinessType()) {
                case FOOD:
                    product = new Food(merchant);
                    break;
                case HOTEL:
                    product = new Hotel(merchant);
                    break;
                case SCENERY:
                    product = new Scenery(merchant);
                    break;
                default:
                    throw new MetooFormException(ErrorMap.INVALID_BUSINESS_TYPE);
            }
        }
        ProductCategory category = productCategoryRepository.findOne(categoryId);
        product.updateCategory(category);
        BigDecimal price = new BigDecimal(priceStr);
        BigDecimal marketingPrice = new BigDecimal(marketingPriceStr);
        product.update(productDTO.getName(), productDTO.getDescription(), price, marketingPrice);
        if (product instanceof Food) {
            String expiryDate = productDTO.getExpiryDate();
            ((Food) product).update(JodaUtils.parseLocalDate(expiryDate), productDTO.getNotices(), productDTO.getArticle());
        } else if (product instanceof Hotel) {
            ((Hotel) product).update(productDTO.isHasBreakfast(), productDTO.isHasWindow());
        }
        productRepository.save(product);
    }

    @Override
    public ProductDTO loadProductById(Long id) {
        Product product = productRepository.findOne(id);
        return new ProductDTO(product);
    }

    @Override
    public void removeProductById(Long id) {
        productRepository.delete(id);
    }

    @Override
    public ProductCategoryDTO loadProductCategoryById(Long id) {
        ProductCategory category = productCategoryRepository.findOne(id);
        return new ProductCategoryDTO(category);
    }

    @Override
    public void saveOrUpdateProductCategory(ProductCategoryDTO productCategoryDTO) {
        Long id = productCategoryDTO.getId();
        ProductCategory category;
        if (id != null) {
            category = productCategoryRepository.findOne(id);
        } else {
            Long merchantId = productCategoryDTO.getMerchant().getId();
            Merchant merchant = merchantRepository.findOne(merchantId);
            category = new ProductCategory(merchant);
        }
        category.update(productCategoryDTO.getName(), productCategoryDTO.getDescription());
        productCategoryRepository.save(category);
    }

    @Override
    public void removeProductCategoryById(Long id) {
        Long productInUse = productRepository.checkProductCategoryInUse(id);
        if (productInUse > 0) {
            throw new MetooFormException(ErrorMap.ALREADY_IN_USE_PRODUCT_CATEGORY);
        }
        productCategoryRepository.delete(id);
    }
}
