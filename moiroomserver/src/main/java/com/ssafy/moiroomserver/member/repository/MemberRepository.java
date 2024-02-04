package com.ssafy.moiroomserver.member.repository;

import com.ssafy.moiroomserver.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByProviderAndSocialId(String provider, Long socialId);
}