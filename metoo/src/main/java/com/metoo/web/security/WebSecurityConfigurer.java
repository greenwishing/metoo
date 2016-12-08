package com.metoo.web.security;

import com.metoo.web.handler.MetooAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    private AuthenticationSuccessHandler successHandler = new MetooAuthenticationSuccessHandler();
    private PasswordEncoder passwordEncoder = new MD5PasswordEncoder();

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置不拦截规则
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/*").permitAll()
                .antMatchers("/admin/**").hasRole("ADMINISTRATOR")
                .anyRequest()
                .fullyAuthenticated()

                .and().formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureForwardUrl("/login?action=3")
                .permitAll()

                .and().logout()
                .logoutSuccessUrl("/login?action=1")
                .permitAll()

                /*.and().sessionManagement()
                .invalidSessionUrl("/login?action=2")*/
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
