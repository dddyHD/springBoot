package cn.suancloud.springBoot.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import cn.suancloud.springBoot.filter.JWTAuthenticationFilter;
import cn.suancloud.springBoot.filter.JWTLoginFilter;
import cn.suancloud.springBoot.security.CustomUserService;
import cn.suancloud.springBoot.security.MyFilterSecurityInterceptor;
import cn.suancloud.springBoot.security.MyPasswordEncoder;
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
    //user Details Service验证
    //auth.userDetailsService(customUserService).passwordEncoder(new BCryptPasswordEncoder());
    //自定义密码加密
    auth.userDetailsService(customUserService).passwordEncoder(new MyPasswordEncoder());
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
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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
