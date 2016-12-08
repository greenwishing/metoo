package com.metoo.core.domain.merchant;

import com.metoo.core.domain.Domain;

import javax.persistence.*;

/**
 * 商户
 *
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@Table(name = "merchant")
public class Merchant extends Domain {

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
}
