package cn.suancloud.springBoot.security;

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
    //decide 方法是判定是否拥有权限的决策方法
    HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
    String url,method;
    AntPathRequestMatcher matcher;

    for (GrantedAuthority ga : authentication.getAuthorities()) {
      if (ga instanceof MyGrantedAuthority) {
        MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) ga;
        url = urlGrantedAuthority.getUrl();
        method = urlGrantedAuthority.getMethod();
        matcher = new AntPathRequestMatcher(url);
        if (matcher.matches(request)) {
          //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
          if (method.equals(request.getMethod()) || "ALL".equals(method)) {
            return;
          }
        }
      } else if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {//未登录只允许访问 login 页面
        matcher = new AntPathRequestMatcher("/login");
        if (matcher.matches(request)) {
          return;
        }
      }
    }

  }

  @Override
  public boolean supports(ConfigAttribute configAttribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}
