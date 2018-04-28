package cn.suancloud.springBoot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 2018/4/16.
 */
@RestController
public class HelloWorldController {
  @RequestMapping("/")
  public String index(){
    return "Hello World";
  }
}
