package cn.suancloud.springBoot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Created by admin on 2018/4/26.
 */
@Entity
@Table(name = "permission")
public class Permission {
  @Id
  @GeneratedValue
  private Long id;
  @Column(name="permission_name",unique = true)
  private String permission_name;
  @Column(name = "url")
  private String url;
  @Column(name = "method")
  private String method;
  @Column(name = "remark")
  private String remark;
  @ManyToMany(mappedBy = "permissions")
  private List<Role> roles = new ArrayList<>();

  public Permission() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPermission_name() {
    return permission_name;
  }

  public void setPermission_name(String permission_name) {
    this.permission_name = permission_name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
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

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
}
