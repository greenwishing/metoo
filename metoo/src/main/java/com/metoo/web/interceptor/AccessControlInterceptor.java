package com.metoo.web.interceptor;

import com.metoo.dto.user.UserDTO;
import com.metoo.web.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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

    private String failureUrl;

    public AccessControlInterceptor() {
    }

    public AccessControlInterceptor(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/admin/")) {
            if (requestURI.startsWith("/admin/login")) {
                return true;
            }
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute(LoginFilter.METOO_USER_SESSION_KEY);
            if (user == null) {
                if (failureUrl == null) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                } else {
                    response.sendRedirect(failureUrl);
                }
                return false;
            }
        }
        return true;
    }
}
