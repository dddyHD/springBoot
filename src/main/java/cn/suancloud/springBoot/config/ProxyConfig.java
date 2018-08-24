package cn.suancloud.springBoot.config;

import com.google.common.collect.ImmutableMap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import javax.servlet.Servlet;

import cn.suancloud.springBoot.util.proxy.ProxyServlet;

import static cn.suancloud.springBoot.util.Constant.OPENSHIFT_URL;

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
            "targetUri", OPENSHIFT_URL+"/oapi/v1",
            "log", "true"
    );
    registrationBean.setInitParameters(params);
    return registrationBean;
  }

}
