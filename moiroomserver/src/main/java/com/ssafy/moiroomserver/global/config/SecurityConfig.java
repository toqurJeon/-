package com.ssafy.moiroomserver.global.config;

import com.ssafy.moiroomserver.member.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    OAuthLoginSuccessHandler oAuthLoginSuccessHandler;

    @Autowired
    OAuthLoginFailureHandler oAuthLoginFailureHandler;

    @Autowired
    OAuthService oAuthService;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("필터 체인 진입");

        http
                .cors(CorsConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .rememberMe(RememberMeConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login/**").permitAll()
                        .anyRequest().authenticated()
                )
                // oauth 로그인 설정
                .oauth2Login(oAuth -> oAuth
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuthService)
                        )
                        .successHandler(oAuthLoginSuccessHandler)
                        .failureHandler(oAuthLoginFailureHandler)
                );
        return http.build();
    }

}