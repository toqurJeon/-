package com.ssafy.moiroomserver.matching.controller;

import com.ssafy.moiroomserver.global.constants.SuccessCode;
import com.ssafy.moiroomserver.global.dto.ApiResponse;
import com.ssafy.moiroomserver.matching.dto.MatchingInfo;
import com.ssafy.moiroomserver.matching.service.MatchingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {

    private final MatchingService matchingService;

    /**
     * 매칭 계산을 위한 정보 조회
     * @param request
     */
    @GetMapping("/info")
    public ApiResponse<?> getInfoForMatching(HttpServletRequest request) {
        MatchingInfo.GetResponse matchingInfoRes = matchingService.getInfoForMatching(request);

        return ApiResponse.success(SuccessCode.GET_INFO_FOR_MATCHING, matchingInfoRes);
    }

    @PostMapping("/result")
    public ApiResponse<?> addMatchingResult(HttpServletRequest request, @RequestBody MatchingInfo.AddRequest matchingInfoAddReq) {
        matchingService.addMatchingResult(request, matchingInfoAddReq);

        return ApiResponse.success(SuccessCode.ADD_MATCHING_RESULT);
    }
}