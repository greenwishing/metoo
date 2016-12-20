package com.metoo.core.domain.merchant;

import com.metoo.dto.merchant.MerchantDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface MerchantRepository extends JpaRepository<Merchant, Long>, JpaSpecificationExecutor<Merchant> {

    MerchantDTO findMerchantByManagerId(Long managerId);
}
