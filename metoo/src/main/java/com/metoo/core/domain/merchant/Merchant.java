package com.metoo.core.domain.merchant;

import com.metoo.core.domain.common.StatefulDomain;

import javax.persistence.*;

/**
 * 商户
 *
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@Table(name = "merchant")
public class Merchant extends StatefulDomain {

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    @Enumerated(EnumType.STRING)
    private MerchantBusinessType businessType;

    /**
     * 等级，从 1 - 5 五个等级，如：五星级酒店，4A级景点等
     */
    @Column(name = "level")
    private Integer level;

    /**
     * 图片，相对地址
     */
    @Column(name = "picture")
    private String picture;

    /**
     * 介绍
     */
    @Column(name = "introduction")
    private String introduction;

    /**
     * 特色
     */
    @Column(name = "specialty")
    private String specialty;

    /**
     * 服务项
     * @see Feature#val
     */
    @Column(name = "features")
    private Integer features = 0;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 联系电话
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    public Merchant() {
    }

    public void update(String name, MerchantBusinessType businessType, Integer level, String picture, String introduction, String specialty, Integer features, String address, String contactPhone) {
        this.name = name;
        this.businessType = businessType;
        this.level = level;
        this.picture = picture;
        this.introduction = introduction;
        this.specialty = specialty;
        this.features = features;
        this.address = address;
        this.contactPhone = contactPhone;
    }

    public String getName() {
        return name;
    }

    public MerchantBusinessType getBusinessType() {
        return businessType;
    }

    public Integer getLevel() {
        return level;
    }

    public String getPicture() {
        return picture;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getSpecialty() {
        return specialty;
    }

    public Integer getFeatures() {
        return features;
    }

    public String getAddress() {
        return address;
    }

    public String getContactPhone() {
        return contactPhone;
    }
}
