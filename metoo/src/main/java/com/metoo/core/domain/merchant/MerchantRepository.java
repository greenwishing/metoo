package com.metoo.core.domain.merchant;

import com.metoo.core.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @Query("from Product p where p.merchant.id=:merchantId and p.class=:productType")
    List<Product> loadMerchantProducts(@Param("merchantId") Long merchantId, @Param("productType") String productType);

    @Query("select count(*) from ProductCategory c where c.merchant.id=:merchantId")
    Long checkMerchantInUse(@Param("merchantId") Long merchantId);
}
