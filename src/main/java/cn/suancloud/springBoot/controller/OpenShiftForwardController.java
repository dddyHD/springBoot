package cn.suancloud.springBoot.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static cn.suancloud.springBoot.util.HttpUtil.*;

@RestController
public class OpenShiftForwardController extends BaseController {
  @GetMapping("/oapi/**")
  public Object oapi_get(HttpServletRequest request, HttpServletResponse response){
    return sendGet(request,response);
  }

  @PostMapping("/oapi/**")
  public Object oapi_post(HttpServletRequest request, HttpServletResponse response){
    return sendPost(request,response);
  }

  @PutMapping("/oapi/**")
  public Object oapi_put(HttpServletRequest request, HttpServletResponse response){
    return sendPut(request,response);
  }
  @DeleteMapping("/oapi/**")
  public Object oapi_delete(HttpServletRequest request, HttpServletResponse response){
    return sendDelete(request,response);
  }

  @PatchMapping("/oapi/**")
  public Object oapi_patch(HttpServletRequest request, HttpServletResponse response){
    return sendPatch(request,response);
  }
}
