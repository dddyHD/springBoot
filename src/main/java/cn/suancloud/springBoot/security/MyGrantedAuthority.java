package cn.suancloud.springBoot.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by admin on 2018/4/27.
 */
public class MyGrantedAuthority implements GrantedAuthority {

  private String url;
  private String method;
  private boolean except;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public boolean isExcept() {
    return except;
  }

  public void setExcept(boolean except) {
    this.except = except;
  }

  public MyGrantedAuthority(String url, String method, boolean except) {
    this.url = url;
    this.method = method;
    this.except = except;
  }

  @Override
  public String getAuthority() {
    return this.url + ";" + this.method + ";" + this.except;
  }
}
