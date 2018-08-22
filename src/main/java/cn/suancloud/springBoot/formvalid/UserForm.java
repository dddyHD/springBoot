package cn.suancloud.springBoot.formvalid;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import cn.suancloud.springBoot.util.Constant;
import cn.suancloud.springBoot.util.Encryption;

/**
 * Created by admin on 2018/4/18.
 */
public class UserForm implements Serializable {

  @NotNull(message = "用户名不允许为空")
  @Size(min=3,max=20,message = "用户名长度为3~20")
  private String username;
  @NotNull(message = "密码不允许为空")
  @Size(min=6,max=20,message = "密码长度为6~225")
  private String password;
  @Pattern(regexp = Constant.EMAIL_REGEX,message = "邮箱不合法")
  private String email;
  @Size(max=20,message = "用户名长度小于20")
  private String nickname;


  public UserForm() {
  }

  public UserForm(String username, String password, String email, String nickname) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.nickname = nickname;

  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return Encryption.getMD5(password);
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
}
