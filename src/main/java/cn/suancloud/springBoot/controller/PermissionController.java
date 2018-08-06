package cn.suancloud.springBoot.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import cn.suancloud.springBoot.exception.FormException;
import cn.suancloud.springBoot.formvalid.PermissionForm;
import cn.suancloud.springBoot.model.Permission;
import cn.suancloud.springBoot.service.PermissionService;
import cn.suancloud.springBoot.service.RoleService;
import cn.suancloud.springBoot.service.UserService;
import cn.suancloud.springBoot.util.ResponseData;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {

  @Autowired
  PermissionService permissionService;
  @Autowired
  UserService userService;
  @Autowired
  RoleService roleService;

  @GetMapping
  public ResponseData list() {
    ResponseData data = ResponseData.ok();
    data.getData().put("list", permissionService.findAll());
    return data;
  }

  @GetMapping("/{id}")
  public ResponseData getPermission(@PathVariable Long id) {
    ResponseData data = ResponseData.ok();
    data.getData().put("permission", permissionService.findOne(id));
    return data;
  }

  @PostMapping
  public ResponseData addPermission(@Valid @RequestBody PermissionForm form, BindingResult result) throws FormException {
    ResponseData data = ResponseData.ok();
    hasErrors(result);
    if (permissionService.isExistsPermissionName(form.getPermission_name())) {
      data = ResponseData.nameAlreadyExistsError();
    } else {
      Permission permission = new Permission();
      BeanUtils.copyProperties(form, permission);
      try {
        permissionService.save(permission);
      } catch (DataIntegrityViolationException e) {
        return data.uniqueConstraintError();
      }
    }
    return data;
  }

  @PutMapping("/{id}")
  public ResponseData editPermission(@Valid @RequestBody PermissionForm form, @PathVariable Long id,
                                     BindingResult result) throws FormException {
    ResponseData data = ResponseData.ok();
    hasErrors(result);
    Permission permission = permissionService.findOne(id);
    BeanUtils.copyProperties(form, permission);
    try {
      permissionService.save(permission);
    } catch (DataIntegrityViolationException e) {
      return data.uniqueConstraintError();
    }
    return data;
  }

  @DeleteMapping("/{id}")
  public ResponseData deletePermission(@PathVariable Long id) {
    ResponseData data = ResponseData.ok();
    permissionService.delete(id);
    return data;
  }
}
