package com.metoo.core.domain.merchant;

import com.metoo.core.domain.common.Status;
import com.metoo.dto.merchant.MerchantDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface MerchantRepository extends JpaRepository<Merchant, Long>, JpaSpecificationExecutor<Merchant> {

    MerchantDTO findMerchantByManagerId(Long managerId);

    List<Merchant> findByBusinessTypeAndStatus(MerchantBusinessType businessType, Status status);

    List<Merchant> findTop4ByStatusOrderBySalesVolumeDescIdDesc(Status status);
}
