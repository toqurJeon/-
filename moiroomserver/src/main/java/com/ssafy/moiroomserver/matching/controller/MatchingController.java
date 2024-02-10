package com.ssafy.moiroomserver.matching.controller;

import static com.ssafy.moiroomserver.global.constants.SuccessCode.*;

import com.ssafy.moiroomserver.global.constants.SuccessCode;
import com.ssafy.moiroomserver.global.dto.ApiResponse;
import com.ssafy.moiroomserver.global.dto.PageResponse;
import com.ssafy.moiroomserver.matching.dto.MatchingInfo;
import com.ssafy.moiroomserver.matching.service.MatchingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        return ApiResponse.success(GET_INFO_FOR_MATCHING, matchingInfoRes);
    }

    /**
     * 매칭 결과 추가
     * @param request
     * @param matchingInfoAddReq 추가할 매칭 결과 리스트
     */
    @PostMapping("/result")
    public ApiResponse<?> addMatchingResult(HttpServletRequest request, @RequestBody MatchingInfo.AddRequest matchingInfoAddReq) {
        matchingService.addMatchingResult(request, matchingInfoAddReq);

        return ApiResponse.success(ADD_MATCHING_RESULT);
    }

    @GetMapping("/result")
    public ApiResponse<?> getMatchingRoommateList(HttpServletRequest request,
                                                @RequestParam(required = false, defaultValue = "1") int pgno) {
        PageResponse matchingRoommateListRes = matchingService.getMatchingRoommateList(request, pgno);
        if (matchingRoommateListRes == null) {

            return ApiResponse.success(NO_MATCHING_ROOMMATE_LIST);
        }

        return ApiResponse.success(GET_MATCHING_ROOMMATE_LIST, matchingRoommateListRes);
    }
}