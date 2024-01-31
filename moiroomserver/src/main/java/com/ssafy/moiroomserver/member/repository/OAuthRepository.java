package com.ssafy.moiroomserver.member.repository;

import com.ssafy.moiroomserver.member.entity.KakaoMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthRepository extends CrudRepository<KakaoMember, String> {
    Optional<KakaoMember> findByEmailAndOauthType(String email, String oauthType);
    Optional<KakaoMember> findByKakaoId(String kakaoId);
}
