package com.ssafy.moiroomserver.oauth.controller;

import com.ssafy.moiroomserver.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthService oAuthService;

    /**
     * 로그인 페이지
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 메인 페이지
     * @return
     */
    @GetMapping("/")
    public String main(OAuth2AuthenticationToken authenticationToken) {
        // OAuth2 로그인 사용자 정보 획득
        OAuth2User principal = authenticationToken.getPrincipal();
        String registrationId = authenticationToken.getAuthorizedClientRegistrationId();
        String userName = principal.getName(); // 혹은 필요에 따라 다른 식별자 사용

        //accessToken 추출
        String accessToken = oAuthService.getAccessToken(registrationId, userName);

        // refreshToken 추출
        String refreshToken = oAuthService.getRefreshToken(registrationId, userName);

        //accessToken 로깅
        System.out.println("accessToken = " + accessToken);

        // refreshToken 로깅
        System.out.println("refreshToken = " + refreshToken);

        return "main";
    }
}
