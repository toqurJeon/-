package com.ssafy.moiroomserver.member.repository;

import com.ssafy.moiroomserver.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByProviderAndSocialId(String provider, Long socialId);

    Member findMemberBySocialIdAndProvider(Long socialId, String provider);

    @Modifying
    @Query("update Member m set m.loginStatus = 1 where m.socialId = :socialId and m.provider = :provider")
    void updateLoginStatusBySocialIdAndProvider(Long socialId, String provider);
}