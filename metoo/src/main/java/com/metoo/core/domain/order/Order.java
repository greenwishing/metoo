package com.metoo.core.domain.order;

import com.metoo.core.domain.Domain;
import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.product.Product;
import com.metoo.core.domain.user.User;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * 订单
 *
 * 订单编号使用自增ID  2016/12/11
 *
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@Table(name = "`order`")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 单表记录继承结构
@DiscriminatorColumn(name = "type") // 鉴别器字段
public abstract class Order extends Domain {

    /**
     * 订单所属商户
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
     * 购买的商品
     */
    @JoinColumn(name ="product_id")
    @ManyToOne(targetEntity = Product.class)
    protected Product product;

    /**
     * 订单状态
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    /**
     * 预订日期
     */
    @Column(name = "booking_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    protected LocalDate bookingDate;

    /**
     * 下单时间
     */
    @Column(name = "creation_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected DateTime creationTime = new DateTime();

    /**
     * 取票人
     */
    @Column(name = "username")
    protected String username;

    /**
     * 预留电话
     */
    @Column(name = "telephone")
    protected String telephone;

    public Order() {
    }

    public Order(Merchant merchant, User user, Product product) {
        this.merchant = merchant;
        this.user = user;
        this.product = product;
    }

    public void update(LocalDate bookingDate, String username, String telephone) {
        this.bookingDate = bookingDate;
        this.username = username;
        this.telephone = telephone;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public String getUsername() {
        return username;
    }

    public String getTelephone() {
        return telephone;
    }
}
