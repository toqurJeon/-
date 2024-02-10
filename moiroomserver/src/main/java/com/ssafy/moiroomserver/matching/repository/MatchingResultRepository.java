package com.ssafy.moiroomserver.matching.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moiroomserver.matching.entity.MatchingResult;

@Repository
public interface MatchingResultRepository extends JpaRepository<MatchingResult, Long> {

	// Page<MatchingResult> findByMemberOneIdOrMemberTwoIdOrderByRateDesc(Long memberId);
}