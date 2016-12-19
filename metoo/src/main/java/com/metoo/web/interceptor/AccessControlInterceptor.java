package com.metoo.web.interceptor;

import com.metoo.core.domain.user.UserType;
import com.metoo.dto.user.UserDTO;
import com.metoo.web.filter.LoginFilter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(LoginFilter.METOO_USER_SESSION_KEY);
        if (requestURI.startsWith("/admin/")) {
            if (user == null || UserType.ADMINISTRATOR != user.getType()) {
                return accessDenied(response);
            }
        } else if (requestURI.startsWith("/merchant/")) {
            if (user == null || UserType.MERCHANT_MANAGER != user.getType()) {
                return accessDenied(response);
            }
        }
        return true;
    }

    private boolean accessDenied(HttpServletResponse response) throws IOException {
        if (failureUrl == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.sendRedirect(failureUrl);
        }
        return false;
    }
}
