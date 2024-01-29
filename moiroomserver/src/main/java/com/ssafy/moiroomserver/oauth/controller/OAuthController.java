package com.ssafy.moiroomserver.oauth.controller;

import com.ssafy.moiroomserver.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthService oauthService;

//    @ResponseBody
//    @GetMapping("/oauth/kakao")
//    public String kakaoLogin(@RequestParam String code) {
//        log.info("code: " + code);
//        String accessToken = oauthService.getKakaoAccessToken(code);
//
//        // 카카오 엑세스 토큰을 바탕으로 회원 정보를 가져오기
//        return "success";
//    }

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
    public String main() {
        return "main";
    }
}
