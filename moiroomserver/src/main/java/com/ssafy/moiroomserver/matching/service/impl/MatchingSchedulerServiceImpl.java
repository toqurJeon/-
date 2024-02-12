package com.ssafy.moiroomserver.matching.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssafy.moiroomserver.matching.service.MatchingSchedulerService;

@Service
public class MatchingSchedulerServiceImpl implements MatchingSchedulerService {

	@Scheduled()
	public void deleteMatchingResultAfterPeriod() {
		;
	}
}