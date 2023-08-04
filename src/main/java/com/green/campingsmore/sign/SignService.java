
package com.green.campingsmore.sign;

import com.green.campingsmore.CommonRes;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.security.JwtTokenProvider;
import com.green.campingsmore.config.security.UserDetailsMapper;
import com.green.campingsmore.config.security.model.*;
import com.green.campingsmore.sign.model.SignInResultDto;
import com.green.campingsmore.sign.model.SignUpResultDto;
import com.green.campingsmore.sign.model.UpdatePwDto;
import com.green.campingsmore.sign.model.UpdateUserInfoDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final UserDetailsMapper MAPPER;
    private final JwtTokenProvider JWT_PROVIDER;
    private final PasswordEncoder PW_ENCODER;
    private final AuthenticationFacade facade;
    private final MailApi mail;

    @Value("${Naver.NAVER_ID}")
    private String NAVER_ID;

    public void test() {
        log.info("service-test-iuser : {}", facade.getLoginUserPk());
    }

    public SignUpResultDto signUp(SignUpDto signUpDto) {
        log.info("[getSignUpResult] signDataHandler로 회원 정보 요청");
        SignUpDto user = SignUpDto.builder()
                .uid(signUpDto.getUid())
                .upw(PW_ENCODER.encode(signUpDto.getUpw()))
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .birth_date(signUpDto.getBirth_date())
                .phone(signUpDto.getPhone())
                .gender(signUpDto.getGender())
                .user_address(signUpDto.getUser_address())
                .role(String.format("ROLE_%s", signUpDto.getRole()))
                .build();
        int result = MAPPER.signUp(user);
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

    public int deleteUser(int iuser){
        return MAPPER.delYnUser(iuser);
    }

    public String searchID(String name,String phone,String birth){
        return MAPPER.searchID(name,phone,birth);
    }

    public int updateUserInfo(UpdateUserInfoDto updateUserInfoDto){
        // 비밀번호 암호화 해줘야할듯
//        PW_ENCODER.encode(updateUserInfoDto.getUpw());
        updateUserInfoDto.setUpw(PW_ENCODER.encode(updateUserInfoDto.getUpw()));
        return MAPPER.updateUserInfo(updateUserInfoDto);
    }

    public int searchPW(String id, String name, String email){
        // 임시 비밀번호 발급해서 DB에 저장하기
        UpdatePwDto updatePwDto = new UpdatePwDto();
        updatePwDto.setUid(id);
        String PW = UUID.randomUUID().toString();
        System.out.println("PW : " + PW);
        updatePwDto.setPassword(PW_ENCODER.encode(PW));
        updatePwDto.setName(name);
        updatePwDto.setEmail(email);

        // 임시 비밀번호 네이버 이메일로 쏴주기
        try {
            mail.sendMail(updatePwDto,PW);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return MAPPER.searchPW(updatePwDto);
    }


}

