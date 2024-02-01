package com.ssafy.moiroomserver.member.controller;

import com.ssafy.moiroomserver.global.constants.SuccessCode;
import com.ssafy.moiroomserver.global.dto.ApiResponse;
import com.ssafy.moiroomserver.member.dto.AddMemberDto;
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

    @PatchMapping
    public ApiResponse<?> modifyMemberInfo(@RequestBody MemberInfo.ModifyRequest infoModifyRequest) {
        memberService.modifyMemberInfo(infoModifyRequest);

        return ApiResponse.success(SuccessCode.MODIFY_MEMBER_INFO);
    }

    /**
     * 카카오 회원 정보 저장
     * @param accountDto
     * @return
     */
    @PostMapping
    public ApiResponse<?> addMember(@RequestBody AddMemberDto accountDto) {
        memberService.addMember(accountDto);
        return ApiResponse.success(SuccessCode.ADD_MEMBER_INFO);
    }
}