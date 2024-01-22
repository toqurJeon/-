package com.ssafy.moiroomserver.member.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
public class MemberInterestId implements Serializable {

    private Member member;
    private Interest interest;
}