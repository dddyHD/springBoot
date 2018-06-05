package cn.suancloud.springBoot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import cn.suancloud.springBoot.model.User;
import cn.suancloud.springBoot.util.ResponseData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by admin on 2018/5/9.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public abstract class BaseControllerTest {
  @Autowired
  protected MockMvc mvc;
  @Autowired
  protected ObjectMapper objectMapper;
  protected String token;

  protected String writeAsString(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  protected void login(String username ,String password) throws Exception {
    token = objectMapper.readValue(
            mvc.perform(
                    post("/login").contentType(MediaType.APPLICATION_JSON)
                            .content(writeAsString(new User(username, password)))
            ).andReturn().getResponse().getContentAsString(),
            ResponseData.class
    ).getData().get("token").toString();
  }
}
