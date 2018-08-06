package cn.suancloud.springBoot.controller;

import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.suancloud.springBoot.model.Role;
import cn.suancloud.springBoot.model.User;
import cn.suancloud.springBoot.model.openshift.OSResponseData;
import cn.suancloud.springBoot.service.LogService;
import cn.suancloud.springBoot.service.RoleService;
import cn.suancloud.springBoot.service.UserService;

import static cn.suancloud.springBoot.log.HandleLog.saveLog;
import static cn.suancloud.springBoot.util.HttpUtil.getOpenShiftToken;
import static cn.suancloud.springBoot.util.HttpUtil.sendDelete;
import static cn.suancloud.springBoot.util.HttpUtil.sendGet;
import static cn.suancloud.springBoot.util.HttpUtil.sendPatch;
import static cn.suancloud.springBoot.util.HttpUtil.sendPost;
import static cn.suancloud.springBoot.util.HttpUtil.sendPut;

/**
 * openshift 请求转发器 openshif 用户模块与java用户模块数据统一
 */
@RestController
public class OpenShiftForwardController extends BaseController {
  protected static Logger logger = LoggerFactory.getLogger(OpenShiftForwardController.class);

  @Autowired
  UserService userService;
  @Autowired
  RoleService roleService;
  @Autowired
  LogService logService;

  @GetMapping("/oapi/**")
  public Object oapi_get(HttpServletRequest request, HttpServletResponse response) {
    return sendGet(request, response);
  }

  @PostMapping("/oapi/**")
  public Object oapi_post(HttpServletRequest request, HttpServletResponse response) {
    return sendPost(request, response);
  }

  @PutMapping("/oapi/**")
  public Object oapi_put(HttpServletRequest request, HttpServletResponse response) {
    return sendPut(request, response);
  }

  @DeleteMapping("/oapi/**")
  public Object oapi_delete(HttpServletRequest request, HttpServletResponse response) {
    return sendDelete(request, response);
  }

  @PatchMapping("/oapi/**")
  public Object oapi_patch(HttpServletRequest request, HttpServletResponse response) {
    return sendPatch(request, response);
  }

  @PostMapping("/openshift_login")
  public Object get_openshift_token(HttpServletRequest request, HttpServletResponse response) {
    saveLog(logService, request, "");
    return getOpenShiftToken(request, response);
  }

  @DeleteMapping("/oapi/v1/oauthaccesstokens/{token}")
  public Object openshift_logout(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable String token) {
    saveLog(logService, request, "");
    return sendDelete(request, response);
  }

  /**
   * 用户处理 GET PUT PATCH DELETE
   */
  @RequestMapping("/oapi/v1/users/{username}")
  public Object operationUsers(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable("username") String username) {
    String method = request.getMethod();
    Object result = "";
    if (method.equals("GET")) {
      result = sendGet(request, response);
    } else if (method.equals("PUT")) {
      result = sendPut(request, response);
      if (response.getStatus() < 400) {
        OSResponseData openshift_user = (OSResponseData) JSONObject.parseObject(result.toString(), OSResponseData.class);
        User user = userService.getUser(openshift_user.getMetadata().getName());
        user.setNickname(openshift_user.getFullName());
        user.setPassword(openshift_user.getPassword());
        userService.save(user);
        saveLog(logService, request, user.getUsername());
        logger.info("修改用户信息!");
      }
    } else if (method.equals("PATCH")) {
      result = sendPatch(request, response);
    } else if (method.equals("DELETE")) {
      result = sendDelete(request, response);
      if (response.getStatus() < 400) {
        User user = userService.getUser(username);
        userService.delete(user.getId());
        saveLog(logService, request, user.getUsername());
        logger.info("删除用户:" + username);
      }
    }
    return result;
  }

  /**
   * 用户处理 POST 添加用户
   */
  @PostMapping("/oapi/v1/users")
  public Object addUser(HttpServletRequest request, HttpServletResponse response) {
    //添加用户
    Object result = sendPost(request, response);
    if (response.getStatus() < 400) {
      OSResponseData openshift_user = (OSResponseData) JSONObject.parseObject(result.toString(), OSResponseData.class);
      User user = new User();
      List<Role> roles = new ArrayList<>();
      roles.add(roleService.getRole(request.getParameter("role")));
      user.setUsername(openshift_user.getMetadata().getName());
      user.setPassword(openshift_user.getPassword());
      user.setNickname(openshift_user.getFullName());
      user.setRoles(roles);
      userService.save(user);
      saveLog(logService, request, user.getUsername());
      logger.info("新增用户" + user.getUsername());
    }
    return result;
  }

}
