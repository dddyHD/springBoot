package cn.suancloud.springBoot.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import cn.suancloud.springBoot.util.Encryption;

public class MyPasswordEncoder implements PasswordEncoder {
  @Override
  public String encode(CharSequence rawPassword) {
    return Encryption.getMD5(rawPassword.toString());
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return encode(rawPassword).equals(encodedPassword);
  }
}
