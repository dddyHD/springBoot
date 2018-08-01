package cn.suancloud.springBoot.security.securityHandle;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.suancloud.springBoot.util.ResponseData;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

/**
 * Created by admin on 2017/7/14.
 * 用户权限不足无法访问资源处理 403
 */
@Service
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
          throws IOException {
    PrintWriter writer = response.getWriter();
    ResponseData data = ResponseData.forbidden();
    response.sendError(SC_FORBIDDEN);
    writer.write(data.toJsonString());
    writer.flush();
  }
}
