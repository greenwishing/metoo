package com.metoo.web.controller.interceptor;

import com.metoo.dto.UserDTO;
import com.metoo.web.filter.LoginFilter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 访问控制
 *
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
public class AccessControlInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/admin/")) {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute(LoginFilter.METOO_USER_SESSION_KEY);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        return true;
    }
}
