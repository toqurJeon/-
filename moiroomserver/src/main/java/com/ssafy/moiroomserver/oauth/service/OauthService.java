package com.ssafy.moiroomserver.oauth.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class OauthService {

    public String getKakaoAccessToken(String code) {

        String accessToken = null;
        String requestURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder input = new StringBuilder();
            input.append("grant_type=authorization_code")
                    .append("&client_id=").append("cfd8bf207f95e154c8f064581f71f655")
                    .append("&redirect_uri=").append("http://127.0.0.1:8080/oauth/kakao")
                    .append("&code=").append(code);
            bw.write(input.toString());
            bw.flush();

            Integer responseCode = conn.getResponseCode();
            log.info("responseCode: {}", responseCode); // 응답 코드 200인지 확인하기

            // 요청 데이터 얻어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            StringBuilder output = new StringBuilder();

            while ((line = br.readLine()) != null) {
                output.append(line);
            }

            // output에 refreshToken, accessToken 등의 정보가 존재하고 있는 상태.
            JsonElement element = JsonParser.parseString(output.toString());
            accessToken = element.getAsJsonObject().get("access_token").getAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return accessToken;
    }
}
