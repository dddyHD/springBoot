package cn.suancloud.springBoot.security.securityHandle;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.suancloud.springBoot.util.ResponseData;

/**
 * Created by admin on 2018/5/8.
 */
@Service
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    PrintWriter writer = response.getWriter();
    ResponseData data = ResponseData.ok();
    writer.write(data.toJsonString());
    // TODO: 2018/7/19 注销openshift token
    writer.flush();
  }
}
