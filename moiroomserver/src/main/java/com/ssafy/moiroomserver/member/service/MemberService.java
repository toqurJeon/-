package com.ssafy.moiroomserver.member.service;

import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.dto.MemberInfo.AddMemberRequest;
import com.ssafy.moiroomserver.member.dto.MemberInfo.MemberInfoRes;
import com.ssafy.moiroomserver.member.dto.MemberTokenDto;
import com.ssafy.moiroomserver.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {

    void modifyMemberInfo(HttpServletRequest request, MemberInfo.ModifyMemberRequest MemberInfoModifyReq);

    void login(AddMemberRequest dto);

    void modifyMemberToken(Long memberId, MemberTokenDto tokenDto);

    Member getMemberById(Long memberId);

    void logout(Long socialId, String provider);

    MemberInfoRes getMemberInfoDetail(HttpServletRequest request);
}