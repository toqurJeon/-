package com.ssafy.moiroomserver.oauth.controller;

import com.ssafy.moiroomserver.oauth.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    @ResponseBody
    @GetMapping("/oauth/kakao")
    public String kakaoCallback(@RequestParam String code) {
        log.info("code: " + code);
        String accessToken = oauthService.getKakaoAccessToken(code);

        // 카카오 엑세스 토큰을 바탕으로 회원 정보를 가져오기
        return "success";
    }
}
