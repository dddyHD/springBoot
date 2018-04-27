package cn.suancloud.springBoot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.suancloud.springBoot.dao.PermissionDao;
import cn.suancloud.springBoot.dao.RoleDao;
import cn.suancloud.springBoot.model.Permission;
import cn.suancloud.springBoot.model.Role;
import cn.suancloud.springBoot.service.PermissionService;
import cn.suancloud.springBoot.service.RoleService;

/**
 * Created by admin on 2018/4/16.
 */
@Service
public class PermissionImpl extends BaseServiceImpl<Permission,Long> implements PermissionService {

  PermissionDao dao;

  @Autowired
  public PermissionImpl(PermissionDao dao) {
    super(dao);
    this.dao = dao;
  }

}
