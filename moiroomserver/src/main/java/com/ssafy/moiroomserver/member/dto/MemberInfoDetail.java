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
    private Integer sociability;
    private Integer positivity;
    private Integer activity;
    private Integer communion;
    private Integer altruism;
    private Integer empathy;
    private Integer humor;
    private Integer generous;
    private String sleepAt;
    private String wakeUpAt;
    private List<InterestRes> interests;

    public MemberInfoDetail(Long memberId, String memberProfileImageUrl,
                            String memberNickname, String memberGender, String memberName,
                            String memberBirthYear, String metropolitanName, String cityName,
                            String memberIntroduction, Integer memberRoommateSearchStatus, Integer sociability,
                            Integer positivity, Integer activity, Integer communion, Integer altruism, Integer empathy,
                            Integer humor, Integer generous, String sleepAt, String wakeUpAt) {
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
        this.sociability = sociability;
        this.positivity = positivity;
        this.activity = activity;
        this.communion = communion;
        this.altruism = altruism;
        this.empathy = empathy;
        this.humor = humor;
        this.generous = generous;
        this.sleepAt = sleepAt;
        this.wakeUpAt = wakeUpAt;
    }

}