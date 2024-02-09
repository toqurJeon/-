package com.ssafy.moiroomserver.member.service;

import com.ssafy.moiroomserver.member.dto.AddMemberDto;
import com.ssafy.moiroomserver.member.dto.CharacteristicInfo;
import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.dto.MemberTokenDto;
import com.ssafy.moiroomserver.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {

    void modifyMemberInfo(HttpServletRequest request, MemberInfo.ModifyRequest MemberInfoModifyReq);

    void login(AddMemberDto dto);

    void modifyMemberToken(Long memberId, MemberTokenDto tokenDto);

    Member getMemberById(Long memberId);

    void logout(Long socialId, String provider);

    void addCharacteristic(HttpServletRequest request, CharacteristicInfo.requestResponse characteristicInfoAddModifyReq);
}