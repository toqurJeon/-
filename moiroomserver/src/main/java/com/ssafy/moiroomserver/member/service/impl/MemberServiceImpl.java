package com.ssafy.moiroomserver.member.service.impl;

import com.ssafy.moiroomserver.member.repository.MemberRepository;
import com.ssafy.moiroomserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
}