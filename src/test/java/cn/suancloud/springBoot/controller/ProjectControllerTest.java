package cn.suancloud.springBoot.controller;

import org.junit.Test;

import cn.suancloud.springBoot.formvalid.ProjectForm;
import cn.suancloud.springBoot.util.ResponseData;

import static cn.suancloud.springBoot.util.Method.GET;

public class ProjectControllerTest extends BaseControllerTest {
  @Override
  protected String getUrlPrefix() {
    return "/japi/v1/projects";
  }

  @Test
  public void testAllProjectList() throws Exception {
    testNoParameter("/all",GET,ResponseData.ok(), false);
  }

  @Test
  public void testSelfProjectList() throws Exception {
    testNoParameter("/self",GET,ResponseData.ok(), false);
  }

  @Test
  public void subscribe(){
    ProjectForm projectForm = new ProjectForm();
    //projectForm.setId();

  }








}
