package com.ssafy.moiroomserver.member.repository;

import java.util.List;

import com.ssafy.moiroomserver.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByProviderAndSocialId(String provider, Long socialId);
    Member findMemberBySocialIdAndProvider(Long socialId, String provider);
    List<Member> findByIdNotGenderAndMetropolitanIdAndCityIdAndRoommateSearchStatus(Long id, String gender, Long metropolitanId, Long cityId, int roommateSearchStatus);
}