package cn.suancloud.springBoot.security.securityHandle;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.suancloud.springBoot.model.User;

import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * Created by admin on 2018/5/8.
 */
@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    PrintWriter writer = response.getWriter();
    writer.write("{\"status\":200,\"message\":\"Login Success !\"}");
    writer.flush();
    writer.close();
  }
}
