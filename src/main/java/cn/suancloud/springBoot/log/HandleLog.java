package cn.suancloud.springBoot.log;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import cn.suancloud.springBoot.model.Log;
import cn.suancloud.springBoot.service.LogService;

public class HandleLog {
  /**
   * 从request取出参数 logType 查找中文对应 规则： addXXX （添加XXX）、editXXX （修改XXX） 、 deleteXXX
   * （删除XXX）、applyXXX(申请XXX) allowXXX （通过XXX）.
   */
  public static String getMsg(String logType, String lastMsg) {
    String msg = "";
    switch (logType) {
      case "loginOpenshift":
        msg = "登录平台";
        break;
      case "logoutOpenshift":
        msg = "安全退出平台";
        break;
      case "editPassword":
        msg = "修改密码";
        break;
      case "addUser":
        msg = "添加用户";
        break;
      case "deleteUser":
        msg = "删除用户";
        break;
      case "editUser":
        msg = "修改用户";
        break;
    }
    if (StringUtils.isEmpty(msg))
      return null;
    return msg + lastMsg;
  }


  public static void saveLog(LogService logService, HttpServletRequest request, String lastMsg) {
    String logType = request.getParameter("logType");
    if (StringUtils.isEmpty(logType))
      return;
    String describe = getMsg(logType, lastMsg);
    if (StringUtils.isEmpty(describe))
      return;
    Log log = new Log();
    log.setUsername(request.getAttribute("current_user").toString());
    log.setUrl(request.getRequestURI());
    log.setMethod(request.getMethod());
    log.setDescribe(describe);
    logService.save(log);
  }

  public static void main(String[] args) {
  }
}
