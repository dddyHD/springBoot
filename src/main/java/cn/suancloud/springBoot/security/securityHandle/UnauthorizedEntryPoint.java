package cn.suancloud.springBoot.security.securityHandle;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * Created by admin on 2017/7/10.
 * 未登录的处理
 */
@Service
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint
{
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
          throws IOException, ServletException
  {
    PrintWriter writer = response.getWriter();
    writer.write("{\"status\":401,\"message\":\"unauthorized!\"}");
    writer.flush();
  }
}
