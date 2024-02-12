package com.ssafy.moiroomserver.matching.service.impl;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssafy.moiroomserver.matching.repository.MatchingResultRepository;
import com.ssafy.moiroomserver.matching.service.MatchingSchedulerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchingSchedulerServiceImpl implements MatchingSchedulerService {

	private final MatchingResultRepository matchingResultRepository;

	/**
	 * 매달 1일에 불필요한 매칭 결과 테이블의 데이터 일괄 삭제
	 */
	@Scheduled(cron = "0 0 0 1 * *") //매달 1일에 실행
	@Override
	public void deleteMatchingResultAfterPeriod() {
		matchingResultRepository.deleteMatchingResultAfterPeriod();
	}
}