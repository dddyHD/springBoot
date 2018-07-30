package cn.suancloud.springBoot.model.openshift;

import java.io.Serializable;

public class OSUser implements Serializable {
  private String kind;
  private String apiVersion;
  private Metadata metadata = new Metadata();
  private String[] identities;
  private String[] groups;
  private String fullName;
  private String password;
  //java role  超级管理员;admin;政务办管理员:zw_admin;普通用户user
  private String role;

  public OSUser() {
  }

  public OSUser(String kind, String apiVersion, Metadata metadata, String[] identities, String[]
          groups, String fullName, String password, String role) {
    this.kind = kind;
    this.apiVersion = apiVersion;
    this.metadata = metadata;
    this.identities = identities;
    this.groups = groups;
    this.fullName = fullName;
    this.password = password;
    this.role = role;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  public String[] getIdentities() {
    return identities;
  }

  public void setIdentities(String[] identities) {
    this.identities = identities;
  }

  public String[] getGroups() {
    return groups;
  }

  public void setGroups(String[] groups) {
    this.groups = groups;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
