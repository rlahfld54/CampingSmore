package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenProvider {
//    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);


    public final Key ACCESS_KEY;
    public final Key REFRESH_KEY;
    public final String TOKEN_TYPE;
//    public final long ACCESS_TOKEN_VALID_MS = 200_000L; // 1000L * 60 * 60 -> 200초
    public final long ACCESS_TOKEN_VALID_MS = 86400_000L; // 1000L * 60 * 60 -> 24시간
    public final long REFRESH_TOKEN_VALID_MS = 1_296_000_000L; // 1000L * 60 * 60 * 24 * 15 -> 15일


    @Autowired
    public JwtTokenProvider(@Value("${springboot.jwt.access-secret}") String accessSecretKey
            , @Value("${springboot.jwt.refresh-secret}") String refreshSecretKey
            , @Value("${springboot.jwt.token-type}") String tokenType) {
        byte[] accessKeyBytes = Decoders.BASE64.decode(accessSecretKey);
        this.ACCESS_KEY = Keys.hmacShaKeyFor(accessKeyBytes);

        byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecretKey);
        this.REFRESH_KEY = Keys.hmacShaKeyFor(refreshKeyBytes);
        this.TOKEN_TYPE = tokenType;
    }


    public String generateJwtToken(String strIuser, List<String> roles, long token_valid_ms, Key key) {
        log.info("JwtTokenProvider - generateJwtToken: 토큰 생성 시작");
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(createClaims(strIuser, roles))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + token_valid_ms))
                .signWith(key)
                .compact();
        log.info("JwtTokenProvider - generateJwtToken: 토큰 생성 완료");
        return token;
    }



    private Claims createClaims(String strIuser, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(strIuser);
        claims.put("roles", roles);
        return claims;
    }

    public Authentication getAuthentication (String token) {
        log.info("JwtTokenProvider - getAuthentication: 토큰 인증 정보 조회 시작");
        UserDetails userDetails = getUserDetailsFromToken(token, ACCESS_KEY);
        System.out.println("userDetails : "+ userDetails);
        log.info("JwtTokenProvider - getAuthentication: 토큰 인증 정보 조회 완료, UserDetails UserName : {}"
                , userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private UserDetails getUserDetailsFromToken(String token, Key key) {
        Claims claims = getClaims(token, key);
        String strIuser = claims.getSubject();
        List<String> roles = (List<String>)claims.get("roles");
        return MyUserDetails
                .builder()
                .iuser(Long.valueOf(strIuser))
                .roles(roles)
                .build();
    }

    public String resolveToken(HttpServletRequest req, String type) { //String type은 Bearer
        log.info("JwtTokenProvider - resolveToken: HTTP 헤더에서 Token 값 추출");
        String headerAuth = req.getHeader("Authorization");
        return headerAuth == null ? null : headerAuth.substring(type.length()).trim(); //substring(6).앞뒤 빈칸제거
    }

    public Claims getClaims(String token, Key key) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Filter에서 사용
    public boolean isValidateToken(String token, Key key) {
        log.info("JwtTokenProvider - isValidateToken: 토큰 유효 체크 시작");
        try {
            return !getClaims(token, key).getExpiration().before(new Date());
        } catch (Exception e) {
            log.info("JwtTokenProvider - isValidateToken: 토큰 유효 체크 예외 발생");
            return false;
        }
        // 지났으면 true > false;
        // 안 지났으면 false > true;
    }

    public long getTokenExpirationTime(String token, Key key) {
        try {
            return getClaims(token, key).getExpiration().getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
