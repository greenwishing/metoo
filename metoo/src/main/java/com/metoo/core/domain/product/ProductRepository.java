package com.metoo.core.domain.product;

import com.metoo.core.domain.common.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface ProductRepository <T extends Product> extends JpaRepository<T, Long> {

    List<Product> findByMerchantIdAndStatus(Long merchantId, Status status);
}
