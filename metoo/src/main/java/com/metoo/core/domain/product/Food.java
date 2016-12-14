package com.metoo.core.domain.product;

import com.metoo.core.domain.merchant.Merchant;
import com.metoo.utils.JsonUtils;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@DiscriminatorValue("Food")
public class Food extends Product {

    /**
     * 有效期
     */
    @Column(name = "expiry_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate expiryDate;

    /**
     * 购买须知，JSON 数组
     * [
     * {title: "标题", content: "内容"},
     * {title: "标题", content: "内容"},
     * ...
     * ]
     */
    @Column(name = "notices")
    private String notices = "[]";

    /**
     * 图文介绍
     */
    @Column(name = "article")
    private String article;

    public Food() {
        super();
    }

    public Food(Merchant merchant) {
        super(merchant);
    }

    public void update(LocalDate expiryDate, List<ProductNotice> notices, String article) {
        this.expiryDate = expiryDate;
        this.notices = JsonUtils.toJsonString(notices);
        this.article = article;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getNotices() {
        return notices;
    }

    public String getArticle() {
        return article;
    }
}
