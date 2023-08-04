package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.redis.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider PROVIDER;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String token = PROVIDER.resolveToken(req, PROVIDER.TOKEN_TYPE); //Request를 보내고있다? 문자열들을 빼내는
        log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 추출 token: {}", token);
        log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 유효성 체크 시작");

        if(token != null && PROVIDER.isValidateToken(token, PROVIDER.ACCESS_KEY)) {

            String isLogout = redisService.getValues(token);
            System.out.println("isLogout : " + isLogout);

            if(ObjectUtils.isEmpty(isLogout)) {//로그아웃이 없으면
                log.info("//로그아웃을 하지않았다면 남은 토큰 유효시간을 확인한다.");
                Authentication auth = PROVIDER.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 유효성 체크 완료");
            }

        }
        filterChain.doFilter(req, res);
    }
}
