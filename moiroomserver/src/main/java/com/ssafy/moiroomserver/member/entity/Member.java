package com.ssafy.moiroomserver.member.entity;

import com.ssafy.moiroomserver.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "metropolitan_id")
    private Long metropolitanId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "character_id")
    private Long characterId; //외래키는 지정 안 한 상태

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "gender")
    private Integer gender; //0: 남자, 1: 여자

    @Column(name = "roommate_search_status", nullable = false)
    @ColumnDefault("1")
    private Integer roommateSearchStatus; // 0: 안 구하는 중, 1: 구하는 중

    @Column(name = "introduction")
    private String introduction; // 자기 소개글

    @Column(name = "nickname", nullable = false)
    @ColumnDefault("'사용자'")
    private String nickname;

    @Column(name = "social_access_token", nullable = false)
    private String socialAccessToken; // 카카오 소셜 accessToken(추후 수정할 수도 있다)

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "account_status", nullable = false)
    @ColumnDefault("1")
    private Integer accountStatus; // 0:탈퇴, 1:존재, 2:비활성, 3:정지
}