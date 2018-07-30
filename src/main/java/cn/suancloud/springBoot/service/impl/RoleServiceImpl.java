package cn.suancloud.springBoot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.suancloud.springBoot.dao.RoleDao;
import cn.suancloud.springBoot.model.Role;
import cn.suancloud.springBoot.service.RoleService;

/**
 * Created by admin on 2018/4/16.
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

  RoleDao dao;

  @Autowired
  public RoleServiceImpl(RoleDao dao) {
    super(dao);
    this.dao = dao;
  }

  @Override
  public Role getRole(String role_name) {
    return dao.getRole(role_name);
  }

  @Override
  public boolean isExistsRoleName(String role_name) {
    return getRole(role_name) == null ? false : true;
  }
}
