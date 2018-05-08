package cn.suancloud.springBoot.util;

import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import cn.suancloud.springBoot.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by admin on 2018/5/4.
 */
public class JwtUtil {
  //密钥
  @Value("jwt.secret")
  private static String SECRET;
  //多久过期（毫秒）
  @Value("jwt.ttlmillis")
  private static long TTLMillis;

  public static String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, "HS512")
            .compact();
  }

  public static String generateToken(User user) {
    //token过期时间
    Date expDate = new Date(System.currentTimeMillis() + TTLMillis);
    Claims claims = Jwts.claims().setSubject(user.getUsername());
    claims.put("id",user.getId());
    // claims.put("")


    return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, "HS512")
            .compact();
  }

  public static Claims getClaimsFromToken(String token) {
    Claims claims;
    try {
      claims = Jwts.parser()
              .setSigningKey("HS512")
              .parseClaimsJws(token)
              .getBody();
    } catch (Exception e) {
      claims = null;
    }
    return claims;
  }

  public static void main(String[] args) {
    Map map = new HashMap();
    map.put("username", "gin");
    System.out.println(generateToken(map));

    System.out.println(getClaimsFromToken("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MjU4MzAzNjAsInVzZXJuYW1lIjoiZ2luIn0.VCz7OBP10aA2IXmn2Uiqqh2CqWUJBLaU7_0Ix4Azz_sd1C3ZNcRqV4-hlXyYxxEfS-eWSPPyc8iO3Tai_6H9-Q"));

  }


}
