package cn.suancloud.springBoot.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2017/6/9.
 *  哈希码加密
 */
public class Encryption {
  // Sha1 加密规则
  public static String getMD5(String str) {
    if (null == str || 0 == str.length()) {
      return null;
    }
    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
    try {
      MessageDigest mdTemp = MessageDigest.getInstance("MD5");
      mdTemp.update(str.getBytes("UTF-8"));

      byte[] md = mdTemp.digest();
      int j = md.length;
      char[] buf = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
        buf[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(buf);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String getBCrypt(String s){
    return new BCryptPasswordEncoder().encode(s);
  }

  public static void main(String[] args) {
    System.out.println(getMD5("admin"));
    System.out.println(getBCrypt("admin"));
  }
}
