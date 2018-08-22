package cn.suancloud.springBoot.controller.system;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cn.suancloud.springBoot.controller.BaseController;
import cn.suancloud.springBoot.exception.FormException;
import cn.suancloud.springBoot.formvalid.UserForm;
import cn.suancloud.springBoot.model.User;
import cn.suancloud.springBoot.service.UserService;
import cn.suancloud.springBoot.util.Encryption;
import cn.suancloud.springBoot.util.LdapUtil;
import cn.suancloud.springBoot.util.ResponseData;

/**
 * Created by admin on 2018/4/18.
 */

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

  @Autowired
  UserService userService;

  @GetMapping
  public ResponseData list() {
    ResponseData data = ResponseData.ok();
    data.getData().put("users", userService.findAll());
    return data;
  }

  @GetMapping("/{id}")
  public ResponseData get(@PathVariable Long id) {
    ResponseData data = ResponseData.ok();
    data.getData().put("user", userService.findOne(id));
    return data;
  }

  @DeleteMapping("/{id}")
  public ResponseData delete(@PathVariable Long id) {
    ResponseData data = ResponseData.ok();
    User user = userService.findOne(id);
    //Todo 超级用户不能被删除
    if (LdapUtil.deleteUser(user.getUsername()))
      userService.delete(id);
    else
      data = ResponseData.badRequest();
    return data;
  }

  @PostMapping
  public ResponseData register(@Valid @RequestBody UserForm form, BindingResult result) throws FormException {
    ResponseData data = ResponseData.ok();
    hasErrors(result);
    if (userService.isExistsUsername(form.getUsername())) {
      data = ResponseData.nameAlreadyExistsError();
    } else {
      User user = new User();
      BeanUtils.copyProperties(form, user);
      userService.save(user);
      if (LdapUtil.addUserLdap(user.getUsername(), user.getPassword()) == false) {
        data = ResponseData.addUserError();
        userService.delete(user.getUsername());
      }
    }
    return data;
  }

  @PutMapping("/{id}")
  public ResponseData edit(@RequestBody UserForm form, @PathVariable Long id,
                           BindingResult result) throws FormException {
    ResponseData data = ResponseData.ok();
    hasErrors(result);
    User user = userService.findOne(id);
    BeanUtils.copyProperties(form, user);
    //不允许修改用户名和密码
    user.setUsername(user.getUsername());
    user.setPassword(user.getPassword());
    try {
      userService.save(user);
    } catch (DataIntegrityViolationException e) {
      data = data.uniqueConstraintError();
    }
    return data;
  }

  @PatchMapping("/{id}")
  public ResponseData changePassword(@PathVariable Long id, HttpServletRequest request) {
    ResponseData data = ResponseData.ok();
    User user = userService.findOne(id);
    String password = request.getParameter("password");
    if (StringUtils.isEmpty(password))
      data = ResponseData.badRequest();
    else {
      user.setPassword(Encryption.getMD5(password));
      userService.save(user);
      LdapUtil.changePassword(user.getUsername(), user.getPassword());
    }
    return data;
  }



}
