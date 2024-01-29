package com.ssafy.moiroomserver.oauth.repository;

import com.ssafy.moiroomserver.oauth.entity.KakaoMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthRepository extends CrudRepository<KakaoMember, String> {
    Optional<KakaoMember> findByEmailAndOauthType(String email, String oauthType);
    Optional<KakaoMember> findByKakaoId(String kakaoId);
}
