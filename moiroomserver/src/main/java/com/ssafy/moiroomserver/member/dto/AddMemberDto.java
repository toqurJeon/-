package com.ssafy.moiroomserver.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberDto {

    private String nickname;
    private String imageUrl;
    private String birthyear;
    private String birthday;
    private String name;
    private String gender;
    private String accessToken; // 카카오 accessToken
    private String refreshToken; // 카카오 refreshToken
}
