package com.ssafy.moiroomserver.member.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.moiroomserver.matching.entity.MatchingResult;
import com.ssafy.moiroomserver.member.entity.Characteristic;
import com.ssafy.moiroomserver.member.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MemberInfo {

    @Getter
    @Setter
    public static class ModifyRequest {
        private MultipartFile memberProfileImage;
        private String profileImageUrl;
        private Long metropolitanId;
        private Long cityId;
        private String memberNickname;
        private String memberIntroduction;
        private Integer roommateSearchStatus;
        private String memberGender;
    }

    @Getter
    @Setter
    public static class GetDetailResponse {
        private Long memberId;
        private String memberProfileImageUrl;
        private String memberNickname;
        private String memberGender;
        private String memberName;
        private String memberBirthYear;
        private String metropolitanName;
        private String cityName;
        private String memberIntroduction;
        private int matchRate;
        private String matchIntroduction;
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
        private List<InterestInfo.RequestResponse> interestList;

        @Builder
        public GetDetailResponse(Member member, String metropolitanName, String cityName, MatchingResult matchingResult,
                Characteristic characteristic, List<InterestInfo.RequestResponse> interestList) {
            this.memberId = member.getMemberId();
            this.memberProfileImageUrl = member.getImageUrl();
            this.memberNickname = member.getNickname();
            this.memberGender = member.getGender();
            this.memberName = member.getName();
            this.memberBirthYear = member.getBirthyear();
            this.metropolitanName = metropolitanName;
            this.cityName = cityName;
            this.memberIntroduction = member.getIntroduction();
            this.matchRate = matchingResult.getRate();
            this.matchIntroduction = matchingResult.getRateIntroduction();
            this.sociability = characteristic.getSociability();
            this.positivity = characteristic.getPositivity();
            this.activity = characteristic.getActivity();
            this.communion = characteristic.getCommunion();
            this.altruism = characteristic.getAltruism();
            this.empathy = characteristic.getEmpathy();
            this.humor = characteristic.getHumor();
            this.generous = characteristic.getGenerous();
            this.sleepAt = characteristic.getSleepAt();
            this.wakeUpAt = characteristic.getWakeUpAt();
            this.interestList = interestList;
        }
    }
}