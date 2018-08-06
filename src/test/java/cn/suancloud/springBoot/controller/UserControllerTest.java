package cn.suancloud.springBoot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import cn.suancloud.springBoot.model.openshift.Metadata;
import cn.suancloud.springBoot.model.openshift.OSResponseData;
import cn.suancloud.springBoot.model.openshift.User;

import static cn.suancloud.springBoot.util.Method.DELETE;
import static cn.suancloud.springBoot.util.Method.GET;
import static cn.suancloud.springBoot.util.Method.POST;
import static cn.suancloud.springBoot.util.Method.PUT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
  @Override
  protected String getUrlPrefix() {
    return "/oapi/v1";
  }

  @Test
  public void testGetUser() throws Exception {
    OSResponseData osUser = new OSResponseData();
    osUser.setKind("User");
    osUser.setApiVersion("v1");
    osUser.setPassword("e6e061838856bf47e1de730719fb2609");
    testNoParameter("/users/admin", GET, osUser, false);
  }

  @Test
  public void testUserList() throws Exception {
    OSResponseData osUserList = new OSResponseData();
    osUserList.setKind("UserList");
    osUserList.setApiVersion("v1");
    testNoParameter("/users", GET, osUserList, false);
  }

  @Test
  public void testAddUser() throws Exception {
    //模拟提交数据格式
    Metadata metadata = new Metadata();
    metadata.setName("test2018");
    OSResponseData body = new OSResponseData();
    body.setApiVersion("v1");
    body.setKind("User");
    body.setGroups(new String[]{"user"});
    body.setPassword("test2018");
    body.setIdentities(new String[]{"custom:test2018"});
    body.setMetadata(metadata);
    OSResponseData osVail = new OSResponseData();
    osVail.setApiVersion("v1");
    osVail.setKind("User");
    osVail.setGroups(new String[]{"user"});
    //发送添加用户权限
    OSResponseData response = JSON.parseObject(
            testWithParameterAndStatus("/users", POST, body, osVail, status().isCreated(),false),
            OSResponseData.class
    );
    //发送验证请求并绑定权限
    metadata.setName(response.getIdentities()[0]);
    User user = new User(response.getMetadata().getUid(), response.getMetadata().getName());

    body = new OSResponseData();
    body.setApiVersion("v1");
    body.setKind("Identity");
    body.setMetadata(metadata);
    body.setProviderName("custom");
    body.setProviderUserName(response.getMetadata().getName());
    body.setUser(user);
    testWithParameterAndStatus("/identities", POST, body, body, status().isCreated(), false);
    osVail = new OSResponseData();
    osVail.setKind("Status");
    osVail.setApiVersion("v1");
    osVail.setStatus("Success");
    osVail.setCode(200);
    testNoParameter("/users/test2018", DELETE, osVail, false);
    testNoParameter("/identities/custom:test2018", DELETE, osVail, false);
  }

  @Test
  public void testEditUser() throws Exception {
    //模拟提交数据格式
    Metadata metadata = new Metadata();
    metadata.setName("testedit2018");
    OSResponseData body = new OSResponseData();
    body.setApiVersion("v1");
    body.setKind("User");
    body.setGroups(new String[]{"user"});
    body.setPassword("test2018");
    body.setIdentities(new String[]{"custom:testedit2018"});
    body.setMetadata(metadata);
    OSResponseData osVail = new OSResponseData();
    osVail.setApiVersion("v1");
    osVail.setKind("User");
    osVail.setGroups(new String[]{"user"});
    //发送添加用户权限
    OSResponseData response = JSON.parseObject(
            testWithParameterAndStatus("/users", POST, body, osVail,status().isCreated(), false),
            OSResponseData.class
    );
    //发送验证请求并绑定权限
    metadata.setName(response.getIdentities()[0]);
    User user = new User(response.getMetadata().getUid(), response.getMetadata().getName());

    body = new OSResponseData();
    body.setApiVersion("v1");
    body.setKind("Identity");
    body.setMetadata(metadata);
    body.setProviderName("custom");
    body.setProviderUserName(response.getMetadata().getName());
    body.setUser(user);
    testWithParameterAndStatus("/identities", POST, body, body, status().isCreated(), false);
    //修改密码
    //获取修改对象的信息
    body = new OSResponseData();
    body.setKind("User");
    body.setApiVersion("v1");
    body.setIdentities(new String[]{"custom:testedit2018"});
    body.setPassword("baa73e111470d3e17433a8deda476da5");
    response = JSONObject.parseObject(
            testNoParameter("/users/testedit2018", GET, body, false),
            OSResponseData.class
    );
    body = new OSResponseData();
    body.setPassword("098f6bcd4621d373cade4e832627b4f6");
    response.setPassword("test");
    testWithParameter("/users/testedit2018",PUT,response,body,false);

    //删除
    osVail = new OSResponseData();
    osVail.setKind("Status");
    osVail.setApiVersion("v1");
    osVail.setStatus("Success");
    osVail.setCode(200);
    testNoParameter("/users/testedit2018", DELETE, osVail, false);
    testNoParameter("/identities/custom:testedit2018", DELETE, osVail, false);
  }
}
