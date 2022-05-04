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
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.ArrayList;
import java.util.List;

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

    // private final RedisTokenStore redisTokenStore;

    private final JwtTokenStore jwtTokenStore;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final JwtTokenEnhancer jwtTokenEnhancer;

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
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                // 将token存储到redis
                // .tokenStore(redisTokenStore);
                // 使用jwt
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain);
    }
}
