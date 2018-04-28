package cn.suancloud.springBoot.security.securityHandle;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.suancloud.springBoot.util.ResponseData;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * Created by admin on 2017/6/20.
 */
@Service
public class LoginFailureHandler implements AuthenticationFailureHandler {
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    PrintWriter writer = response.getWriter();
    response.sendError(SC_BAD_REQUEST);
    writer.write("{\"status\":400,\"message\":\"Username or Password error!\"}");
    writer.flush();
  }
}
