package com.metoo.service.impl;

import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.merchant.MerchantRepository;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/12
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

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
        merchantRepository.delete(id);
    }
}
