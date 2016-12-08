package com.metoo.web.security;

import com.metoo.utils.MD5Utils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return MD5Utils.encode(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return true;
    }
}
