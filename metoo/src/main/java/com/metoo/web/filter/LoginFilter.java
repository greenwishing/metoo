package com.metoo.web.filter;

import com.metoo.cache.SessionCodeHolder;
import com.metoo.core.domain.user.UserType;
import com.metoo.dto.UserDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.exception.MetooLoginException;
import com.metoo.service.UserService;
import com.metoo.utils.MD5Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
public abstract class LoginFilter extends MetooFilter {

    protected UserService userService;

    @Override
    protected void processes(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (!request.getMethod().equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        UserDTO user = null;
        try {
            user = login(request, response);
            if (user == null) {
                return;
            }
        } catch (MetooLoginException failed) {
            loginFailure(request, response, failed);
            return;
        }

        loginSuccess(request, response, chain, user);
    }


    protected UserDTO login(HttpServletRequest request, HttpServletResponse response) {
        String email = ServletRequestUtils.getStringParameter(request, "email", "");
        String password = ServletRequestUtils.getStringParameter(request, "password", "");
        String code = ServletRequestUtils.getStringParameter(request, "code", "");
        UserDTO user = userService.loadUserByEmail(email);
        if (user == null) {
            throw new MetooLoginException(ErrorMap.EMAIL_NOT_FOUND);
        }
        String cachedCode = SessionCodeHolder.get(email);
        if (!code.equalsIgnoreCase(cachedCode)) {
            throw new MetooLoginException(ErrorMap.INVALID_CODE);
        }
        SessionCodeHolder.remove(email);
        String md5Password = MD5Utils.encode(password);
        if (!md5Password.equals(user.getPassword())) {
            throw new MetooLoginException(ErrorMap.INVALID_PASSWORD);
        }
        UserType type = user.getType();
        if (!isUserType(type)) {
            throw new MetooLoginException(ErrorMap.INVALID_USER_TYPE);
        }
        return user;
    }

    protected void loginSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, UserDTO user) throws IOException, ServletException {
        onLoginSuccess(request, response, user);
        chain.doFilter(request, response);
    }

    protected void loginFailure(HttpServletRequest request, HttpServletResponse response, MetooLoginException failed) throws IOException, ServletException {
        if (failureUrl == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "登录失败：" + failed.getMessage());
        } else {
            response.sendRedirect(failureUrl + "?code=" + failed.getError().getCode());
        }
    }

    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, UserDTO user) throws IOException {
        request.getSession().setAttribute(METOO_USER_SESSION_KEY, user);
        response.sendRedirect(successUrl);
    }

    protected abstract boolean isUserType(UserType userType);

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
