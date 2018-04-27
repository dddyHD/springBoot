package cn.suancloud.springBoot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.suancloud.springBoot.dao.RoleDao;
import cn.suancloud.springBoot.dao.UserDao;
import cn.suancloud.springBoot.model.Role;

import cn.suancloud.springBoot.service.RoleService;
import cn.suancloud.springBoot.service.UserService;

/**
 * Created by admin on 2018/4/16.
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {

  RoleDao dao;

  @Autowired
  public RoleServiceImpl(RoleDao dao) {
    super(dao);
    this.dao = dao;
  }

}
