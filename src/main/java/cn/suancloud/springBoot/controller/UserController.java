package cn.suancloud.springBoot.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import cn.suancloud.springBoot.exception.FormException;
import cn.suancloud.springBoot.formvalid.UserForm;
import cn.suancloud.springBoot.model.User;
import cn.suancloud.springBoot.service.UserService;
import cn.suancloud.springBoot.util.ResponseData;

/**
 * Created by admin on 2018/4/18.
 */

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

  @Autowired
  UserService userService;

  @GetMapping("/")
  public ResponseData list(){
    ResponseData data = ResponseData.ok();
    data.getData().put("list",userService.findAll());
    return data;
  }

  @PostMapping("/")
  public ResponseData register(@Valid @RequestBody UserForm form, BindingResult result) throws FormException {
    ResponseData data = ResponseData.ok();
    hasErrors(result);
    if (userService.isExistsUsername(form.getUsername())){
      data = ResponseData.usernameAlreadyExistsError();
    }else {
      User user  = new User();
      BeanUtils.copyProperties(form,user);
      userService.save(user);
    }
    return data;
  }





}
