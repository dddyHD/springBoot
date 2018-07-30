package cn.suancloud.springBoot.formvalid;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cn.suancloud.springBoot.util.validutil.HttpMethodContain;


public class PermissionForm {
  @NotNull
  @Size(min=3,max=50,message = "权限名长度为3~50")
  private String permission_name;
  @NotNull
  private String url;
  @HttpMethodContain(message = "方法类型错误")
  private String method;
  @NotNull
  private boolean except;
  private String remark;

  public String getPermission_name() {
    return permission_name;
  }

  public void setPermission_name(String permission_name) {
    this.permission_name = permission_name;
  }

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

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
