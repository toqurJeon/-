package com.ssafy.moiroomserver.oauth.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class KakaoProfile {

    private String nickname; // 카카오에서 쓰고 있는 닉네임

    private String thumbnailImageUrl; // 프로필 미리보기 이미지 URL

    private String profileImageUrl; // 카카오 프로필 이미지 URL

}
