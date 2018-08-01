package cn.suancloud.springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.suancloud.springBoot.service.LogService;
import cn.suancloud.springBoot.util.ResponseData;

@RestController
@RequestMapping("/log")
public class LogController extends BaseController {

  @Autowired
  LogService logService;

  @GetMapping("/{size}/{page}")
  public Object logList(@PathVariable int size, @PathVariable int page) {
    ResponseData data = ResponseData.ok();
    data.getData().put("log", logService.getPageLog(page, size));
    return data;
  }

  @DeleteMapping("/{id}")
  public Object deleteLog(@PathVariable Long id) {
    ResponseData data = ResponseData.ok();
    logService.delete(id);
    return data;
  }

}
