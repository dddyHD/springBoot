package cn.suancloud.springBoot.formvalid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cn.suancloud.springBoot.model.Permission;

public class RoleForm implements Serializable {
  @NotNull(message = "角色户名不允许为空")
  @Size(min = 3, max = 20, message = "用户名长度为3~20")
  private String role_name;
  private List<Permission> permissions = new ArrayList<>();

  public String getRole_name() {
    return role_name;
  }

  public void setRole_name(String role_name) {
    this.role_name = role_name;
  }

  public List<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Permission> permissions) {
    this.permissions = permissions;
  }
}
