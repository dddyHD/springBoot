package cn.suancloud.springBoot.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import cn.suancloud.springBoot.filter.JWTAuthenticationFilter;
import cn.suancloud.springBoot.filter.JWTLoginFilter;
import cn.suancloud.springBoot.security.CustomUserService;
import cn.suancloud.springBoot.security.MyFilterSecurityInterceptor;
import cn.suancloud.springBoot.security.securityHandle.DefaultAccessDeniedHandler;


/**
 * Created by admin on 2018/4/27.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;
  @Autowired
  private DefaultAccessDeniedHandler accessDeniedHandler;
  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;
  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;
  @Autowired
  private LogoutSuccessHandler logoutSuccessHandler;
  @Autowired
  private CustomUserService customUserService;

//  @Bean
//  UserDetailsService customUserService() { //注册UserDetailsService 的bean
//    return customUserService;
//  }
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserService).passwordEncoder(new BCryptPasswordEncoder()); //user Details Service验证
  }
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
            .disable()
            .authorizeRequests()
            .anyRequest().authenticated() //任何请求,登录后可以访问
            .and()
            .formLogin()
            .failureHandler(authenticationFailureHandler)
            .successHandler(authenticationSuccessHandler)
            .loginPage("/login")
            .permitAll() //登录页面用户任意访问
            .and()
            .logout()
            .logoutSuccessHandler(logoutSuccessHandler)
            .logoutUrl("/logout")
            .permitAll() //注销行为任意访问
            .and()
            .addFilter(new JWTLoginFilter(authenticationManager()))
            .addFilter(new JWTAuthenticationFilter(authenticationManager(),customUserService));



    http.exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler)
            .authenticationEntryPoint(authenticationEntryPoint);
    http.sessionManagement()
            .disable();
    http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
            .csrf()
            .disable();
  }

}
