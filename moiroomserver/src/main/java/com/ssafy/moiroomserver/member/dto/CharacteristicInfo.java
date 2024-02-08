package com.ssafy.moiroomserver.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CharacteristicInfo {

    @Getter
    @Setter
    public static class AddRequest {
        private int sociability;
        private int positivity;
        private int activity;
        private int communion;
        private int altruism;
        private int empathy;
        private int humor;
        private int generous;
        private String sleepAt;
        private String wakeUpAt;
        private List<InterestInfo.AddRequest> interest;

    }
}