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

    @Column(name = "profile_image_url")
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

    @Column(name = "access_token") // 카카오 accessToken을 저장
    private String accessToken;

    @Column(name = "refresh_token") // 카카오에서 발급 받은 refreshToken
    private String refreshToken;

    @Column(name = "account_status", nullable = false)
    @ColumnDefault("1")
    private Integer accountStatus; // 0:탈퇴, 1:존재, 2:비활성, 3:정지

    public void modify(MemberInfo.ModifyRequest infoModifyRequest) {
        this.metropolitanId = infoModifyRequest.getMetropolitanId();
        this.cityId = infoModifyRequest.getCityId();
        this.introduction = infoModifyRequest.getMemberIntroduction();
        this.nickname = infoModifyRequest.getMemberNickname();
    }
}