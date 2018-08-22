package cn.suancloud.springBoot.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class PermissionServiceTest extends BaseServiceTest {

  @Autowired
  PermissionService permissionService;

  @Test
  public void testFindByUserId(){
    Assert.isTrue(permissionService.findByUserId(4L).size()==1,"权限出错！");
  }

}
