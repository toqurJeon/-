package com.ssafy.moiroomserver.oauth.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
@Slf4j
public class OauthController {

    @ResponseBody
    @GetMapping("/oauth/kakao")
    public String kakaoCallback(@RequestParam String code) {
        log.info("code: " + code);
        return "success";
    }

    public String getKakaoAccessToken(String code) {

        String accessToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에 필요한 파라미터를 전송하기
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=cfd8bf207f95e154c8f064581f71f655");
            sb.append("&redirect_uri=http://localhost:8080/auth/kakao");
            sb.append("&code=").append(code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드 200을 받아야 한다.
            int responseCode = conn.getResponseCode();
            log.info("responseCode : ", responseCode);

            // 요청을 통해 얻은 Json 타입의 데이터를 받아오기
            String result = getRequestResult(conn);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();

            // log.info
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return accessToken;
    }

    private String getRequestResult(HttpURLConnection conn) {
        return null;
    }
}
