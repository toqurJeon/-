package com.ssafy.moiroomserver.matching.service;

import java.util.List;

import com.ssafy.moiroomserver.matching.dto.MatchingInfo;

import jakarta.servlet.http.HttpServletRequest;

public interface MatchingService {

	MatchingInfo.GetResponse getInfoForMatching(HttpServletRequest request);
	void addMatchingResult(HttpServletRequest request, MatchingInfo.AddRequest matchingInfoAddReq);
}