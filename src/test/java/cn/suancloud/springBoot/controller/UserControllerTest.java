package cn.suancloud.springBoot.controller;

import org.junit.Test;
import org.springframework.util.Assert;

import cn.suancloud.springBoot.model.User;
import cn.suancloud.springBoot.util.LdapUtil;
import cn.suancloud.springBoot.util.ResponseData;
import cn.suancloud.springBoot.util.StringUtil;

import static cn.suancloud.springBoot.util.Method.GET;
import static cn.suancloud.springBoot.util.Method.POST;
import static cn.suancloud.springBoot.util.Method.PUT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
  @Override
  protected String getUrlPrefix() {
    return "/user";
  }

  @Test
  public void testUserList() throws Exception {
    testNoParameter("", GET, ResponseData.ok(), false);
  }

  @Test
  public void testGetUser() throws Exception {
    testNoParameter("/1", GET, ResponseData.ok(), false);
  }

  @Test
  public void testAddUser() throws Exception {
    User user = new User();
    String username = "test" + StringUtil.getTimeStamp();
    user.setUsername(username);
    user.setPassword("test@123");
    testWithParameter("", POST, user, ResponseData.ok(), true);
    verifyLogin(username, user.getPassword(), status().isOk(), ResponseData.ok(), false);
    Assert.isTrue(LdapUtil.deleteUser(username), "删除ldap失败！");
  }

  @Test
  public void testEditUser() throws Exception {
    User user = new User();
    user.setUsername("admin");
    user.setPassword("12345678");
    user.setEmail("heng@qq.com");
    user.setNickname("min");
    testWithParameter("/4",PUT,user,ResponseData.ok(),true);
  }

  @Test
  public void changePassword() throws Exception{
    //todo 改密码

  }

}
