package com.ssafy.moiroomserver.oauth.service;

import com.ssafy.moiroomserver.oauth.entity.KakaoMember;
import com.ssafy.moiroomserver.oauth.repository.OAuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class OAuthService extends DefaultOAuth2UserService {

    @Autowired
    OAuthRepository oAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("OAuthService.loadUser");
        // email, oauthType 호출
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
        log.info("ATTR INFO : {}", attributes.toString());

        String email = null;
        String kakaoId = null;
        String oauthType = userRequest.getClientRegistration().getRegistrationId();

        OAuth2User user2 = super.loadUser(userRequest);

        // oauth 타입에 따라 데이터가 다르기에 분기
        if("kakao".equals(oauthType.toLowerCase())) {
            // kakao는 kakao_account 내에 email이 존재함.
            kakaoId = attributes.get("id").toString();
            email = ((Map<String, Object>) attributes.get("kakao_account")).get("email").toString();
        }
        else if("google".equals(oauthType.toLowerCase())) {
            email = attributes.get("email").toString();
        }
        else if("naver".equals(oauthType.toLowerCase())) {
            // naver는 response 내에 email이 존재함.
            email = ((Map<String, Object>) attributes.get("response")).get("email").toString();
        }

        // User 존재여부 확인 및 없으면 생성
        if(getKakaoMemberByKakaoId(kakaoId) == null){
            log.info("kakao({}) NOT EXISTS. REGISTER", kakaoId, oauthType);
            KakaoMember member = new KakaoMember();
            member.setKakaoMemberId(UUID.randomUUID().toString()); // 수정해야 할 듯
            member.setKakaoId(kakaoId);
            member.setEmail(email);
            member.setOauthType(oauthType);

            save(member);
        }
        return super.loadUser(userRequest);
    }

    // 저장, 조회만 수행. 기타 예외처리 및 다양한 로직은 연습용이므로
    public void save(KakaoMember kakaoMember) {
        oAuthRepository.save(kakaoMember);
    }

    public KakaoMember getKakaoMemberByKakaoId(String kakaoId) {
        return oAuthRepository.findByKakaoId(kakaoId).orElse(null);
    }

    public KakaoMember getKakaoMemberByEmailAndOAuthType(String email, String oauthType) {
        return oAuthRepository.findByEmailAndOauthType(email, oauthType).orElse(null);
    }
}
