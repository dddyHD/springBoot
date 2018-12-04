package cn.suancloud.springBoot.controller;

import org.junit.Test;

import cn.suancloud.springBoot.util.ResponseData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends BaseControllerTest {
  @Override
  protected String getUrlPrefix() {
    return "/login";
  }

  /**
   * 无参数登录
   */
  @Test
  public void testLoginNoParameter() throws Exception {
    mockMvc.perform(post(getUrlPrefix()))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().json(writeAsString(ResponseData.badRequest())));
  }

  /**
   * 正常登录
   */
  @Test
  public void testLoginHasAllParameter() throws Exception {
    verifyLogin("admin","admin",status().isOk(),ResponseData.ok(),false);
  }

  /**
   * 用户名错误登录
   */
  @Test
  public void testLoginUsernameError() throws Exception {
    verifyLogin("admin_no_exist", "admin@123", status().isBadRequest(), ResponseData.badRequest(), true);
  }

  /**
   * 密码错误登录
   */
  @Test
  public void testLoginPasswordError() throws Exception {
    verifyLogin("admin", "admin_error", status().isBadRequest(), ResponseData.badRequest(), true);
  }

  /**
   * 正常退出登录
   */
  @Test
  public void testLogout() throws Exception {
    mockMvc.perform(post("/logout"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(writeAsString(ResponseData.ok()), true));
  }
}
