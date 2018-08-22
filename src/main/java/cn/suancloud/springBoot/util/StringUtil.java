package cn.suancloud.springBoot.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

  /**
   * 通过真正表达式截取字符串
   *
   * @param str 源数据
   * @param reg 正则表达式
   * @return 如果源数据或者截取到的为"" 返回原来的数据
   */
  public static String substring(String str, String reg) {
    if (!StringUtils.isEmpty(str)) {
      Pattern pattern = Pattern.compile(reg);
      Matcher matcher = pattern.matcher(str);
      if (matcher.find())
        return matcher.group(1);
    }
    return str;
  }

  /**
   * 获取系统时间字符串
   * @return String
   */
  public static String getTimeStamp(){
    return String.valueOf(System.currentTimeMillis());
  }

}
