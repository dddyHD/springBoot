package cn.suancloud.springBoot.controller;

import org.junit.Test;
import org.springframework.util.Assert;

import cn.suancloud.springBoot.model.Apply;
import cn.suancloud.springBoot.util.ResponseData;

import static cn.suancloud.springBoot.util.Method.GET;
import static cn.suancloud.springBoot.util.Method.PATCH;
import static cn.suancloud.springBoot.util.Method.POST;

public class ApplyControllerTest extends BaseControllerTest {
  @Override
  protected String getUrlPrefix() {
    return "/japi/v1/apply";
  }

  @Test
  public void testApplyList() throws Exception {
    testNoParameter("", GET, ResponseData.ok(), false);
  }

  @Test
  public void testSelfApplyProjectList() throws Exception {
    String result=testNoParameter("/self", GET, ResponseData.ok(), false);
    Assert.isTrue(result.contains("\"id\":2,\"applicant\":\"admin\",\"project\":\"unittest2\""),
            "contains unittest2");
  }


  @Test
  public void testAddApply()throws Exception {
    //订阅
    doJavaJwtLogin("user","user@123");
    Apply apply = new Apply();
    apply.setProject("unittest3");
    apply.setType("dy");//订阅
    apply.setReason("because you!");
    apply.setRemark("之前没申请，要申请");
    apply.setStatus(true);
    testWithParameter("",POST,apply,ResponseData.ok(),true);
    login();
  }

  @Test
  public void testAddApplyExistsProject()throws Exception {
    doJavaJwtLogin("user","user@123");
    Apply apply = new Apply();
    apply.setProject("unittest1");
    apply.setType("dy");
    apply.setReason("because you!");
    apply.setRemark("已经拥有了该项目，还有要申请");
    apply.setStatus(true);
    testWithParameter("",POST,apply,ResponseData.projectAlreadyOwn(),true);
    login();
  }

  @Test
  public void testAddApplyHasApplying()throws Exception {
    doJavaJwtLogin("user","user@123");
    Apply apply = new Apply();
    apply.setProject("unittest2");
    apply.setType("dy");
    apply.setReason("because you!");
    apply.setRemark("已经申请了项目，还有要申请");
    apply.setStatus(true);
    testWithParameter("",POST,apply,ResponseData.alreadyApply(),true);
    login();
  }
  @Test
  public void testAddApplyNoExistsProject()throws Exception {
    Apply apply = new Apply();
    apply.setProject("projectnoexists");
    apply.setType("dy");
    apply.setReason("because you!");
    apply.setRemark("项目名不存在！！！");
    apply.setStatus(true);
    testWithParameter("",POST,apply,ResponseData.projectNotExist(),true);
  }

  //以下是退订业务
  @Test
  public void testAddApply2() throws Exception {
    //退订
    doJavaJwtLogin("user","user@123");
    Apply apply = new Apply();
    apply.setProject("unittest1");
    apply.setType("td");
    apply.setReason("because you!");
    apply.setRemark("unittest1 已经拥有，但申请退订");
    apply.setStatus(true);
    testWithParameter("",POST,apply,ResponseData.ok(),true);
    login();
  }
  @Test
  public void testAddApplyNoWith() throws Exception {
    //退订
    doJavaJwtLogin("user","user@123");
    Apply apply = new Apply();
    apply.setProject("unittest3");
    apply.setType("td");//订阅
    apply.setReason("because you!");
    apply.setRemark("unittest3 没有拥有，但申请退订");
    apply.setStatus(true);
    testWithParameter("",POST,apply,ResponseData.projectNotOwn(),true);
    login();
  }

  @Test
  public void testAddApplyRepeatTD() throws Exception {
    //退订
    doJavaJwtLogin("user","user@123");
    Apply apply = new Apply();
    apply.setProject("unittest4");
    apply.setType("td");//订阅
    apply.setReason("because you!");
    apply.setRemark("unittest4 已经在申请退订当中，暂未审批，但再次申请退订");
    apply.setStatus(true);
    testWithParameter("",POST,apply,ResponseData.alreadyApply(),true);
    login();
  }

  @Test
  public void testCancelApply() throws Exception {
    //正常取消
    testNoParameter("/2",PATCH,ResponseData.ok(),true);
    //取消申请通过的
    testNoParameter("/1",PATCH,ResponseData.badRequest(),true);
    //不是自己申请的
    testNoParameter("/3",PATCH,ResponseData.badRequest(),true);
  }


}
