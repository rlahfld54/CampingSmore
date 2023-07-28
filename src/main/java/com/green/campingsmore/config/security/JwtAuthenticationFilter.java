package com.green.campingsmore.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider PROVIDER;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String token = PROVIDER.resolveToken(req, PROVIDER.TOKEN_TYPE); //Request를 보내고있다? 문자열들을 빼내는
        log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 추출 token: {}", token);

        if(token != null && PROVIDER.isValidateToken(token, PROVIDER.ACCESS_KEY)) {
            Authentication auth = PROVIDER.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 유효성 체크 완료");
        }
        filterChain.doFilter(req, res);
    }
}
