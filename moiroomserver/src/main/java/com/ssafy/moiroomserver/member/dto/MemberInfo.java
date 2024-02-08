package com.ssafy.moiroomserver.member.dto;

import org.springframework.web.multipart.MultipartFile;

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
}