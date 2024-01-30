package com.ssafy.moiroomserver.member.controller;

import com.ssafy.moiroomserver.global.dto.ApiResponse;
import com.ssafy.moiroomserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public ApiResponse<?> modifyMemberInfo() {
        return null;
    }
}