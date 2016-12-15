package com.metoo.core.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface ProductRepository <T extends Product> extends JpaRepository<Product, Long> {

    @Query("select count(*) from Product p where p.category.id=:categoryId")
    Long checkProductCategoryInUse(@Param("categoryId") Long categoryId);
}
