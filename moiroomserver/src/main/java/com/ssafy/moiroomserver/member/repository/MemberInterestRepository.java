package com.ssafy.moiroomserver.member.repository;

import java.util.List;

import com.ssafy.moiroomserver.member.dto.InterestRes;
import com.ssafy.moiroomserver.member.entity.Member;
import com.ssafy.moiroomserver.member.entity.MemberInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInterestRepository extends JpaRepository<MemberInterest, Long> {

    void deleteByMember(Member member);
    List<MemberInterest> findByMemberOrderByPercentDesc(Member member);

    @Query(
            "select new com.ssafy.moiroomserver.member.dto.InterestRes(mi.interest.name, mi.percent) from MemberInterest mi " +
                    "join Interest i on i.interestId = mi.interest.interestId" +
                    " where mi.member.memberId = :memberId"
    )
    List<InterestRes> findByMemberId(@Param("memberId") Long memberId);
}