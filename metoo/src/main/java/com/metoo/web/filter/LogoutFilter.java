package com.metoo.web.filter;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
@WebFilter(filterName = "logoutFilter", urlPatterns = "/*")
public class LogoutFilter extends MetooFilter {

    @Override
    protected void processes(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!requires(request, response)) {
            chain.doFilter(request, response);
            return;
        }
        request.getSession().removeAttribute(METOO_USER_SESSION_KEY);
        response.sendRedirect(successUrl);
    }

    @Override
    @Value("${metoo.logout.url}")
    public void setProcessesUrl(String processesUrl) {
        this.processesUrl = processesUrl;
    }

    @Override
    @Value("${metoo.logout.success.url}")
    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

}
