package com.cheng.demo.config;

import com.cheng.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 配置client-id
                .withClient("admin")
                // 配置client-secret
                .secret(passwordEncoder.encode("112233"))
                // 配置访问token的有效期
                // .accessTokenValiditySeconds(3000)
                // 配置redirect-uri,用于授权成功后跳转
                .redirectUris("http://www.baidu.com")
                // 配置申请的权限范围
                .scopes("all")
                // 授权码模式
                // .authorizedGrantTypes("authorization_code");
                // 密码模式
                .authorizedGrantTypes("password");
    }

    /**
     * 密码模式配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService);
    }
}