package cn.suancloud.springBoot.config;

import com.google.common.collect.ImmutableMap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import javax.servlet.Servlet;

import cn.suancloud.springBoot.util.proxy.ProxyServlet;

@Configuration
public class ProxyConfig {

  @Bean
  public Servlet openShiftServlet() {
    return new ProxyServlet();
  }

  @Bean
  public ServletRegistrationBean proxyServletRegistration() {
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(
            openShiftServlet(),
            "/oapi/v1/*");
    Map<String, String> params = ImmutableMap.of(
            "targetUri", "https://112.74.27.228:8443/oapi/v1",
            "log", "true"
    );
    registrationBean.setInitParameters(params);
    return registrationBean;
  }

}
