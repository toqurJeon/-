package com.ssafy.moiroomserver.member.dto;

import com.ssafy.moiroomserver.member.entity.Characteristic;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.entity.MemberInterest;
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

        public Characteristic toEntity() {

            return Characteristic.builder()
                    .sociability(sociability)
                    .positivity(positivity)
                    .activity(activity)
                    .communion(communion)
                    .altruism(altruism)
                    .empathy(empathy)
                    .humor(humor)
                    .generous(generous)
                    .sleepAt(sleepAt)
                    .wakeUpAt(wakeUpAt)
                    .build();
        }
    }
}