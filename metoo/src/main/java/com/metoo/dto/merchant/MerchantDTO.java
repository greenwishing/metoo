package com.metoo.dto.merchant;

import com.metoo.core.domain.merchant.Feature;
import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.merchant.MerchantBusinessType;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class MerchantDTO {

    private Long id;
    private String name;
    private MerchantBusinessType businessType;
    private Integer level;
    private String picture;
    private String introduction;
    private String specialty;
    private List<Feature> features = new ArrayList<>();
    private String address;
    private String contactPhone;

    public MerchantDTO() {
    }

    public MerchantDTO(Merchant merchant) {
        this.id = merchant.getId();
        this.name = merchant.getName();
        this.businessType = merchant.getBusinessType();
        this.level = merchant.getLevel();
        this.picture = merchant.getPicture();
        this.introduction = merchant.getIntroduction();
        this.specialty = merchant.getSpecialty();
        this.features = Feature.listOf(merchant.getFeatures());
        this.address = merchant.getAddress();
        this.contactPhone = merchant.getContactPhone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setBusinessType(MerchantBusinessType businessType) {
        this.businessType = businessType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
        merchant.update(name, businessType, level, picture, introduction, specialty, features, address, contactPhone);
    }
}
