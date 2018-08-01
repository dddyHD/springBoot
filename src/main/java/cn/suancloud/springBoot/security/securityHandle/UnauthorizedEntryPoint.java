package cn.suancloud.springBoot.security.securityHandle;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.suancloud.springBoot.util.ResponseData;

import static org.apache.commons.httpclient.HttpStatus.SC_UNAUTHORIZED;

/**
 * Created by admin on 2017/7/10. 未登录的处理 401
 */
@Service
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
          throws IOException {
    ResponseData data = ResponseData.unauthorized();
    PrintWriter writer = response.getWriter();
    response.setStatus(SC_UNAUTHORIZED);
    writer.write(data.toJsonString());
    writer.flush();
  }
}
