package com.metoo.service;

import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.merchant.MerchantUserDTO;
import com.metoo.dto.product.ProductCategoryDTO;
import com.metoo.dto.product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/12
 */
public interface MerchantService {
    List<MerchantDTO> loadAll();

    MerchantDTO loadById(Long id);

    void saveOrUpdateMerchant(MerchantDTO merchantDTO);

    void toggleMerchantStatus(Long id);

    List<MerchantDTO> loadMerchantSaleRanking(int top);

    Page<MerchantDTO> loadMerchantByPage(MerchantBusinessType businessType, Pageable page);

    MerchantDTO loadByManagerId(Long managerId);

    String handlePictureUpload(MultipartFile picture) throws Exception;

    List<MerchantUserDTO> loadByMerchantId(Long merchantId);

    void toggleMerchantUserStatus(Long id);
}
