package com.metoo.web.security;

import com.metoo.core.domain.user.User;
import com.metoo.core.domain.user.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public class LoginUser implements UserDetails {

    private Long id;
    private String email;
    private UserType type = UserType.CUSTOMER;
    private String username;
    private String password;

    public LoginUser() {

    }

    public LoginUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.type = user.getType();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + type.getValue()));
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserType getType() {
        return type;
    }

    public String getRealUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
