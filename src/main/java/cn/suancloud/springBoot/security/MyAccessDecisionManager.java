package cn.suancloud.springBoot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2018/4/27.
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
  @Override
  public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
    Logger logger = LoggerFactory.getLogger(MyAccessDecisionManager.class);
    //decide 方法是判定是否拥有权限的决策方法
    HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
    String url, method;

    //不拦截
    if (matchers("/login", request) || matchers("/logout", request)) {
      return;
    }
    //权限检查(反向)
    for (GrantedAuthority ga : authentication.getAuthorities()) {
      if (ga instanceof MyGrantedAuthority) {
        MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) ga;
        url = urlGrantedAuthority.getUrl();
        method = urlGrantedAuthority.getMethod();
        if (urlGrantedAuthority.isExcept())//不予许访问的资源
          if (matchers(url, request))
            if (method.equals(request.getMethod()) || "ALL".equals(method))
              throw new AccessDeniedException("Permission denied !");
      }
    }
    //权限检查
    for (GrantedAuthority ga : authentication.getAuthorities()) {
      if (ga instanceof MyGrantedAuthority) {
        MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) ga;
        url = urlGrantedAuthority.getUrl();
        method = urlGrantedAuthority.getMethod();
        if (matchers(url, request))
          //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
          if (method.equals(request.getMethod()) || "ALL".equals(method))
            return;
      }
    }
    //权限不足无法访问
    logger.warn("权限不足 无法访问");
    throw new AccessDeniedException("Permission denied !");
  }

  @Override
  public boolean supports(ConfigAttribute configAttribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }

  private boolean matchers(String url, HttpServletRequest request) {
    AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
    if (matcher.matches(request)) {
      return true;
    }
    return false;
  }
}
