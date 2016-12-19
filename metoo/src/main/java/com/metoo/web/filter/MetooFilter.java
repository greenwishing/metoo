package com.metoo.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
public abstract class MetooFilter implements Filter {

    public static final String METOO_USER_SESSION_KEY = "METOO_USER";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!requires(request, response)) {
            chain.doFilter(request, response);
            return;
        }

        processes(request, response, chain);

    }

    protected abstract void processes(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;

    protected boolean requires(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        return uri.startsWith(request.getContextPath() + getProcessesUrl());
    }

    protected abstract String getProcessesUrl();

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("init filter: " + this.getClass().getName());
    }

    @Override
    public void destroy() {
    }
}
