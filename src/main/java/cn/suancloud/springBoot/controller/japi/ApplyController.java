package cn.suancloud.springBoot.controller.japi;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cn.suancloud.springBoot.controller.BaseController;
import cn.suancloud.springBoot.exception.FormException;
import cn.suancloud.springBoot.formvalid.ApplyForm;
import cn.suancloud.springBoot.model.Apply;
import cn.suancloud.springBoot.service.ApplyService;
import cn.suancloud.springBoot.util.ResponseData;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.openshift.client.OpenShiftClient;

import static cn.suancloud.springBoot.util.openshift.GetClient.getAdminOClient;
import static cn.suancloud.springBoot.util.openshift.GetClient.getOClient;

@RestController
@RequestMapping("/japi/v1/apply")
public class ApplyController extends BaseController {
  @Autowired
  ApplyService applyService;

  @GetMapping
  public ResponseData applyList() {
    ResponseData data = ResponseData.ok();
    data.getData().put("applyList", applyService.findAll());
    return data;
  }
  //当前用户正在申请的项目
  @GetMapping("/self")
  public Object selfApplyProjectList(HttpServletRequest request) {
    ResponseData data = ResponseData.ok();
    data.getData().put("applyingList",
            applyService.getApplying(request.getAttribute("current_user").toString()));
    return data;
  }

  @PostMapping
  public ResponseData addApply(@Valid @RequestBody ApplyForm form, BindingResult result,
                               HttpServletRequest request) throws FormException {
    ResponseData data = ResponseData.ok();
    hasErrors(result);
    form.setApplicant(request.getAttribute("current_user").toString());
    String type = form.getType();
    OpenShiftClient adminClient = getAdminOClient();
    //验证项目是否存在
    try {
      if (adminClient.namespaces().withName(form.getProject()).get() == null) {
        data = ResponseData.projectNotExist();
        return data;
      }
    } catch (KubernetesClientException e) {
    } finally {
      adminClient.close();
    }
    OpenShiftClient oSClient = getOClient(request.getAttribute("current_os_token").toString());
    if (type.equals("dy")) {//订阅操作
      try {
        //判断当前用户是否拥有该项目,没拥有抛出异常
        oSClient.namespaces().withName(form.getProject()).get();
        data = ResponseData.projectAlreadyOwn();
      } catch (KubernetesClientException e) {
        //判断申请是否已经存在
        data = saveApply(form, data);
      } finally {
        oSClient.close();
      }
    } else if (type.equals("td")) {//退订操作
      try {
        oSClient.namespaces().withName(form.getProject()).get();
        data = saveApply(form, data);

      } catch (KubernetesClientException e) {
        //没有项目的权限
        data = ResponseData.projectNotOwn();
      } finally {
        oSClient.close();
      }
    } else {
      oSClient.close();
      data = ResponseData.badRequest();
    }
    return data;
  }

  @PatchMapping("/{id}")
  public ResponseData cancelApply(@PathVariable Long id, HttpServletRequest request) {
    ResponseData data = ResponseData.ok();
    String applicant = request.getAttribute("current_user").toString();
    Apply apply = applyService.findOne(id);
    //属于自己的申请且还未审批的
    if (applicant.equals(apply.getApplicant())&&apply.getAgree()==null){
      apply.setStatus(false);
      applyService.save(apply);
    }else {
      data = ResponseData.badRequest();
    }
    return data;
  }


  private ResponseData saveApply(ApplyForm form, ResponseData data) {
    if (applyService.isApplying(form.getApplicant(), form.getProject())) {
      data = ResponseData.alreadyApply();
    } else {
      Apply apply = new Apply();
      BeanUtils.copyProperties(form, apply);
      applyService.save(apply);
    }
    return data;
  }
}
