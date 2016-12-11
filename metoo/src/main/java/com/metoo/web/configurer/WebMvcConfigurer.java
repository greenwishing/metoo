package com.metoo.web.configurer;

import com.metoo.web.interceptor.AccessControlInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Value("${metoo.administrator.login.failure.url}")
    private String loginFailureUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessControlInterceptor(loginFailureUrl));
    }
}
