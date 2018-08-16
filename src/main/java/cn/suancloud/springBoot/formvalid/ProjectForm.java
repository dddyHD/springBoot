package cn.suancloud.springBoot.formvalid;

import javax.validation.constraints.NotNull;

public class ProjectForm {
  @NotNull(message = "id 不允许为空")
  private Long id;
  @NotNull(message = "项目不允许为空")
  private String project;
  @NotNull(message = "角色不允许为空")
  private String role;
  @NotNull(message = "类型不允许为空")
  private String kind;
  @NotNull(message = "用户名不允许为空")
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
