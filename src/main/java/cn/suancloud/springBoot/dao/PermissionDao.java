package cn.suancloud.springBoot.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import cn.suancloud.springBoot.model.Permission;

/**
 * Created by admin on 2018/4/16.
 */
@Repository
public interface PermissionDao extends CrudRepository<Permission, Long> {
  @Query(value = "SELECT DISTINCT p.* FROM user u LEFT JOIN user_role ur on ur.user_id=u.id " +
          "LEFT JOIN role r on ur.role_id=ur.role_id " +
          "LEFT JOIN role_permission rp on rp.role_id=rp.permission_id " +
          "LEFT JOIN permission p on rp.permission_id=u.id;",
          nativeQuery = true
  )
  List<Permission> findByUserId(Long userId);

  @Query(value = "FROM Permission WHERE permission_name = ?1 ")
  Permission findByName(String permission_name);

}
