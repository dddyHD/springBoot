package cn.suancloud.springBoot.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.suancloud.springBoot.model.User;
import cn.suancloud.springBoot.util.ResponseData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static cn.suancloud.springBoot.util.Constant.JWT_SECRET;
import static cn.suancloud.springBoot.util.Constant.JWT_TTLMILLIS;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;


/**
 * Created by admin on 2018/5/9.
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
  protected static Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);
  private AuthenticationManager authenticationManager;

  public JWTLoginFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  // 接收并解析用户凭证
  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
          throws AuthenticationException {
    try {
      User user = new ObjectMapper()
              .readValue(req.getInputStream(), User.class);

      return authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      user.getUsername(),
                      user.getPassword(),
                      new ArrayList<>())
      );
    } catch (IOException e) {
      logger.info("用户登录请求的参数错误");
      UserAndPasswordError(req, res);
      return null;
    }
  }

  // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException, ServletException {

    String token = Jwts.builder()
            .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TTLMILLIS))
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
    res.addHeader("J_Authorization", "Bearer " + token);
//    返回到body中
    logger.info("获取token值");
    PrintWriter writer = res.getWriter();
    ResponseData data = ResponseData.ok();
    data.getData().put("J_Authorization", "Bearer " + token);
    writer.write(data.toJsonString());
    writer.flush();
  }

  // 用户登录失败 用户名或者密码误
  protected void unsuccessfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            AuthenticationException failed) {
    logger.info("用户名或者密码错误");
    UserAndPasswordError(req, res);
  }

  private void UserAndPasswordError(HttpServletRequest req, HttpServletResponse res) {
    PrintWriter writer = null;
    try {
      writer = res.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    ResponseData data = ResponseData.badRequest();
    res.setStatus(SC_BAD_REQUEST);
    writer.write(data.toJsonString());
    writer.flush();
  }

}
