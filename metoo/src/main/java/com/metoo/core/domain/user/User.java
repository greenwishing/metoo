package com.metoo.core.domain.user;

import com.metoo.core.domain.Domain;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * 用户
 *
 * User: Zhang xiaomei
 * Date: 2016/11/23
 */
@Entity
@Table(name = "user")
public class User extends Domain {

    /**
     * 邮箱，唯一，不可为空
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * 用户类型
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserType type = UserType.CUSTOMER;

    /**
     * 创建时间
     */
    @Column(name = "creation_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime = new DateTime();


    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码，使用 MD5 算法加密
     */
    @Column(name = "password")
    private String password;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
