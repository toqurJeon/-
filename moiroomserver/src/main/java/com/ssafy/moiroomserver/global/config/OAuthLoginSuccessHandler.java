package com.ssafy.moiroomserver.global.config;

import com.ssafy.moiroomserver.member.entity.KakaoMember;
import com.ssafy.moiroomserver.member.service.OAuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuthService oauthService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String email = null;
        String oauthType = token.getAuthorizedClientRegistrationId();

        // oauth 타입에 따른 분기점
        if ("kakao".equals(oauthType.toLowerCase())) {
            // kakao는 kakao_account 내에 email이 존재함.
            email = ((Map<String, Object>) token.getPrincipal().getAttribute("kakao_account")).get("email").toString();
        } else if ("google".equals(oauthType.toLowerCase())) {
            email = token.getPrincipal().getAttribute("email").toString();
        } else if ("naver".equals(oauthType.toLowerCase())) {
            email = ((Map<String, Object>) token.getPrincipal().getAttribute("response")).get("email").toString();
        }

        log.info("LOGIN SUCCESS : {} FROM {}", email, oauthType);
        KakaoMember kakaoMember = oauthService.getKakaoMemberByEmailAndOAuthType(email, oauthType);

        // 세션에 user 저장
        log.info("KAKAOMEMBER SAVED IN SESSION");
        HttpSession session = request.getSession();
        session.setAttribute("kakaoMember", kakaoMember);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
