package com.ssafy.moiroomserver.global.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.moiroomserver.global.constants.ErrorCode;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService {

    private static final String INFO_URL = "https://kapi.kakao.com/v2/user/me";
    public boolean validateToken(String accessToken) {
        System.out.println("TEST 7 - 에러");
        RestTemplate template = new RestTemplate();
        System.out.println("TEST 8 - 에러");
        HttpHeaders headers = new HttpHeaders();
        System.out.println("TEST 9 - 에러");
        headers.set("Authorization", "Bearer " + accessToken);
        System.out.println("TEST 10 - 에러");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        System.out.println("TEST 11 - 에러");

        try {
            System.out.println("TEST 12 - 에러");
            ResponseEntity<String> response
                    = template.exchange(INFO_URL, HttpMethod.GET, entity, String.class);
            System.out.println("TEST 13 - 에러");
            System.out.println("response.getStatusCode().is2xxSuccessful() : " + response.getStatusCode().is2xxSuccessful());
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("TEST 14 - 에러");
            return false;
        }
    }

    public Long getInformation(String accessToken) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<String> response
                = template.exchange(INFO_URL, HttpMethod.GET, entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new NoExistException(ErrorCode.NOT_EXISTS_ACCESS_TOKEN);
        }

        try {
            return mapper.readTree(response.getBody()).path("id").asLong();
        } catch (JsonProcessingException e) {
            throw new NoExistException(ErrorCode.NOT_GET_SOCIAL_ID);
        }
    }
}