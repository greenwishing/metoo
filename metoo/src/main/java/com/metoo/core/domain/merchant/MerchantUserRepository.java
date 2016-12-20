package com.metoo.core.domain.merchant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/20
 */
public interface MerchantUserRepository extends JpaRepository<MerchantUser, Long> {

    List<MerchantUser> findByMerchantId(Long merchantId);

    MerchantUser findByMerchantIdAndUserId(Long merchantId, Long userId);
}
