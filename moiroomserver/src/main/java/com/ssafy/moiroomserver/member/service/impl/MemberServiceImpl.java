package com.ssafy.moiroomserver.member.service.impl;

import com.ssafy.moiroomserver.global.constants.ErrorCode;
import com.ssafy.moiroomserver.global.exception.NoIdException;
import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 정보 수정
     * @param infoModifyRequest 수정 시 입력할 데이터
     */
    @Transactional
    @Override
    public void modifyMemberInfo(MemberInfo.ModifyRequest infoModifyRequest) {
        Member member = memberRepository.findById(2L)
                .orElseThrow(() -> new NoIdException(ErrorCode.NOT_EXISTS_MEMBER_ID));
        member.modify(infoModifyRequest);
    }
}