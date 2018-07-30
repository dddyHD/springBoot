package cn.suancloud.springBoot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.suancloud.springBoot.dao.UserDao;
import cn.suancloud.springBoot.model.User;
import cn.suancloud.springBoot.service.UserService;

/**
 * Created by admin on 2018/4/16.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService{

  UserDao dao;

  @Autowired
  public UserServiceImpl(UserDao dao) {
    super(dao);
    this.dao = dao;
  }

  @Override
  public boolean isExistsUsername(String username) {
    return dao.findByUsername(username) != null;
  }

  @Override
  public User getUser(String username) {
    return dao.findByUsername(username);
  }
}
