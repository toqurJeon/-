package com.ssafy.moiroomserver.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class OauthController {

    @ResponseBody
    @GetMapping("/oauth/kakao")
    public String kakaoCallback(@RequestParam String code) {
        log.info("code: " + code);
        return "success";
    }
}
