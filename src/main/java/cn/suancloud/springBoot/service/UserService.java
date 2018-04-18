package cn.suancloud.springBoot.service;

import cn.suancloud.springBoot.mould.User;

/**
 * Created by admin on 2018/4/16.
 */
public interface UserService extends BaseService<User,Long>{
  boolean isExistsUsername(String username);
}
