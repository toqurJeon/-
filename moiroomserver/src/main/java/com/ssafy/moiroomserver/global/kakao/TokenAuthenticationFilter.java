package com.ssafy.moiroomserver.global.kakao;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private KakaoService kakaoTokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("TEST 3 - 에러");
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            if (kakaoTokenValidator.validateToken(token)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                null,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority("USER"))
                        );
                System.out.println("TEST 4 - 에러");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("TEST 5 - 에러");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Access Token");
                System.out.println("TEST 6 - 에러");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}