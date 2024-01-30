package com.ssafy.moiroomserver.member.controller;

import com.ssafy.moiroomserver.global.dto.ApiResponse;
import com.ssafy.moiroomserver.member.dto.MemberInfo;
import com.ssafy.moiroomserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/member")
    public ApiResponse<?> modifyMemberInfo(@RequestBody MemberInfo.ModifyRequest infoModifyRequest) {
        return null;
    }
}