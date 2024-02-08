package com.ssafy.moiroomserver.member.repository;

import com.ssafy.moiroomserver.member.entity.MemberInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInterestRepository extends JpaRepository<MemberInterest, Long> {
}