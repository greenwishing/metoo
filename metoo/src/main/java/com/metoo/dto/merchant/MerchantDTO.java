package com.metoo.dto.merchant;

import com.metoo.core.domain.merchant.Feature;
import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.dto.StatefulDTO;
import com.metoo.dto.user.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class MerchantDTO extends StatefulDTO {

    private String name;
    private MerchantBusinessType businessType;
    private Integer level;
    private MultipartFile picture;
    private String pictureKey;
    private String introduction;
    private String specialty;
    private List<Feature> features = new ArrayList<>();
    private String address;
    private String contactPhone;

    private UserDTO manager = new UserDTO();

    public MerchantDTO() {
    }

    public MerchantDTO(Merchant merchant) {
        super(merchant);
        this.manager = new UserDTO(merchant.getManager());
        this.name = merchant.getName();
        this.businessType = merchant.getBusinessType();
        this.level = merchant.getLevel();
        this.pictureKey = merchant.getPicture();
        this.introduction = merchant.getIntroduction();
        this.specialty = merchant.getSpecialty();
        this.features = Feature.listOf(merchant.getFeatures());
        this.address = merchant.getAddress();
        this.contactPhone = merchant.getContactPhone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MerchantBusinessType getBusinessType() {
        return businessType;
    }

    public String getLocation() {
        if (businessType == null) return null;
        return businessType.getValue().toLowerCase();
    }

    public void setBusinessType(MerchantBusinessType businessType) {
        this.businessType = businessType;
    }

    public Integer getLevel() {
        return level;
    }

    public String getFormattedLevel() {
        if (level == null || businessType == null) return null;
        String formattedLevel = null;
        if (level >= 3) {
            formattedLevel = "";
            for (int i = 1; i <= level; i++) {
                formattedLevel += businessType.getLevelChar();
            }
        }
        return formattedLevel;
    }

    public String getHotelType() {
        if (MerchantBusinessType.HOTEL != this.businessType) return null;
        if (level < 3) {
            return "经济型";
        }
        String starLevel = "";
        for (int i = 1; i <= level; i++) {
            starLevel += businessType.getLevelChar();
        }
        return starLevel + "星级";
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPictureKey() {
        return pictureKey;
    }

    public void setPictureKey(String pictureKey) {
        this.pictureKey = pictureKey;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public Integer getFeatureVal() {
        return Feature.countValue(features);
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public static List<MerchantDTO> toDTOs(List<Merchant> merchants) {
        List<MerchantDTO> merchantDTOs = new ArrayList<>();
        for (Merchant merchant : merchants) {
            MerchantDTO merchantDTO = new MerchantDTO(merchant);
            merchantDTOs.add(merchantDTO);
        }
        return merchantDTOs;
    }

    public void update(Merchant merchant) {
        Integer features = 0;
        for (Feature feature : this.features) {
            features += feature.getVal();
        }
        merchant.update(name, businessType, level, introduction, specialty, features, address, contactPhone);
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public String getMinConsumption() {
        return "100.00";
    }

    public String getTicketPrice() {
        return "80.00";
    }

    public UserDTO getManager() {
        return manager;
    }

    public void setManager(UserDTO manager) {
        this.manager = manager;
    }
}
