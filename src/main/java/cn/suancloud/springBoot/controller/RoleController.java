package cn.suancloud.springBoot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import cn.suancloud.springBoot.formvalid.RoleForm;
import cn.suancloud.springBoot.model.Role;
import cn.suancloud.springBoot.service.RoleService;
import cn.suancloud.springBoot.util.ResponseData;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
  protected static Logger logger = LoggerFactory.getLogger(RoleController.class);
  @Autowired
  RoleService roleService;

  @GetMapping
  public ResponseData list() {
    ResponseData data = ResponseData.ok();
    data.getData().put("list", roleService.findAll());
    return data;
  }

  @GetMapping("/{id}")
  public ResponseData getRole(@PathVariable Long id) {
    ResponseData data = ResponseData.ok();
    data.getData().put("role", roleService.findOne(id));
    return data;
  }

  @PostMapping
  public ResponseData addRole(@Valid @RequestBody RoleForm form, BindingResult result) throws FormException {
    ResponseData data = ResponseData.ok();
    hasErrors(result);
    if (roleService.isExistsRoleName(form.getRole_name())) {
      data = ResponseData.nameAlreadyExistsError();
    } else {
      Role role = new Role();
      BeanUtils.copyProperties(form, role);
      roleService.save(role);
    }
    return data;
  }

  @PutMapping("/{id}")
  public ResponseData editRole(@Valid @RequestBody RoleForm form, @PathVariable("id") Long id,
                               BindingResult result) throws FormException {
    ResponseData data = ResponseData.ok();
    hasErrors(result);
    Role role = roleService.findOne(id);
    BeanUtils.copyProperties(form, role);
    try {
      roleService.save(role);
    } catch (DataIntegrityViolationException e) {
      return data.uniqueConstraintError();
    }
    return data;

  }
  @DeleteMapping("/{id}")
  public ResponseData deleteRole(@PathVariable Long id){
    ResponseData data = ResponseData.ok();
    roleService.delete(id);
    return data;
  }
}
