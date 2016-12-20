package com.metoo.service.impl;

import com.metoo.core.MetooSystem;
import com.metoo.core.domain.common.DomainUtils;
import com.metoo.core.domain.common.Status;
import com.metoo.core.domain.merchant.*;
import com.metoo.core.domain.user.User;
import com.metoo.core.domain.user.UserRepository;
import com.metoo.core.domain.user.UserType;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.merchant.MerchantUserDTO;
import com.metoo.dto.user.UserDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.exception.MetooException;
import com.metoo.exception.MetooFormException;
import com.metoo.service.MerchantService;
import com.metoo.utils.FileuploadUtils;
import com.metoo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/12
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private MerchantUserRepository merchantUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MetooSystem metooSystem;

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
            UserDTO managerDTO = merchantDTO.getManager();
            String email = managerDTO.getEmail();
            User exists = userRepository.findByEmail(email);
            if (exists != null) {
                throw new MetooFormException(ErrorMap.ALREADY_EXISTS_EMAIL);
            }
            User manager = new User(email);
            manager.setType(UserType.MERCHANT_MANAGER);
            manager.setUsername(managerDTO.getUsername());
            manager.setPassword(MD5Utils.encode(managerDTO.getPassword()));
            userRepository.save(manager);
            merchant = new Merchant(manager);
        }
        merchantDTO.update(merchant);
        String pictureKey = merchantDTO.getPictureKey();
        if (StringUtils.hasText(pictureKey)) {
            merchant.updatePicture(pictureKey);
        }
        merchantRepository.save(merchant);
    }

    @Override
    public void toggleMerchantStatus(Long id) {
        DomainUtils.toggleStatus(merchantRepository, id);
    }

    @Override
    public List<MerchantDTO> loadTop5Merchants() {
        List<Merchant> merchants = merchantRepository.findTop4ByStatusOrderBySalesVolumeDescIdDesc(Status.ACTIVATED);
        return MerchantDTO.toDTOs(merchants);
    }

    @Override
    public List<MerchantDTO> loadMerchantByBusinessType(MerchantBusinessType businessType) {
        List<Merchant> merchants = merchantRepository.findByBusinessTypeAndStatus(businessType, Status.ACTIVATED);
        return MerchantDTO.toDTOs(merchants);
    }

    @Override
    public MerchantDTO loadByManagerId(Long managerId) {
        return merchantRepository.findMerchantByManagerId(managerId);
    }

    @Override
    public String handlePictureUpload(MultipartFile picture) throws Exception {
        long size = picture.getSize();
        if (size > 512 * 1024) {
            throw new MetooException(ErrorMap.INVALID_PICTURE_SIZE);
        }
        return FileuploadUtils.storePicture(picture, metooSystem);
    }

    @Override
    public List<MerchantUserDTO> loadByMerchantId(Long merchantId) {
        List<MerchantUser> merchantUsers = merchantUserRepository.findByMerchantId(merchantId);
        return MerchantUserDTO.toDTOs(merchantUsers);
    }

    @Override
    public void toggleMerchantUserStatus(Long id) {
        DomainUtils.toggleStatus(merchantUserRepository, id);
    }
}
