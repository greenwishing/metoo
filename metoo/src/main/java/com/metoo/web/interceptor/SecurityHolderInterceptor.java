package com.metoo.web.interceptor;

import com.metoo.dto.user.UserDTO;
import com.metoo.web.filter.LoginFilter;
import com.metoo.web.security.SecurityHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/19
 */
public class SecurityHolderInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(LoginFilter.METOO_USER_SESSION_KEY);
        SecurityHolder.set(userDTO);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityHolder.remove();
    }
}
