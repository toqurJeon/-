package com.ssafy.moiroomserver.global.util;

import com.ssafy.moiroomserver.global.dto.JwtDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // accessToken 유효 시간: 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // refreshToken 유효 시간: 일주일

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    //토큰 생성하기 (refresh 및 access)
    public JwtDto generateToken(Long socialId, String socialProvider) {

        long accessExpiresIn = System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME;
        long refreshExpiresIn = System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME;

        //access token 생성하기
        String accessToken = Jwts.builder()
                .issuer("moiroom")
                .subject("socialId")
                .issuedAt(new Date())
                .expiration(new Date(accessExpiresIn))
                .claim("socialId", socialId)
                .claim("socialProvider", socialProvider)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        //refresh token 생성하기
        String refreshToken = Jwts.builder()
                .expiration(new Date(refreshExpiresIn))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return JwtDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpiresIn(accessExpiresIn)
                .build();
    }
}
