package cn.suancloud.springBoot.controller;

import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.suancloud.springBoot.model.Permission;
import cn.suancloud.springBoot.util.ResponseData;

import static cn.suancloud.springBoot.util.Method.DELETE;
import static cn.suancloud.springBoot.util.Method.GET;
import static cn.suancloud.springBoot.util.Method.POST;
import static cn.suancloud.springBoot.util.Method.PUT;

public class PermissionControllerTest extends BaseControllerTest {
  @Override
  protected String getUrlPrefix() {
    return "/permission";
  }

  @Test
  public void testPermissionList() throws Exception {
    testNoParameter("",GET,ResponseData.ok(),false);
  }

  @Test
  public void testGetPermission() throws Exception {
    testNoParameter("/1",GET,ResponseData.ok(),false);
  }

  @Test
  public void testAddPermission() throws Exception {
    Permission permission = new Permission();
    permission.setPermission_name("test");
    permission.setMethod("GET");
    permission.setExcept(true);
    permission.setUrl("/test/**");
    permission.setRemark("test remark");
    testWithParameter("",POST,permission,ResponseData.ok(),true);
  }
  @Test
  public void testAddSamePermissionName() throws Exception {
    Permission permission = new Permission();
    permission.setPermission_name("all");
    permission.setMethod("GET");
    permission.setExcept(true);
    permission.setUrl("/test/**");
    permission.setRemark("test remark");
    testWithParameter("",POST,permission,ResponseData.nameAlreadyExistsError(),true);
  }
  @Test
  public void testAddSamePermissionUrlAndMethod() throws Exception {
    Permission permission = new Permission();
    permission.setPermission_name("test");
    permission.setMethod("ALL");
    permission.setExcept(false);
    permission.setUrl("/**");
    permission.setRemark("test remark");
    testWithParameter("",POST,permission,ResponseData.uniqueConstraintError(),true);
  }
  @Test
  public void testAddPermissionNullParam() throws Exception {
    Permission permission = new Permission();
    permission.setPermission_name("test");
    permission.setExcept(false);
    permission.setUrl("/test/**");
    testWithParameter("",POST,permission,ResponseData.formValidError("[方法类型错误]"),false);
  }

  @Test
  public void testEditPermission() throws Exception {
    Permission permission = new Permission();
    permission.setPermission_name("all");
    permission.setMethod("ALL");
    permission.setExcept(false);
    permission.setUrl("/**");
    permission.setRemark("root permission");
    testWithParameter("/1",PUT,permission,ResponseData.ok(),true);
  }


  @Test
  public void testEditPermission2() throws Exception {
    Permission permission = new Permission();
    permission.setPermission_name("all");
    permission.setMethod("POST");
    permission.setExcept(true);
    permission.setUrl("/test/**");
    permission.setRemark("root permission");
    testWithParameter("/1",PUT,permission,ResponseData.ok(),true);
  }

  /**
   * 唯一约束冲突
   */
  @Test
  @Transactional(propagation=Propagation.NOT_SUPPORTED)
  public void testEditPermissionUnique() throws Exception {
    Permission permission = new Permission();
    permission.setPermission_name("all");
    permission.setMethod("POST");
    permission.setExcept(true);
    permission.setUrl("/**");
    permission.setRemark("root permission");
    testWithParameter("/2",PUT,permission,ResponseData.uniqueConstraintError(),true);
  }

  /**
   * 唯一约束冲突2
   */
  @Test
  @Transactional(propagation=Propagation.NOT_SUPPORTED)
  public void testEditPermissionUnique2() throws Exception {
    Permission permission = new Permission();
    permission.setPermission_name("test");

    permission.setMethod("ALL");
    permission.setExcept(false);
    permission.setUrl("/**");

    permission.setRemark("root permission");
    testWithParameter("/2",PUT,permission,ResponseData.uniqueConstraintError(),true);
  }

  @Test
  public void testDeletePermission() throws Exception{
    testNoParameter("/2",DELETE,ResponseData.ok(),true);
  }
}
