package com.ssafy.moiroomserver.oauth.service;

import com.ssafy.moiroomserver.oauth.dto.KakaoAccountDto;
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

        String oauthType = userRequest.getClientRegistration().getRegistrationId();

        // oauth 타입에 따라 데이터가 다르기에 분기
        if("kakao".equals(oauthType.toLowerCase())) {
            KakaoAccountDto dto = new KakaoAccountDto();
            setKakaoAccount(dto, attributes);

            if (getKakaoMemberByKakaoId(dto.getKakaoId()) == null) {
                joinKakaoMember(oauthType, dto);
            }
        }
        else if("google".equals(oauthType.toLowerCase())) {
            // 구글 소셜 로그인 영역
        }
        else if("naver".equals(oauthType.toLowerCase())) {
            // 네이버 소셜 로그인 영역
        }

        return super.loadUser(userRequest);
    }

    private void joinKakaoMember(String oauthType, KakaoAccountDto dto) {
        log.info("kakao({}) NOT EXISTS. REGISTER", dto.getKakaoId(), oauthType);
        KakaoMember member = new KakaoMember();
        member.setKakaoMemberId(UUID.randomUUID().toString()); // 수정해야 할 듯
        member.setKakaoId(dto.getKakaoId());
        member.setEmail(dto.getEmail());
        member.setName(dto.getName());
        member.setAgeRange(dto.getAgeRange());
        member.setBirthyear(dto.getBirthyear());
        member.setBirthday(dto.getBirthday());
        member.setGender(dto.getGender());
        member.setPhoneNumber(dto.getPhoneNumber());
        member.setOauthType(oauthType);

        save(member);
    }

    private static void setKakaoAccount(KakaoAccountDto dto, Map<String, Object> attributes) {
        dto.setKakaoId(attributes.get("id").toString());
        dto.setEmail(((Map<String, Object>) attributes.get("kakao_account")).get("email").toString());
        dto.setName(((Map<String, Object>) attributes.get("kakao_account")).get("name").toString());
        dto.setAgeRange(((Map<String, Object>) attributes.get("kakao_account")).get("age_range").toString());
        dto.setBirthyear(((Map<String, Object>) attributes.get("kakao_account")).get("birthyear").toString());
        dto.setBirthday(((Map<String, Object>) attributes.get("kakao_account")).get("birthday").toString());
        dto.setGender(((Map<String, Object>) attributes.get("kakao_account")).get("gender").toString());
        dto.setPhoneNumber(((Map<String, Object>) attributes.get("kakao_account")).get("phone_number").toString());
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
