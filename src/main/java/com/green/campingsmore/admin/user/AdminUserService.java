package com.green.campingsmore.admin.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.CommonRes;
import com.green.campingsmore.admin.user.model.UserDto;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.security.JwtTokenProvider;
import com.green.campingsmore.config.security.UserDetailsMapper;
import com.green.campingsmore.config.security.model.LoginDto;
import com.green.campingsmore.config.security.redis.RedisService;
import com.green.campingsmore.config.security.redis.model.RedisJwtVo;
import com.green.campingsmore.sign.model.SignInResultDto;
import com.green.campingsmore.sign.model.SignUpResultDto;
import com.green.campingsmore.sign.model.UserLogin;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AdminUserService {
    private final AdminUserMapper adminUserMapper;
    private final AuthenticationFacade FACADE;
    private final PasswordEncoder PW_ENCODER;
    private final RedisService REDIS_SERVICE;
    private final JwtTokenProvider JWT_PROVIDER;
    private final ObjectMapper OBJECT_MAPPER;

    public List<UserDto> selectAllusers(){
        return adminUserMapper.selectAllusers();
    }

    public SignInResultDto adminSignIn(UserLogin userLogin, String ip){
        String id = userLogin.getUid();
        String password = userLogin.getUpw();
        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");

//        LoginDto user = MAPPER.getByUid(id);
        LoginDto user = adminUserMapper.getAdminUser(id);

        System.out.println("LoginDto = " + user);
        if(user == null){
            throw new RuntimeException("관리자 권한이 없습니다.");
        }

//        if(user.getRole().equals("ROLE_ADMIN")){
//            System.out.println("관리자 권한이 있다...");
//        } else{
//            throw new RuntimeException("관리자 권한이 없습니다.");
//        }

        // 비밀번호 일치하는지 확인
        if(!PW_ENCODER.matches(password, user.getUpw())) {
            throw new RuntimeException("비밀번호 다름"); // return문 대신에 throw 예욍처리해도 된다.
        }
        log.info("[getSignInResult] 패스워드 일치");

        // RT가 이미 있을 경우
        String redisKey = String.format("RT(%s):%s:%s", "Server", user.getIuser(), ip);
        if(REDIS_SERVICE.getValues(redisKey) != null) {
            REDIS_SERVICE.deleteValues(redisKey); // 삭제
        }

        log.info("[getSignInResult] access_token 객체 생성");
        String accessToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getIuser()), Collections.singletonList(user.getRole()), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken(String.valueOf(user.getIuser()), Collections.singletonList(user.getRole()), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);

        // Redis에 RT 저장
        RedisJwtVo redisJwtVo = RedisJwtVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        String value = null;
        try {
            value = OBJECT_MAPPER.writeValueAsString(redisJwtVo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        REDIS_SERVICE.setValues(redisKey, value);

        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto dto = SignInResultDto.builder()
                .accessToken(redisJwtVo.getAccessToken())
                .refreshToken(redisJwtVo.getRefreshToken())
                .build();

        log.info("[getSignInResult] SignInResultDto 객체 값 주입");
        setSuccessResult(dto);

        System.out.println("로그인 확인 = " + FACADE.isLogin());

        return dto;
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
