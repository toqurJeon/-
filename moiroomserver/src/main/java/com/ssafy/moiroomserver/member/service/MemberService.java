package com.ssafy.moiroomserver.member.service;

import com.ssafy.moiroomserver.member.dto.AddMemberDto;
import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.dto.MemberTokenDto;
import com.ssafy.moiroomserver.member.entity.Member;

public interface MemberService {

    void modifyMemberInfo(MemberInfo.ModifyRequest infoModifyRequest);

    void login(AddMemberDto dto);

    void modifyMemberToken(Long memberId, MemberTokenDto tokenDto);

    Member getMemberById(Long memberId);

    void logout(Long socialId, String provider);

}