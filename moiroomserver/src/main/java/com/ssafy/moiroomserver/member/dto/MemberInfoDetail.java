package com.ssafy.moiroomserver.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberInfoDetail {
    private Long memberId;
    private String memberProfileImageUrl;
    private String memberNickname;
    private String memberGender;
    private String memberName;
    private String memberBirthYear;
    private String metropolitanName;
    private String cityName;
    private String memberIntroduction;
    private Integer memberRoommateSearchStatus;
    private CharacteristicInfo.RequestResponse characteristic;
    private List<InterestRes> interests;

    public MemberInfoDetail(Long memberId, String memberProfileImageUrl,
                            String memberNickname, String memberGender, String memberName,
                            String memberBirthYear, String metropolitanName, String cityName,
                            String memberIntroduction, Integer memberRoommateSearchStatus) {
        this.memberId = memberId;
        this.memberProfileImageUrl = memberProfileImageUrl;
        this.memberNickname = memberNickname;
        this.memberGender = memberGender;
        this.memberName = memberName;
        this.memberBirthYear = memberBirthYear;
        this.metropolitanName = metropolitanName;
        this.cityName = cityName;
        this.memberIntroduction = memberIntroduction;
        this.memberRoommateSearchStatus = memberRoommateSearchStatus;
    }

}