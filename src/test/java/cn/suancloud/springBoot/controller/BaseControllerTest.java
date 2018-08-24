package cn.suancloud.springBoot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.suancloud.springBoot.model.User;
import cn.suancloud.springBoot.util.Method;
import cn.suancloud.springBoot.util.ResponseData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public abstract class BaseControllerTest {
  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  protected String token;
  protected String openshift_token;


  protected abstract String getUrlPrefix();

  protected String writeAsString(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  protected void login() {
    if (StringUtils.isEmpty(token) || StringUtils.isEmpty(openshift_token))
      try {
        doJavaJwtLogin("admin", "admin@123");
      } catch (Exception e) {
        System.out.println("登录错误！");
        e.printStackTrace();
      }
  }

  /**
   * java登录
   */
  protected void doJavaJwtLogin(String username, String password) throws Exception {
    token = objectMapper.readValue(
            mockMvc.perform(
                    post("/login").contentType(MediaType.APPLICATION_JSON)
                            .content(writeAsString(new User(username, password)))
            ).andReturn().getResponse().getContentAsString(),
            ResponseData.class
    ).getData().get("J_Authorization").toString();
    Thread.sleep(1000);
    doOpenshiftJwtLogin(username, password);
  }

  /**
   * openshift 登录
   */
  protected void doOpenshiftJwtLogin(String username, String password) throws Exception {
    openshift_token = mockMvc.perform(
            post("/openshift_login")
                    .header("J_Authorization", token)
                    .header("cookie", "csrf=c6daad07-8a31-11e8-b9a2-00163e000194")
                    .contentType("application/x-www-form-urlencoded")
                    .param("username", username)
                    .param("password", password)
                    .param("then", "/oauth/authorize?client_id=openshift-web-console&response_type=token&redirect_uri=https%3A%2F%2F112.74.27.228%3A8443%2Fconsole%2Foauth")
                    .param("csrf", "c6daad07-8a31-11e8-b9a2-00163e000194")
    ).andReturn().getResponse().getContentAsString();
  }

  protected String testNoParameter(String name, Method method, Object expected,
                                   boolean strict) throws Exception {
    testUnauthorized(name, method);

    login();

    ResultActions result = mockMvc.perform(getBuilder(getUrlPrefix() + name, method)
            .header("J_Authorization", token)
            .header("Authorization", openshift_token)
    )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(writeAsString(expected), strict));
    return result.andReturn().getResponse().getContentAsString();
  }

  protected String testWithParameter(String name, Method method, Object parameter, Object expected,
                                     boolean strict) throws Exception {
    testUnauthorized(name, method);

    login();

    ResultActions result = mockMvc.perform(
            getBuilder(getUrlPrefix() + name, method)
                    .header("J_Authorization", token)
                    .header("Authorization", openshift_token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(writeAsString(parameter))
    )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(writeAsString(expected), strict));
    return result.andReturn().getResponse().getContentAsString();

  }

  protected String testWithParameterAndStatus(String name, Method method, Object parameter, Object
          expected, ResultMatcher status, boolean strict) throws Exception {
    testUnauthorized(name, method);

    login();

    ResultActions result = mockMvc.perform(
            getBuilder(getUrlPrefix() + name, method)
                    .header("J_Authorization", token)
                    .header("Authorization", openshift_token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(writeAsString(parameter))
    )
            .andDo(print())
            .andExpect(status)
            .andExpect(content().json(writeAsString(expected), strict));
    return result.andReturn().getResponse().getContentAsString();

  }

  protected void verifyLogin(String username, String password, ResultMatcher matcher,
                             ResponseData responseData, boolean strict) throws Exception {
    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeAsString(new User(username, password)))
    )
            .andDo(print())
            .andExpect(matcher)
            .andExpect(content().json(writeAsString(responseData), strict));


  }


  /**
   * 验证是否有权限
   */
  protected void testUnauthorized(String name, Method method) throws Exception {
    mockMvc.perform(getBuilder(getUrlPrefix() + name, method))
            .andDo(print())
            .andExpect(status().isUnauthorized());
  }

  /**
   * 表单验证
   */
  protected void testFormValid(String name, Method method) throws Exception {
    ResponseData expected = ResponseData.formValidError("请求体为空");
    mockMvc.perform(getBuilder(getUrlPrefix() + name, method).header("authorization", token))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(writeAsString(expected)));
  }

  private MockHttpServletRequestBuilder getBuilder(String url, Method method) {
    switch (method) {
      case GET:
        return get(url);
      case POST:
        return post(url);
      case PUT:
        return put(url);
      case PATCH:
        return patch(url);
      case DELETE:
        return delete(url);
      default:
        throw new IllegalArgumentException("requestMethod is illegal : " + method);
    }
  }

}
