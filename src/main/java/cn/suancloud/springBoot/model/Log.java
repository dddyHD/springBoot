package cn.suancloud.springBoot.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

@Entity
@Table(name = "log")
public class Log implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "username")
  private String username;
  @Column(name = "url")
  private String url;
  @Column(name = "method")
  private String method;
  @Column(name = "describes")
  private String describes;
  @Column(name = "operation_time")
  private Date operationTime = new Date();

  public Log() {
  }

  public Log(String username, String url, String method, String describe) {
    this.username = username;
    this.url = url;
    this.method = method;
    this.describes = describe;
  }

  public Log(HttpServletRequest request, String describe) {
    this.username = request.getAttribute("current_user").toString();
    this.url = request.getRequestURI();
    this.method = request.getMethod();
    this.describes = describe;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public String getDescribe() {
    return describes;
  }

  public void setDescribe(String describe) {
    this.describes = describe;
  }

  public Date getOperationTime() {
    return operationTime;
  }

  public void setOperationTime(Date operationTime) {
    this.operationTime = operationTime;
  }

}
