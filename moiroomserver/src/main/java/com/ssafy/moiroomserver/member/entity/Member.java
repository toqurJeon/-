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

    @Column(name = "social_id", nullable = false)
    private Long socialId;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "metropolitan_id")
    private Long metropolitanId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "characteristic_id")
    private Long characteristicId; //외래키는 지정 안 한 상태

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

    /**
     * 회원 정보 수정
     * @param infoModifyRequest 수정 시 입력할 회원 정보
     */
    public void modifyMemberInfo(MemberInfo.ModifyRequest infoModifyRequest) {
        this.imageUrl = (infoModifyRequest.getProfileImageUrl() == null) ? imageUrl : infoModifyRequest.getProfileImageUrl();
        this.metropolitanId = (infoModifyRequest.getMetropolitanId() == null) ? metropolitanId : infoModifyRequest.getMetropolitanId();
        this.cityId = (infoModifyRequest.getCityId() == null) ? cityId : infoModifyRequest.getCityId();
        this.nickname = (infoModifyRequest.getMemberNickname() == null) ? nickname : infoModifyRequest.getMemberNickname();
        this.introduction = (infoModifyRequest.getMemberIntroduction() == null) ? introduction : infoModifyRequest.getMemberIntroduction();
        this.roommateSearchStatus = (infoModifyRequest.getRoommateSearchStatus() == null) ? roommateSearchStatus : infoModifyRequest.getRoommateSearchStatus();
        this.gender = (infoModifyRequest.getMemberGender() == null) ? gender : infoModifyRequest.getMemberGender();
    }

    /**
     * 특성 아이디 수정
     * @param characteristicId 수정 시 입력할 특성 아이디
     */
    public void modifyCharacteristicId(Long characteristicId) {
        this.characteristicId = characteristicId;
    }
}