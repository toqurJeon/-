package com.ssafy.moiroomserver.member.dto;

import lombok.Getter;

@Getter
public class MemberTokenDto {
    private String accessToken;
    private String refreshToken;
}
