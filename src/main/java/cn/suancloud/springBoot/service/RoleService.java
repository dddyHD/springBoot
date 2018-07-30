package cn.suancloud.springBoot.service;

import cn.suancloud.springBoot.model.Role;

/**
 * Created by admin on 2018/4/16.
 */
public interface RoleService extends BaseService<Role,Long>{
  Role getRole(String role_name);
  boolean isExistsRoleName(String role_name);
}
