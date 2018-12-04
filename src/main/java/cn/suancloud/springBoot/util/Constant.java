package cn.suancloud.springBoot.util;

/**
 * Created by Administrator on 2018/1/30.
 */
public abstract class Constant {
  //参数配置
  public static final String MOBILE_REGEX = "^(?=\\d{11}$)^1(?:3\\d|4[57]|5[^4\\D]|66|7[^249\\D]|8\\d|9[89])\\d{8}$";
  public static final String ID_CARDS = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";
  public static final String EMAIL_REGEX = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
  public static final String[] METHOD_TYPE = {"ALL", "GET", "POST", "PUT", "PATCH"};


  //服务配置
  public static final Long JWT_TTLMILLIS = Long.valueOf(1000 * 60 * 60 * 24);
  public static final String JWT_SECRET = "SpringBoot";

  public static final String OPENSHIFT_URL = "https://172.168.1.35:8443";
  public static final String OPENSHIFT_ADMIN_USERNAME = "admin";
  public static final String OPENSHIFT_ADMIN_PASSWORD = "admin";


  public static final String LDAP_URL = "ldap://172.168.1.28:389";
  public static final String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
  public static final String LDAP_USERNAME = "cn=Manager,dc=my-domain,dc=com";
  public static final String LDAP_PASSWORD = "123456";
  public static final String SECURITY_AUTHENTICATION ="simple";
  public static final String LDAP_BASE_DN = "ou=People,dc=my-domain,dc=com";


}
