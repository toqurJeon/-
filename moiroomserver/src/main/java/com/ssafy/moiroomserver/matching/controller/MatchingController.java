package com.ssafy.moiroomserver.matching.controller;

import java.util.List;

import com.ssafy.moiroomserver.global.constants.SuccessCode;
import com.ssafy.moiroomserver.global.dto.ApiResponse;
import com.ssafy.moiroomserver.matching.dto.MatchingInfo;
import com.ssafy.moiroomserver.matching.service.MatchingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @GetMapping("/info")
    public ApiResponse<?> getInfoForMatching(HttpServletRequest request) {
        List<MatchingInfo.GetResponse> matchingInfoResList = matchingService.getInfoForMatching(request);

        return ApiResponse.success(SuccessCode.GET_INFO_FOR_MATCHING, matchingInfoResList);
    }
}