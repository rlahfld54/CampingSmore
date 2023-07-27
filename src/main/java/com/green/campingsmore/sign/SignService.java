package com.green.campingsmore.sign;

import com.green.campingsmore.CommonRes;
import com.green.campingsmore.config.security.JwtTokenProvider;
import com.green.campingsmore.config.security.UserDetailsMapper;
import com.green.campingsmore.config.security.model.LoginDto;
import com.green.campingsmore.config.security.model.SignUpDto;
import com.green.campingsmore.config.security.model.UserTokenDto;
import com.green.campingsmore.config.security.model.UserTokenEntity;
import com.green.campingsmore.sign.model.SignInResultDto;
import com.green.campingsmore.sign.model.SignUpResultDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final UserDetailsMapper MAPPER;
    private final JwtTokenProvider JWT_PROVIDER;
    private final PasswordEncoder PW_ENCODER;

    public SignUpResultDto signUp(SignUpDto signUpDto) {
        log.info("[getSignUpResult] signDataHandler로 회원 정보 요청");
        SignUpDto user = SignUpDto.builder()
                .uid(signUpDto.getUid())
                .upw(PW_ENCODER.encode(signUpDto.getUpw()))
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .birth_date(signUpDto.getBirth_date())
                .phone(signUpDto.getPhone())
                .user_address(signUpDto.getUser_address())
                .role(String.format("ROLE_%s", signUpDto.getRole()))
                .build();
        int result = MAPPER.save(user);
        SignUpResultDto dto = new SignUpResultDto();

        if(result == 1) {
            log.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(dto);
        } else {
            log.info("[getSignUpResult] 실패 처리 완료");
            setFileResult(dto);
        }
        return dto;
    }

    public SignInResultDto signIn(String id, String password, String ip) throws RuntimeException {
        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        LoginDto loginDto = MAPPER.getByUid(id);

        log.info("[getSignInResult] id: {}", id);

        log.info("[getSignInResult] 패스워드 비교 : {}",password);
        log.info("[getSignInResult] UserEntity : {}",loginDto);
        log.info("[getSignInResult] user.getUpw() : {}",loginDto.getUpw());
        if(!PW_ENCODER.matches(password, loginDto.getUpw())) {
            throw new RuntimeException("비밀번호 다름");
        }
        log.info("[getSignInResult] 패스워드 일치");


        log.info("[getSignInResult] access_token 객체 생성");
        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(loginDto.getIuser()), Collections.singletonList(loginDto.getRole()), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(loginDto.getIuser()), Collections.singletonList(loginDto.getRole()), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);
        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setIuser(loginDto.getIuser());
        userTokenDto.setIp(ip);
        userTokenDto.setAccessToken(accessToken);
        userTokenDto.setRefreshToken(refreshToken);
        
        //이제 디비에 저장해야함 mapper 어쩌고
        MAPPER.updUserToken(userTokenDto);

        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto dto = SignInResultDto.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .build();

        log.info("[getSignInResult] SignInResultDto 객체 값 주입");
        setSuccessResult(dto);
        return dto;
    }

    public SignInResultDto refreshToken(HttpServletRequest req, String refreshToken) throws RuntimeException {
        if(!(JWT_PROVIDER.isValidateToken(refreshToken, JWT_PROVIDER.REFRESH_KEY))) {
            return null;
        }

        String ip = req.getRemoteAddr(); // ip주소 얻어오기
        String accessToken = JWT_PROVIDER.resolveToken(req, JWT_PROVIDER.TOKEN_TYPE); // accessToken 얻어오기
        Claims claims = JWT_PROVIDER.getClaims(refreshToken, JWT_PROVIDER.REFRESH_KEY); // refreshToken 얻어오기
        if(claims == null) {
            return null;
        }
        String strIuser = claims.getSubject();
        Long uid = Long.valueOf(strIuser);
        List<String> roles = (List<String>)claims.get("roles");

        UserTokenEntity p = UserTokenEntity.builder()
                .iuser(uid)
                .ip(ip)
                .build();
        UserTokenEntity selResult = MAPPER.selUserToken(p);
        if(selResult == null || !(selResult.getAccess_token().equals(accessToken) && selResult.getRefresh_token().equals(refreshToken))) {
            return null;
        }

        String reAccessToken = JWT_PROVIDER.generateJwtToken(strIuser, roles, JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setIuser(uid);
        userTokenDto.setIp(ip);
        userTokenDto.setAccessToken(accessToken);
        userTokenDto.setRefreshToken(refreshToken);

        MAPPER.updUserToken(userTokenDto);

        return SignInResultDto.builder()
                .accessToken(reAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonRes.SUCCESS.getCode());
        result.setMsg(CommonRes.SUCCESS.getMsg());
    }

    private void setFileResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonRes.FAIL.getCode());
        result.setMsg(CommonRes.FAIL.getMsg());
    }
}

