package cn.suancloud.springBoot.model.openshift;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata implements Serializable {
  private String name;
  private String selfLink;
  private String uid;
  private String resourceVersion;
  private String creationTimestamp;
  private Annotations annotations;
  public Metadata() {
  }

  //User
  public Metadata(String name, String selfLink, String uid, String resourceVersion, String creationTimestamp) {
    this.name = name;
    this.selfLink = selfLink;
    this.uid = uid;
    this.resourceVersion = resourceVersion;
    this.creationTimestamp = creationTimestamp;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSelfLink() {
    return selfLink;
  }

  public void setSelfLink(String selfLink) {
    this.selfLink = selfLink;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getResourceVersion() {
    return resourceVersion;
  }

  public void setResourceVersion(String resourceVersion) {
    this.resourceVersion = resourceVersion;
  }

  public String getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(String creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public Annotations getAnnotations() {
    return annotations;
  }

  public void setAnnotations(Annotations annotations) {
    this.annotations = annotations;
  }
}
