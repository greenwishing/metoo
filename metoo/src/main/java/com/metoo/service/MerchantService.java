package com.metoo.service;

import com.metoo.dto.merchant.MerchantDTO;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/12
 */
public interface MerchantService {
    List<MerchantDTO> loadAll();

    MerchantDTO loadById(Long id);

    void saveOrUpdateMerchant(MerchantDTO merchantDTO);

    void removeMerchantById(Long id);
}
