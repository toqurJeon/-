package com.ssafy.moiroomserver.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MemberInfoDetail {
    private Long memberId;
    private String memberProfileImageUrl;
    private String memberNickname;
    private String memberGender;
    private String memberName;
    private Integer memberBirthYear;
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
    private List<Interest> interest;
}