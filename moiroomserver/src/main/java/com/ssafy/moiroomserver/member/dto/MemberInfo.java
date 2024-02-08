package com.ssafy.moiroomserver.member.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberInfo {

    @Getter
    @Setter
    public static class ModifyRequest {

        private Long metropolitanId;
        private Long cityId;
        private String memberNickname;
        private String memberIntroduction;
    }
}