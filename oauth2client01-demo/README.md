# oauth2单点登录客户端01

## 一、引入依赖
> 具体看pom.xml

## 二、配置文件
```yaml
server:
  port: 8081
  servlet:
    session:
      cookie:
        # 防止cookie冲突导致登录验证不通过
        name: OAUTH-CLIENT-SESSION01

#与授权服务器对应的配置
oauth2-server-url: localhost:8080
security:
  oauth2:
    client:
      client-id: admin
      client-secret: 112233
      user-authorization-uri: ${oauth2-server-url}/oauth/authorize
      access-token-uri: ${oauth2-serve-url}/oauth/token
    resource:
      jwt:
        key-uri: ${oauth2-serve-url}/oauth/token_key
```

## 三、开启单点登录
> 启动类开启注解@EnableOAuth2Sso


## 四、测试
分别启动资源服务器和客户端应用

浏览器访问客户端接口
> localhost:8081/user/getCurrentUser

这时会自动跳转到资源服务器的登录页面，
登录成功后会跳转回客户端访问的接口，这说明单点登录成功了。
