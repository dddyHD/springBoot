package cn.suancloud.springBoot.controller.japi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cn.suancloud.springBoot.controller.BaseController;
import cn.suancloud.springBoot.exception.FormException;
import cn.suancloud.springBoot.formvalid.ProjectForm;
import cn.suancloud.springBoot.model.Apply;
import cn.suancloud.springBoot.service.ApplyService;
import cn.suancloud.springBoot.util.ResponseData;
import io.fabric8.kubernetes.api.model.ObjectReference;
import io.fabric8.openshift.api.model.DoneableRoleBinding;
import io.fabric8.openshift.client.OpenShiftClient;

import static cn.suancloud.springBoot.util.openshift.GetClient.getAdminOClient;
import static cn.suancloud.springBoot.util.openshift.GetClient.getOClient;

@RestController
@RequestMapping("/japi/v1/projects")
public class ProjectController extends BaseController {

  @Autowired
  ApplyService applyService;

  //所有项目
  @GetMapping("/all")
  public ResponseData allProjectList() {
    ResponseData data = ResponseData.ok();
    OpenShiftClient oSClient = getAdminOClient();
    data.getData().put("list", oSClient.projects().list());
    oSClient.close();
    return data;
  }

  //当前用户的的项目
  @GetMapping("/self")
  public Object selfProjectList(HttpServletRequest request) {
    ResponseData data = ResponseData.ok();
    OpenShiftClient oSClient = getOClient(request.getAttribute("current_os_token").toString());
    data.getData().put("list", oSClient.projects().list());
    oSClient.close();
    return data;
  }


  /**
   * 订阅同意操作 id 申请的id， project 申请的项目名, role 用户要添加的角色 ,kind 有User和Group两种 ,name用户名
   *
   * @return http status 200 和修改完成的 roleBindings
   */
  @PutMapping("/subscribe")
  public Object subscribe(@Valid @RequestBody ProjectForm form, BindingResult result,
                          HttpServletRequest request) throws FormException {
    hasErrors(result);
    return operation(form, request);
  }

  @PutMapping("/unsubscribe")
  public Object unsubscribe(@Valid @RequestBody ProjectForm form, BindingResult result,
                            HttpServletRequest request) throws FormException {
    hasErrors(result);
    return operation(form, request);
  }

  @PutMapping("/disagree/{id}")
  public ResponseData disapprove(@PathVariable Long id, HttpServletRequest request) {
    String approver = request.getAttribute("current_user").toString();
    ResponseData data = ResponseData.ok();
    Apply apply = applyService.findOne(id);
    apply.setAgree(false);
    apply.setApprover(approver);
    apply.setApprovalTime(new Date());
    applyService.save(apply);
    return data;
  }

  public ResponseData operation(ProjectForm form, HttpServletRequest request) {
    ResponseData data = ResponseData.ok();
    Apply apply = applyService.findOne(form.getId());
    if (apply.getProject().equals(form.getProject())
            && apply.getApplicant().equals(form.getName())
            && apply.getAgree() == null) {
      //admin client get admin role
      OpenShiftClient oSClient = getAdminOClient();
      // add user to role
      ObjectReference objectReference = new ObjectReference();
      objectReference.setKind(form.getKind());
      objectReference.setName(form.getName());
      // send out
      DoneableRoleBinding roleBinding;
      if (request.getRequestURI().contains("/subscribe/")) {//订阅
        roleBinding = oSClient.roleBindings().inNamespace(form.getProject())
                .withName(form.getRole()).edit().addToSubjects(objectReference);
        if (form.getKind().equals("User"))
          roleBinding.addToUserNames(form.getName()).done();
        else
          roleBinding.addToGroupNames(form.getName()).done();
      } else {//退订
        roleBinding = oSClient.roleBindings().inNamespace(form.getProject()).withName(form.getRole())
                .edit().removeFromSubjects(objectReference);
        if (form.getKind().equals("User"))
          roleBinding.removeFromUserNames(form.getName()).done();
        else
          roleBinding.removeFromGroupNames(form.getName());
      }
      oSClient.close();
      apply.setApprover(request.getAttribute("current_user").toString());
      apply.setApprovalTime(new Date());
      apply.setAgree(true);
      applyService.save(apply);
    } else {
      data = ResponseData.badRequest();
    }
    return data;
  }

}
