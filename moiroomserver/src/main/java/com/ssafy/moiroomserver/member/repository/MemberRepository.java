package com.ssafy.moiroomserver.member.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.moiroomserver.member.dto.MemberInfoDetail;
import com.ssafy.moiroomserver.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByProviderAndSocialId(String provider, Long socialId);
    Member findMemberBySocialIdAndProvider(Long socialId, String provider);
    List<Member> findByMemberIdNotAndGenderAndMetropolitanIdAndCityIdAndRoommateSearchStatus(Long memberId, String gender, Long metropolitanId, Long cityId, int roommateSearchStatus);

    @Query(
            "select new com.ssafy.moiroomserver.member.dto.MemberInfoDetail(" +
                    "m.memberId as memberId, m.imageUrl as memberProfileImageUrl, m.nickname as memberNickname, " +
                    "m.gender as memberGender, m.name as memberName, m.birthyear as memberBirthYear," +
                    "met.name as metropolitanName, c.name as cityName, m.introduction as memberIntroduction, " +
                    "m.roommateSearchStatus as memberRoommateSearchStatus) " +
                    "from Member m " +
                    "join Metropolitan met on m.metropolitanId = met.metropolitanId " +
                    "join City c on m.cityId = c.cityId " +
                    "where m.memberId = :memberId"
    )
    Optional<MemberInfoDetail> findMemberDetailByMemberId(@Param("memberId") Long memberId);
}