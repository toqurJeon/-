package com.ssafy.moiroomserver.member.dto;

import com.ssafy.moiroomserver.member.dto.jpql.interest.InterestRes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberInfoDetail {
    private Long memberId;
    private String memberProfileImageUrl;
    private String memberNickname;
    private String memberGender;
    private String memberName;
    private String memberBirthYear;
    private String metropolitanName;
    private String cityName;
    private String memberIntroduction;
    private Integer memberRoommateSearchStatus;
    private CharacteristicInfo.CharacteristicResponse characteristic;
    private List<InterestRes> interests;
}
