package com.ssafy.moiroomserver.member.entity;

import com.ssafy.moiroomserver.global.entity.BaseEntity;
import com.ssafy.moiroomserver.member.dto.MemberInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "member",
uniqueConstraints = @UniqueConstraint(
        name = "social_id",
        columnNames = {"social_id"}
))
public class Member extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "social_id", nullable = false, unique = true)
    private Long socialId;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "metropolitan_id")
    private Long metropolitanId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "character_id")
    private Long characterId; //외래키는 지정 안 한 상태

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profile_image_url", nullable = false)
    private String imageUrl;

    @Column(name = "birthyear", nullable = false)
    private String birthyear;

    @Column(name = "birthday", nullable = false)
    private String birthday;

    @Column(name = "gender", nullable = false)
    private String gender; //0: 남자, 1: 여자

    @Column(name = "roommate_search_status", nullable = false)
    @ColumnDefault("1")
    private int roommateSearchStatus; // 0: 안 구하는 중, 1: 구하는 중

    @Column(name = "introduction")
    private String introduction; // 자기 소개글

    @Column(name = "nickname", nullable = false)
    @ColumnDefault("'사용자'")
    private String nickname;

    @Column(name = "access_token", nullable = false) // 카카오 accessToken을 저장
    private String accessToken;

    @Column(name = "refresh_token", nullable = false) // 카카오에서 발급 받은 refreshToken
    private String refreshToken;

    @Column(name = "login_status", nullable = false)
    private int loginStatus = 1; // 0: 로그아웃, 1: 로그인

    @Column(name = "account_status", nullable = false)
    private int accountStatus = 1; // 0:탈퇴, 1:존재, 2:비활성, 3:정지

    public void modify(MemberInfo.ModifyRequest infoModifyRequest) {
        this.metropolitanId = infoModifyRequest.getMetropolitanId();
        this.cityId = infoModifyRequest.getCityId();
        this.introduction = infoModifyRequest.getMemberIntroduction();
        this.nickname = infoModifyRequest.getMemberNickname();
    }
}