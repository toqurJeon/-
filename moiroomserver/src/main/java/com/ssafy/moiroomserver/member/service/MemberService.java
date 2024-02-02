package com.ssafy.moiroomserver.member.service;

import com.ssafy.moiroomserver.member.dto.AddMemberDto;
import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.dto.MemberTokenDto;

public interface MemberService {

    void modifyMemberInfo(MemberInfo.ModifyRequest infoModifyRequest);

    void addMember(AddMemberDto dto);

    void modifyMemberToken(Long memberId, MemberTokenDto tokenDto);
}