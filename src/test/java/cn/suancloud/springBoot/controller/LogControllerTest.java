package cn.suancloud.springBoot.controller;

import org.junit.Test;

import cn.suancloud.springBoot.util.ResponseData;

import static cn.suancloud.springBoot.util.Method.DELETE;
import static cn.suancloud.springBoot.util.Method.GET;

public class LogControllerTest extends BaseControllerTest {
  @Override
  protected String getUrlPrefix() {
    return "/log";
  }

  @Test
  public void testLogList() throws Exception {
    testNoParameter("/5/1",GET,ResponseData.ok(),false);
  }

  @Test
  public void testLogRole() throws Exception{
    testNoParameter("/2",DELETE,ResponseData.ok(),true);
  }

}
