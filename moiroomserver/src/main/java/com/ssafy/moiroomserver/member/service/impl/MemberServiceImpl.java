package com.ssafy.moiroomserver.member.service.impl;

import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void modifyMemberInfo(MemberInfo.ModifyRequest infoModifyRequest) {
        Member member = memberRepository.findById(1L);
        member.modify(infoModifyRequest);
    }
}