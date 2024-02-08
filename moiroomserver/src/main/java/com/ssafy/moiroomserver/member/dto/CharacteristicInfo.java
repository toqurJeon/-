package com.ssafy.moiroomserver.member.dto;

import com.ssafy.moiroomserver.member.entity.Characteristic;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CharacteristicInfo {

    @Getter
    @Setter
    public static class AddModifyRequest {
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
        private List<InterestInfo.AddRequest> interestList;

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