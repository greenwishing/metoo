package com.metoo.core.domain.merchant;

/**
 * 与商户建立管理的会员
 * User: Zhang xiaomei
 * Date: 2016/12/20
 */

import com.metoo.core.domain.common.StatefulDomain;
import com.metoo.core.domain.user.User;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("MerchantUser")
public class MerchantUser extends StatefulDomain {

    /**
     * 商户
     */
    @JoinColumn(name ="merchant_id")
    @ManyToOne(targetEntity = Merchant.class)
    protected Merchant merchant;

    /**
     * 用户
     */
    @JoinColumn(name ="user_id")
    @ManyToOne(targetEntity = User.class)
    protected User user;

    /**
     * 入会时间
     */
    @Column(name = "creation_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected DateTime creationTime = new DateTime();

    public MerchantUser() {
    }

    public MerchantUser(Merchant merchant, User user) {
        this.merchant = merchant;
        this.user = user;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public User getUser() {
        return user;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }
}
