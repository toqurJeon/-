package com.ssafy.moiroomserver.matching.service;

import com.ssafy.moiroomserver.global.dto.PageResponse;
import com.ssafy.moiroomserver.matching.dto.MatchingInfo;

import jakarta.servlet.http.HttpServletRequest;

public interface MatchingService {

	MatchingInfo.MatchingResponse getInfoForMatching(HttpServletRequest request);
	void addMatchingResult(HttpServletRequest request, MatchingInfo.AddRequest matchingInfoAddReq);
	PageResponse getMatchingRoommateList(HttpServletRequest request, int pgno);
}