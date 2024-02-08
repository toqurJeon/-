package com.ssafy.moiroomserver.member.dto;

import com.ssafy.moiroomserver.member.entity.Interest;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.entity.MemberInterest;
import lombok.Getter;
import lombok.Setter;

public class InterestInfo {

    @Getter
    @Setter
    public static class AddRequest {
        private String interestName;
        private int interestPercent;

        public MemberInterest toEntity(Member member, Interest interest) {

            return MemberInterest.builder()
                    .member(member)
                    .interest(interest)
                    .percent(interestPercent)
                    .build();
        }
    }
}