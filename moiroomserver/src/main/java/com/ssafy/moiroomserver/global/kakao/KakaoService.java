package com.ssafy.moiroomserver.global.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.moiroomserver.global.constants.ErrorCode;
import com.ssafy.moiroomserver.global.exception.NoExistException;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.ssafy.moiroomserver.global.constants.ErrorCode.NOT_EXISTS_ACCESS_TOKEN;
import static com.ssafy.moiroomserver.global.constants.ErrorCode.NOT_EXISTS_MEMBER;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private static final String INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final MemberRepository memberRepository;

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

    /**
     * HttpServletRequest를 이용해 로그인 사용자 정보 추출
     * @param request
     * @return 로그인 사용자 정보
     */
    public Member getMemberByHttpServletRequest(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            throw new NoExistException(NOT_EXISTS_ACCESS_TOKEN);
        }
        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.substring(7, authorization.length());
        Long socialId = getInformation(accessToken);
        Member member = memberRepository.findMemberBySocialIdAndProvider(socialId, "kakao");
        if (member == null) {
            throw new NoExistException(NOT_EXISTS_MEMBER);
        }
        return member;
    }

    private boolean validateAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization") != null;
    }
}