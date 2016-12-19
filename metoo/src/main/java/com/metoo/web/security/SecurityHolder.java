package com.metoo.web.security;

import com.metoo.dto.user.UserDTO;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/19
 */
public class SecurityHolder {

    private static final ThreadLocal<UserDTO> instance = new ThreadLocal<>();

    public static UserDTO get() {
        return instance.get();
    }

    public static void set(UserDTO userDTO) {
        instance.set(userDTO);
    }

    public static void remove() {
        instance.remove();
    }
}
