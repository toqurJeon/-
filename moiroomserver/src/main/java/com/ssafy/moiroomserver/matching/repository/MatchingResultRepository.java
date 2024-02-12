package com.ssafy.moiroomserver.matching.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.moiroomserver.matching.entity.MatchingResult;

import jakarta.transaction.Transactional;

@Repository
public interface MatchingResultRepository extends JpaRepository<MatchingResult, Long> {

	MatchingResult findByMemberOneIdAndMemberTwoId(Long memberOneId, Long MemberTwoId);
	@Query(
			"SELECT mr FROM MatchingResult mr "
			+ "JOIN Member m1 ON mr.memberOneId = m1.memberId "
			+ "JOIN Member m2 ON mr.memberTwoId = m2.memberId "
			+ "WHERE (m1.memberId = :id OR m2.memberId = :id) "
			+ "AND m1.roommateSearchStatus = 1 AND m2.roommateSearchStatus = 1 "
			+ "AND m1.accountStatus = 1 AND m2.accountStatus = 1 "
			+ "AND m1.metropolitanId = m2.metropolitanId AND m1.cityId = m2.cityId "
			+ "ORDER BY mr.rate DESC"
	)
	Page<MatchingResult> findMatchingResultByMemberId(@Param("id") Long memberId, Pageable pageable);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(
		"DELETE FROM MatchingResult mrt "
		+ "WHERE mrt.matchingResultId IN ( "
			+ "SELECT tmp.matchingResultId FROM ( "
				+ "SELECT mr.matchingResultId AS matchingResultId FROM MatchingResult mr "
				+ "JOIN Member m1 ON mr.memberOneId = m1.memberId "
				+ "JOIN Member m2 ON mr.memberTwoId = m2.memberId "
				+ "WHERE m1.roommateSearchStatus = 0 OR m2.roommateSearchStatus = 0 "
				+ "OR m1.accountStatus != 1 OR m2.accountStatus != 1 "
				+ "OR m1.metropolitanId != m2.metropolitanId OR m1.cityId != m2.cityId "
			+ ") tmp "
		+ ")"
	)
	void deleteMatchingResultAfterPeriod();
}