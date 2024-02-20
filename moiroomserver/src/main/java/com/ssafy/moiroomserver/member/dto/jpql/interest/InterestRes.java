package com.ssafy.moiroomserver.member.dto.jpql.interest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InterestRes {
    private String interestName;
    private Integer interestPercent;
}