package cn.suancloud.springBoot.config;

import org.apache.catalina.filters.RemoteIpFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2018/4/16.
 * 实现Filter接口，实现Filter方法
 * 添加@Configuration 注解，将自定义Filter加入过滤链
 */
@Configuration
public class WebConfiguration {
  protected static Logger logger = LoggerFactory.getLogger(WebConfiguration.class);

  @Bean
  public RemoteIpFilter remoteIpFilter() {
    return new RemoteIpFilter();
  }

  @Bean
  public FilterRegistrationBean testFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new MyFilter());
    registration.addUrlPatterns("/*");
    registration.addInitParameter("paramName", "paramValue");
    registration.setName("MyFilter");
    registration.setOrder(1);
    return registration;
  }

  public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      // TODO Auto-generated method stub
      HttpServletRequest request = (HttpServletRequest) servletRequest;
      logger.info("this is MyFilter,url :" + request.getRequestURI());
      filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
      // TODO Auto-generated method stub
    }
  }


}
