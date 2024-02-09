package com.ssafy.moiroomserver.matching.service.impl;

import java.util.List;

import com.ssafy.moiroomserver.matching.dto.MatchingInfo;
import com.ssafy.moiroomserver.matching.service.MatchingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

	@Override
	public List<MatchingInfo.GetResponse> getInfoForMatching(HttpServletRequest request) {
		return null;
	}
}