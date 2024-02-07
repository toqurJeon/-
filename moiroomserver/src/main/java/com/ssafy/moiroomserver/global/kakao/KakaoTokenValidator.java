package com.ssafy.moiroomserver.global.kakao;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoTokenValidator {

    public boolean validateToken(String accessToken) {
        final String url = "https://kapi.kakao.com/v2/user/me";
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            System.out.println("진입");
            System.out.println("entity = " + entity);
            ResponseEntity<String> response
                    = template.exchange(url, HttpMethod.GET, entity, String.class);
            System.out.println("통과");
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}