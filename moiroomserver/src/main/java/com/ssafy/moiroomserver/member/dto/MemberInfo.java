package com.ssafy.moiroomserver.member.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.moiroomserver.member.entity.Characteristic;
import com.ssafy.moiroomserver.member.entity.Member;

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
        private List<InterestInfo.RequestResponse> interestList;

        public GetDetailResponse(Member member, Characteristic characteristic) {
            this.memberId = member.getMemberId();
            this.memberProfileImageUrl = member.getImageUrl();
            this.memberNickname = member.getNickname();
            this.memberGender = member.getGender();
            this.memberName = member.getName();
            this.memberBirthYear = member.getBirthyear();
            this.metropolitanName = null;
            this.cityName = null;
            this.memberIntroduction = member.getIntroduction();
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
            this.interestList = interestList;
        }
    }
}