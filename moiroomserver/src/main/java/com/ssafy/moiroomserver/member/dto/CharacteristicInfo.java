package com.ssafy.moiroomserver.member.dto;

import com.ssafy.moiroomserver.member.entity.Characteristic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CharacteristicInfo {

    @Getter
    @Setter
    public static class RequestResponse {
        private Long memberId;
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
        public RequestResponse(Characteristic characteristic, Long memberId, List<InterestInfo.RequestResponse> interestList) {
            this.memberId = memberId;
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