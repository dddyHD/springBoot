package cn.suancloud.springBoot.util;

import com.sun.istack.internal.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import static cn.suancloud.springBoot.util.Constant.LDAP_BASE_DN;
import static cn.suancloud.springBoot.util.Constant.LDAP_FACTORY;
import static cn.suancloud.springBoot.util.Constant.LDAP_PASSWORD;
import static cn.suancloud.springBoot.util.Constant.LDAP_URL;
import static cn.suancloud.springBoot.util.Constant.LDAP_USERNAME;
import static cn.suancloud.springBoot.util.Constant.SECURITY_AUTHENTICATION;

public class LdapUtil {

  private static Logger logger = Logger.getLogger(LdapUtil.class);

  // 通过连接LDAP服务器对用户进行认证，返回LDAP对象
  public static DirContext getLoginContext() {
    DirContext ctx = null;
    Hashtable env = new Hashtable();
    env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
    env.put(Context.SECURITY_CREDENTIALS, LDAP_PASSWORD);
    env.put(Context.SECURITY_PRINCIPAL, LDAP_USERNAME);
    env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
    env.put(Context.PROVIDER_URL, LDAP_URL);
    try {
      // 连接LDAP进行认证
      ctx = new InitialDirContext(env);
      System.out.println("【" + LDAP_USERNAME + "】用户于【" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "】登陆系统成功");
    } catch (javax.naming.AuthenticationException e) {
      System.out.println("认证失败:" + e);
    } catch (Exception e) {
      System.out.println("认证出错" + e);
      e.printStackTrace();
    }
    return ctx;
  }

  // 添加用户到 ou=People
  public static boolean addUserLdap(String username, String password) {
    DirContext ctx = null;
    try {
      ctx = LdapUtil.getLoginContext();
      //String root = "ou=People,dc=my-domain,dc=com"; // LDAP的根节点的DC
      BasicAttributes attrs = new BasicAttributes();
      BasicAttribute objclassSet = new BasicAttribute("objectClass");
      objclassSet.add("person");
      objclassSet.add("top");
      objclassSet.add("organizationalPerson");
      objclassSet.add("inetOrgPerson");
      objclassSet.add("posixAccount");
      attrs.put("userPassword", password);
      attrs.put("cn", username);
      attrs.put("sn", username);
      //时间戳
      attrs.put("uidNumber", StringUtil.getTimeStamp());
      attrs.put("gidNumber", "1000");
      attrs.put("homeDirectory", "/home/" + username);
      //attrs.put("uidNumber", "1002");
      attrs.put(objclassSet);
      ctx.createSubcontext("uid=" + username + "," + LDAP_BASE_DN, attrs);
      //User4Group(username, "ldapgroup1", ctx, DirContext.ADD_ATTRIBUTE);
      ctx.close();
      return true;
    } catch (NamingException ex) {
      try {
        if (ctx != null) {
          ctx.close();
        }
      } catch (NamingException namingException) {
        namingException.printStackTrace();
      }
      logger.info("--------->>添加用户失败" + ex);
    }
    return false;
  }

  //用户操作用户组
  public static boolean User4Group(String memberUid, String groupName, DirContext ctx1, int mod_op) {
    ModificationItem[] mods = new ModificationItem[1];
    mods[0] = new ModificationItem(mod_op
            , new BasicAttribute("MemberUid", memberUid));
    try {
      ctx1.modifyAttributes("cn=" + groupName + ",ou=Group,dc=my-domain,dc=com", mods);
      return true;
    } catch (NamingException e) {
      e.printStackTrace();
    }
    return false;
  }


  //删除用户
  public static boolean deleteUser(String username) {//, String group
    DirContext ctx = null;
    ctx = LdapUtil.getLoginContext();
    try {
      //User4Group(username, group, ctx, DirContext.REMOVE_ATTRIBUTE);
      ctx.destroySubcontext("uid=" + username + "," + LDAP_BASE_DN);
    } catch (NamingException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 基于 ou=People
   *
   * @param targetDn 目标路径
   * @param action   修改类型
   * @param map      需要修改的属性
   */
  private static void modify(DirContext ctx, String targetDn, String action, Map<String, String>
          map) throws NamingException {
    int i = 0;
    ModificationItem[] mods = new ModificationItem[map.size()];
    Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
    //修改原来的属性
    if ("replace".equals(action)) {
      while (entries.hasNext()) {
        Map.Entry<String, String> entry = entries.next();
        mods[i] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(entry.getKey(), entry.getValue()));
        i++;
      }
    }
    //添加属性
    if ("add".equals(action)) {
      while (entries.hasNext()) {
        Map.Entry<String, String> entry = entries.next();
        mods[i] = new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute(entry.getKey(), entry.getValue()));
        i++;
      }
    }
    //移除原来的属性
    if ("remove".equals(action)) {
      while (entries.hasNext()) {
        Map.Entry<String, String> entry = entries.next();
        mods[i] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute(entry.getKey(), entry.getValue()));
        i++;
      }
    }
    ctx.modifyAttributes(targetDn + "," + LDAP_BASE_DN, mods);
  }


  public static boolean changePassword(String username, String password) {
    DirContext ctx = null;
    ctx = LdapUtil.getLoginContext();
    Map<String, String> map = new HashMap<>();
    map.put("userPassword", password);
    try {
      modify(ctx, "uid=" + username, "replace", map);
      ctx.close();
      return true;
    } catch (NamingException e) {
      e.printStackTrace();
      try {
        if (ctx != null) {
          ctx.close();
        }
      } catch (NamingException namingException) {
        namingException.printStackTrace();
      }
    }
    return false;
  }


  public static void main(String[] args) {
    //LdapUtil.getLoginContext();
    System.out.println(LdapUtil.addUserLdap("admin", Encryption.getMD5("admin")));
    //changePassword("ldapuser3", "min");
    //deleteUser("ldapuser3");
//    LdapUtil.updatePasswordLdap("10000", "1234567");
//    LdapUtil.deleteUserLdap("10000");
  }
}
