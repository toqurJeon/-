package com.ssafy.moiroomserver.matching.service.impl;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssafy.moiroomserver.matching.service.MatchingSchedulerService;

@Service
public class MatchingSchedulerServiceImpl implements MatchingSchedulerService {

	@Scheduled(fixedDelay = 10000)
	public void test() {
		System.out.println("현재 시간: " + LocalDateTime.now());
	}
}