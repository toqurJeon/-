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
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response
                    = template.exchange(INFO_URL, HttpMethod.GET, entity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
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