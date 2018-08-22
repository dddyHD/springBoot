package cn.suancloud.springBoot.service;

import java.util.List;

import cn.suancloud.springBoot.model.Permission;

/**
 * Created by admin on 2018/4/16.
 */
public interface PermissionService extends BaseService<Permission,Long>{
  boolean isExistsPermissionName (String permission_name);
  List<Permission> findByUserId(Long id);
}
