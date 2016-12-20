package com.metoo.core.domain.product;

import com.metoo.core.domain.common.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/14
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findByMerchantIdAndStatus(Long merchantId, Status activated);
}
