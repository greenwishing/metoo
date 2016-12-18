package com.metoo.core.domain.feedback;

import com.metoo.core.domain.common.StatefulDomain;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.core.domain.user.User;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * 反馈
 *
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@Table(name = "feedback")
public class Feedback extends StatefulDomain {

    /**
     * 用户，用户登录则有值，否则为空
     */
    @JoinColumn(name ="user_id")
    @ManyToOne(targetEntity = User.class)
    private User user;

    /**
     * 反馈时间
     */
    @Column(name = "creation_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime = new DateTime();

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    @Enumerated(EnumType.STRING)
    private MerchantBusinessType businessType;

    /**
     * 反馈内容
     */
    @Column(name = "description")
    private String description;

    /**
     * 姓名
     */
    @Column(name = "username")
    private String username;

    /**
     * 电话
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    public Feedback() {
    }

    public Feedback(User user, MerchantBusinessType businessType) {
        this.user = user;
        this.businessType = businessType;
    }

    public void update(String description, String username, String telephone, String email) {
        this.description = description;
        this.username = username;
        this.telephone = telephone;
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public MerchantBusinessType getBusinessType() {
        return businessType;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }
}
