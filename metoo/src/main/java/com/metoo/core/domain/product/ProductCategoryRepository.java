package com.metoo.core.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/14
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("from ProductCategory c where c.merchant.id=:merchantId")
    List<ProductCategory> findByMerchantId(@Param("merchantId") Long merchantId);
}
