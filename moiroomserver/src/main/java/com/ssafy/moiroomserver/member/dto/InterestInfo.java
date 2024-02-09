package com.ssafy.moiroomserver.member.dto;

import com.ssafy.moiroomserver.member.entity.Interest;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.entity.MemberInterest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class InterestInfo {

    @Getter
    @Setter
    public static class RequestResponse {
        private String interestName;
        private int interestPercent;

        @Builder
        public RequestResponse(MemberInterest memberInterest) {
            this.interestName = memberInterest.getInterest().getName();
            this.interestPercent = memberInterest.getPercent();
        }

        public MemberInterest toEntity(Member member, Interest interest) {

            return MemberInterest.builder()
                    .member(member)
                    .interest(interest)
                    .percent(interestPercent)
                    .build();
        }
    }
}