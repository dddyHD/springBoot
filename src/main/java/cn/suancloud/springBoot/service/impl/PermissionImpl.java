package cn.suancloud.springBoot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.suancloud.springBoot.dao.PermissionDao;
import cn.suancloud.springBoot.model.Permission;
import cn.suancloud.springBoot.service.PermissionService;

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

  public Permission findByName(String permission_name){
    return dao.findByName(permission_name);
  }

  @Override
  public boolean isExistsPermissionName(String permission_name) {
    return findByName(permission_name)==null ? false : true;
  }
}
