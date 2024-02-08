package com.ssafy.moiroomserver.member.dto;

import lombok.Getter;
import lombok.Setter;

public class InterestInfo {

    @Getter
    @Setter
    public static class AddRequest {
        private String interestName;
        private int interestPercent;
    }
}