package cn.suancloud.springBoot.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static cn.suancloud.springBoot.util.Constant.JWT_SECRET;
import static cn.suancloud.springBoot.util.Constant.JWT_TTLMILLIS;


/**
 * Created by admin on 2018/5/9.
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;

  public JWTLoginFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }
  // 接收并解析用户凭证
  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,HttpServletResponse res)
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
      throw new RuntimeException(e);
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
    PrintWriter writer = res.getWriter();
    writer.write("{\"status\":200,\"J_Authorization\":\""+"Bearer " + token +"\"}");
    writer.flush();
  }

}
