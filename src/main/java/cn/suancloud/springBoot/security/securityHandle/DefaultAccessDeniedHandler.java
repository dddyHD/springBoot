package cn.suancloud.springBoot.security.securityHandle;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

/**
 * Created by admin on 2017/7/14.
 * 用户权限不足无法访问资源处理
 */
@Service
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
          throws IOException, ServletException {
    PrintWriter writer = response.getWriter();
    response.sendError(SC_FORBIDDEN);
    writer.write("{\"status\":403,\"message\":\"Forbidden!\"}");
    writer.flush();
  }
}
