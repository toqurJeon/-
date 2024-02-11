package com.ssafy.moiroomserver.member.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
    public static class GetResponse {
        private Long memberId;
        private String memberProfileImageUrl;
        private String memberNickname;
        private String memberGender;
        private String memberName;
        private String memberBirthYear;
        private String metropolitanName;
        private String cityName;
        private String memberIntroduction;
        private CharacteristicInfo.RequestResponse characteristic;
        private List<InterestInfo.RequestResponse> interestList;

        @Builder
        public GetResponse(Member member, String metropolitanName, String cityName, CharacteristicInfo.RequestResponse characteristic,
                List<InterestInfo.RequestResponse> interestList) {
            this.memberId = member.getMemberId();
            this.memberProfileImageUrl = member.getImageUrl();
            this.memberNickname = member.getNickname();
            this.memberGender = member.getGender();
            this.memberName = member.getName();
            this.memberBirthYear = member.getBirthyear();
            this.metropolitanName = metropolitanName;
            this.cityName = cityName;
            this.memberIntroduction = member.getIntroduction();
            this.characteristic = characteristic;
            this.interestList = interestList;
        }
    }
}