package com.cheng.jjwtdemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class JjwtdemoApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(Instant.now().toEpochMilli());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void testCreateToken(){
        Instant.now().getEpochSecond();
        // 创建JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder()
                // 声明标识{"jti","8888"}
                .setId("8888")
                // 主体，用户 {"sub","Rose"}
                .setSubject("Rose")
                // 创建日期 {"ita","xxxx"}
                .setIssuedAt(new Date())
                // 过期时间 1分钟
                .setExpiration(new Date(System.currentTimeMillis() + 60*1000))
                .signWith(SignatureAlgorithm.HS256,"xxxxx");

        // 获取token
        String token = jwtBuilder.compact();
        System.out.println(token);

        System.out.println("======================");

        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));

        System.out.println(new String(Base64Utils.decodeFromString(split[1])));
    }


    /**
     * 解析token
     */
    @Test
    public void testParseTokenHasExp(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTY0OTY5NjE4MiwiZXhwIjoxNjQ5Njk2MjQyfQ.Cou59qe2lMIPhyft-BVNbgg75NMpR0tRb0GceAvNM5U";
        // 解析token获取负载中声明的对象
        Claims claims = Jwts.parser()
                .setSigningKey("xxxxx")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id"+claims.getId());
        System.out.println("subject"+claims.getSubject());
        System.out.println("issuedAt"+claims.getIssuedAt());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间"+sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间"+sdf.format(claims.getExpiration()));
        System.out.println("当前时间"+sdf.format(new Date()));
    }

    /**
     * 创建token（自定义声明）
     */
    @Test
    public void testCreatTokenByClaim(){
        Map<String,Object> map = new HashMap<>();

        Instant.now().getEpochSecond();
        // 创建JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder()
                // 声明标识{"jti","8888"}
                .setId("8888")
                // 主体，用户 {"sub","Rose"}
                .setSubject("Rose")
                // 创建日期 {"ita","xxxx"}
                .setIssuedAt(new Date())
                // 过期时间 1分钟
                .setExpiration(new Date(System.currentTimeMillis() + 60*1000))
                .signWith(SignatureAlgorithm.HS256,"xxxxx")
                // 自定义声明
                .claim("roles","admin")
                .claim("logo","xxx.png");
                // 直接传入map
//                .addClaims(map);

        // 获取token
        String token = jwtBuilder.compact();
        System.out.println(token);

        System.out.println("======================");

        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));

        System.out.println(new String(Base64Utils.decodeFromString(split[1])));
    }

    /**
     * 解析token(自定义声明)
     */
    @Test
    public void testParseTokenByClaim(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTY1MDIxMDk1NCwiZXhwIjoxNjUwMjExMDE0LCJyb2xlcyI6ImFkbWluIiwibG9nbyI6Inh4eC5wbmcifQ.5hfDd7AyQWJB6QlKkz8BzrcjvX6YZSZql40rRU9nDss";
        // 解析token获取负载中声明的对象
        Claims claims = Jwts.parser()
                .setSigningKey("xxxxx")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id"+claims.getId());
        System.out.println("subject"+claims.getSubject());
        System.out.println("issuedAt"+claims.getIssuedAt());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间"+sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间"+sdf.format(claims.getExpiration()));
        System.out.println("当前时间"+sdf.format(new Date()));
        System.out.println("roles:"+claims.get("roles"));
        System.out.println("logo:"+claims.get("logo"));

    }
}
