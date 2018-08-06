package cn.suancloud.springBoot.controller;

import org.junit.Test;
import org.springframework.http.MediaType;

import cn.suancloud.springBoot.model.User;
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
    mockMvc.perform(
            post(getUrlPrefix()).contentType(MediaType.APPLICATION_JSON)
                    .content(writeAsString(new User("" +
                            "admin", "admin@123")))
    )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(writeAsString(ResponseData.ok()), false));
  }

  /**
   * 用户名错误登录
   */
  @Test
  public void testLoginUsernameError() throws Exception {
    mockMvc.perform(post(getUrlPrefix())
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeAsString(new User("admin_no_exist", "admin@123")))
    )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().json(writeAsString(ResponseData.badRequest())));
  }

  /**
   * 密码错误登录
   */
  @Test
  public void testLoginPasswordError() throws Exception {
    mockMvc.perform(post(getUrlPrefix())
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeAsString(new User("admin", "admin_error")))
    )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().json(writeAsString(ResponseData.badRequest())));
  }
  /**
   * 正常退出登录
   */
  @Test
  public void testLogout()throws Exception{
    mockMvc.perform(post("/logout"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(writeAsString(ResponseData.ok()), true));
  }
}
