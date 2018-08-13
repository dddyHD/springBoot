package cn.suancloud.springBoot.controller.japi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import cn.suancloud.springBoot.service.ApplyService;
import cn.suancloud.springBoot.util.ResponseData;
import io.fabric8.kubernetes.api.model.ObjectReference;
import io.fabric8.openshift.client.OpenShiftClient;

import static cn.suancloud.springBoot.util.openshift.GetClient.getAdminOClient;
import static cn.suancloud.springBoot.util.openshift.GetClient.getOClient;

@RestController
@RequestMapping("/japi/v1/projects")
public class ProjectController {

  @Autowired
  ApplyService applyService;
  //所有项目
  @GetMapping("/all")
  public Object allProjectList(){
    OpenShiftClient oSClient = getAdminOClient();
    Object result = oSClient.projects().list();
    oSClient.close();
    return result;
  }

  //当前用户的的项目
  @GetMapping("/self")
  public Object selfProjectList(){
    OpenShiftClient oSClient = getOClient();
    Object result = oSClient.projects().list();
    oSClient.close();
    return result;
  }
  //当前用户正在申请的项目
  @GetMapping("/apply")
  public Object selfApplyProjectList(HttpServletRequest request){
    ResponseData data = ResponseData.ok();
    data.getData().put("applyingList",
            applyService.getApplying(request.getAttribute("current_user").toString()));
    return data;
  }

  /**
   *
   * @param project 申请的项目名
   * @param role 用户要添加的角色
   * @param kind 有User和Group两种
   * @param name 用户名
   * @return http status 200 和修改完成的 roleBindings
   */
  @PutMapping("/{project}/{role}/{kind}/{name}")
  public Object approve(@PathVariable String project,@PathVariable String role,
                        @PathVariable String kind,@PathVariable String name){
    //admin client get admin role
    OpenShiftClient oSClient = getAdminOClient();
    // add user to role
    ObjectReference objectReference = new ObjectReference();
    objectReference.setName(kind);
    objectReference.setName(name);
    // send out
    Object result = oSClient.roleBindings().inNamespace(project).withName(role)
            .edit().addToSubjects(objectReference);
    return result;
  }

}
