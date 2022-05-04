package com.cheng.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@EnableOAuth2Sso
@SpringBootApplication
public class Oauth2Client01DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2Client01DemoApplication.class);
    }
}
