package cn.suancloud.springBoot.controller;

import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import cn.suancloud.springBoot.model.Permission;
import cn.suancloud.springBoot.model.Role;
import cn.suancloud.springBoot.util.ResponseData;

import static cn.suancloud.springBoot.util.Method.DELETE;
import static cn.suancloud.springBoot.util.Method.GET;
import static cn.suancloud.springBoot.util.Method.POST;
import static cn.suancloud.springBoot.util.Method.PUT;

public class RoleControllerTest extends BaseControllerTest {
  @Override
  protected String getUrlPrefix() {
    return "/role";
  }

  @Test
  public void testRoleList() throws Exception {
    testNoParameter("",GET,ResponseData.ok(),false);
  }

  @Test
  public void testGetRole() throws Exception {
    testNoParameter("/1",GET,ResponseData.ok(),false);
  }

  @Test
  public void testAddRole() throws Exception {

    Role role = new Role();
    role.setRole_name("role_test");
    List<Permission> permissions = new ArrayList<>();
    permissions.add(new Permission(1L));
    role.setPermissions(permissions);
    testWithParameter("",POST,role,ResponseData.ok(),false);
  }

  @Test
  public void testAddSameRoleName() throws Exception {
    Role role = new Role();
    role.setRole_name("admin");
    List<Permission> permissions = new ArrayList<>();
    permissions.add(new Permission(1L));
    role.setPermissions(permissions);
    testWithParameter("",POST,role,ResponseData.nameAlreadyExistsError(),true);
  }

  @Test
  public void testAddRoleAndNullPermission() throws Exception {
    Role role = new Role();
    role.setRole_name("role_test");
    testWithParameter("",POST,role,ResponseData.ok(),true);
  }

  /**
   * 不改变数据修改
   */
  @Test
  public void testEditRole() throws Exception {
    Role role = new Role();
    role.setRole_name("admin");
    List<Permission> permissions = new ArrayList<>();
    permissions.add(new Permission(1L));
    role.setPermissions(permissions);
    testWithParameter("/1",PUT,role,ResponseData.ok(),true);
  }

  /**
   * 同时修改角色的名和权限
   */
  @Test
  public void testEditRole2() throws Exception {
    Role role = new Role();
    role.setRole_name("test");
    List<Permission> permissions = new ArrayList<>();
    permissions.add(new Permission(2L));
    role.setPermissions(permissions);
    testWithParameter("/1",PUT,role,ResponseData.ok(),true);
  }

  /**
   * 同名冲突
   *  ** 如果该方法的是检验数据库主键唯一冲突这一类问题 应该设置 取消事务
   */
  @Test
  @Transactional(propagation=Propagation.NOT_SUPPORTED)
  public void testEditRoleSameName() throws Exception{
    Role role = new Role();
    role.setRole_name("admin");
    testWithParameter("/2",PUT,role,ResponseData.uniqueConstraintError(),true);
  }

  @Test
  public void testDeleteRole() throws Exception{
    testNoParameter("/2",DELETE,ResponseData.ok(),true);
  }
}
