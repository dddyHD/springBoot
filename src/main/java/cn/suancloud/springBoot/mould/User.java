package cn.suancloud.springBoot.mould;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by admin on 2018/4/16.
 */
@Entity
@Table(name="user")
public class User implements Serializable {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "username", nullable = false, unique = true)
  private String username;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "nickname")
  private String nickname;
  @Column(name = "register_time", nullable = false)
  private Date registerTime= new Date();

  public User() {
  }

  public User(String username, String password, String email, String nickname, Date registerTime) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.nickname = nickname;
    this.registerTime = registerTime;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Date getRegisterTime() {
    return registerTime;
  }

  public void setRegisterTime(Date registerTime) {
    this.registerTime = registerTime;
  }
}
