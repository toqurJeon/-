package com.ssafy.moiroomserver.global.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 회원 정보를 Authentication에서 받아오는 것이 아니다.
        // 클라이언트에서 request body로 정보를 요청 받을 것이다. -> 중간 작업이 필요함
    }
}
