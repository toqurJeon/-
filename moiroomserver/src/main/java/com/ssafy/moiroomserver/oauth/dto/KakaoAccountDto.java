package com.ssafy.moiroomserver.oauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoAccountDto {

    private KakaoProfile kakaoProfile; // 카카오 프로필 추가 정보
    private String email; // 카카오에서 사용할 이메일
    private String kakaoId; // 카카오 고유 id
    private String name; // 카카오 회원 이름
    private String ageRange; // 카카오 정보에 담긴 회원의 연령대
    private String birthyear; // 카카오 회원의 출생년도
    private String birthday; // 카카오 회원의 생일
    private String gender; // 카카오 회원의 성별
    private String phoneNumber; // 카카오 회원의 전화번호
}
