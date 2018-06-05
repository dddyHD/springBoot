package cn.suancloud.springBoot.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cn.suancloud.springBoot.controller.HelloWorldController;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by admin on 2018/4/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTest{

  private MockMvc mvc;

  @Before
  public void setUp() throws Exception{
    mvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
  }
  @Test
  public void getHello() throws Exception{
    mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("Hello World")));
  }

}
