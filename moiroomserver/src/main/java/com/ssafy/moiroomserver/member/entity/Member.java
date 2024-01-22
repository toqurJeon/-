package com.ssafy.moiroomserver.member.entity;

import com.ssafy.moiroomserver.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Member extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private Long metropolitanId;
    private Long cityId;

    private Long characterId; //외래키는 지정 안 한 상태

    private String name;

    private String imageUrl;

    private Integer gender; //0: 남자, 1: 여자

    private Integer roommateSearchStatus; // 0: 구하는 중, 1: 안 구하는 중

    private String introduction; // 자기 소개글

    private String nickname;

    private String socialAccessToken; // 카카오 소셜 accessToken(추후 수정할 수도 있다)

    private String access_token;

    private String refresh_token;

    private Integer account_status; // 0:탈퇴, 1:존재, 2:비활성, 3:정지
}