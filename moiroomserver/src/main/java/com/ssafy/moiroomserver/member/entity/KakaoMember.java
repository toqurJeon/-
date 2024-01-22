package com.ssafy.moiroomserver.member.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class KakaoMember {

    private Long id;
    private String nickname;
    private String profileImgUrl;
    private String email;
    private String name;
    private String gender;
    private String ageRange;
    private String birthday;
    private String birthyear;
    private String phoneNumber;

}