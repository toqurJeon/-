package com.ssafy.moiroomserver.matching.service;

import com.ssafy.moiroomserver.global.dto.PageResponse;
import com.ssafy.moiroomserver.matching.dto.MatchingInfo;

import com.ssafy.moiroomserver.matching.dto.MatchingResultInfo;
import jakarta.servlet.http.HttpServletRequest;

public interface MatchingService {

	MatchingInfo.MatchingResponse getInfoForMatching(HttpServletRequest request);
	void addMatchingResult(HttpServletRequest request, MatchingResultInfo.AddMatchingResultsRequest matchingInfoAddReq);
	PageResponse getMatchingRoommateList(HttpServletRequest request, int pgno);
}