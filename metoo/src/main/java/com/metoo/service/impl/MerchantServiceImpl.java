package com.metoo.service.impl;

import com.metoo.core.MetooSystem;
import com.metoo.core.domain.common.DomainUtils;
import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.core.domain.merchant.MerchantRepository;
import com.metoo.core.domain.product.Product;
import com.metoo.core.domain.product.ProductCategoryRepository;
import com.metoo.core.domain.product.ProductRepository;
import com.metoo.core.domain.user.User;
import com.metoo.core.domain.user.UserRepository;
import com.metoo.core.domain.user.UserType;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.user.UserDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.exception.MetooException;
import com.metoo.service.MerchantService;
import com.metoo.utils.FileuploadUtils;
import com.metoo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
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
    private ProductRepository<Product> productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
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
        MultipartFile picture = merchantDTO.getPicture();
        String pictureKey = null;
        if (picture != null && picture.getSize() > 0) {
            long size = picture.getSize();
            if (size > 512 * 1024) {
                throw new MetooException(ErrorMap.INVALID_PICTURE_SIZE);
            }
            try {
                pictureKey = FileuploadUtils.storePicture(merchantDTO.getPicture(), metooSystem);
                merchantDTO.setPictureKey(pictureKey);
            } catch (Exception e) {
                throw new MetooException(ErrorMap.INVALID_PICTURE);
            }
        }
        Long id = merchantDTO.getId();
        Merchant merchant;
        if (id != null) {
            merchant = merchantRepository.findOne(id);
        } else {
            UserDTO managerDTO = merchantDTO.getManager();
            User manager = new User(managerDTO.getEmail());
            manager.setType(UserType.MERCHANT_MANAGER);
            manager.setUsername(managerDTO.getUsername());
            manager.setPassword(MD5Utils.encode(managerDTO.getPassword()));
            userRepository.save(manager);
            merchant = new Merchant(manager);
        }
        merchantDTO.update(merchant);
        if (pictureKey != null) {
            merchant.updatePicture(pictureKey);
        }
        merchantRepository.save(merchant);
    }

    @Override
    public void toggleMerchantStatus(Long id) {
        DomainUtils.toggleStatus(merchantRepository, id);
    }

    @Override
    public List<MerchantDTO> loadMerchantSaleRanking(int top) {
        Sort.Order salesVolumeDesc = new Sort.Order(Sort.Direction.DESC, "salesVolume");
        Sort.Order idDesc = new Sort.Order(Sort.Direction.DESC, "id");
        PageRequest pageRequest = new PageRequest(0, top, new Sort(salesVolumeDesc, idDesc));
        Page<Merchant> page = merchantRepository.findAll(pageRequest);
        List<Merchant> content = page.getContent();
        return MerchantDTO.toDTOs(content);
    }

    @Override
    public Page<MerchantDTO> loadMerchantByPage(MerchantBusinessType businessType, Pageable page) {
        Page<Merchant> pageResult = merchantRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.where(criteriaBuilder.equal(root.get("businessType"), businessType));
            return null;
        }, page);
        List<MerchantDTO> merchantDTOs = MerchantDTO.toDTOs(pageResult.getContent());
        return new PageImpl<>(merchantDTOs);
    }

    @Override
    public MerchantDTO loadByManagerId(Long managerId) {
        return merchantRepository.findMerchantByManagerId(managerId);
    }
}
