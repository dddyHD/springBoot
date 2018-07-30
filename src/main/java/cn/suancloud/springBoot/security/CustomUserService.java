package cn.suancloud.springBoot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import cn.suancloud.springBoot.dao.PermissionDao;
import cn.suancloud.springBoot.dao.UserDao;
import cn.suancloud.springBoot.model.Permission;

/**
 * Created by admin on 2018/4/27.
 */
@Service
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

  @Autowired
  UserDao userDao;
  @Autowired
  PermissionDao permissionDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    cn.suancloud.springBoot.model.User user = userDao.findByUsername(username);
    if (user != null){
      List<Permission> permissions = permissionDao.findByUserId(user.getId());
      List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
      for (Permission permission : permissions) {
        if (permission != null && permission.getPermission_name() != null) {
          GrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getUrl(),
                  permission.getMethod(), permission.getExcept());
          grantedAuthorities.add(grantedAuthority);
        }
      }
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }else {
      throw new UsernameNotFoundException("username error");
    }
  }

}
