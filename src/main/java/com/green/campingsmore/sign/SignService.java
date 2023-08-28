
package com.green.campingsmore.sign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.CommonRes;
import com.green.campingsmore.community.board.utils.FileUtils;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.security.JwtTokenProvider;
import com.green.campingsmore.config.security.UserDetailsMapper;
import com.green.campingsmore.config.security.model.*;
import com.green.campingsmore.config.security.redis.RedisService;
import com.green.campingsmore.config.security.redis.model.RedisJwtVo;
import com.green.campingsmore.sign.model.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final UserDetailsMapper MAPPER;
    private final JwtTokenProvider JWT_PROVIDER;
    private final PasswordEncoder PW_ENCODER;
    private final RedisService REDIS_SERVICE;
    private final ObjectMapper OBJECT_MAPPER;
    private final AuthenticationFacade FACADE;
    private final MailApi mail;
    @Value("${file.dir}")
    private String fileDir;

    public void test() {
        log.info("service-test-iuser : {}", FACADE.getLoginUserPk());
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

    // 로그인
    public SignInResultDto signIn(UserLogin userLogin, String ip) throws RuntimeException {
        String id = userLogin.getUid();
        String password = userLogin.getUpw();
        log.info("[getSignInResult] signDataHandler로 회원 정보 요청");

        LoginDto user = MAPPER.getByUid(id);
        System.out.println("로그인 확인 = "+FACADE.isLogin());

        System.out.println("LoginDto : "+user);
        if(user == null){
            throw new RuntimeException("없는 회원이거나 탈퇴한 회원입니다.");
        }

//        log.info("[getSignInResult] id: {}", id);
//        log.info("[getSignInResult] 패스워드 비교 : {}",password);
//        log.info("[getSignInResult] UserEntity : {}",user);
//        log.info("[getSignInResult] user.getUpw() : {}",user.getUpw());
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

        return dto;
    }


    public void logout(HttpServletRequest req) {
        String accessToken = JWT_PROVIDER.resolveToken(req, JWT_PROVIDER.TOKEN_TYPE);
        Long iuser = FACADE.getLoginUserPk();
        String ip = req.getRemoteAddr();

        // Redis에 저장되어 있는 RT 삭제
        String redisKey = String.format("RT(%s):%s:%s", "Server", iuser, ip);
        String refreshTokenInRedis = REDIS_SERVICE.getValues(redisKey);
        if (refreshTokenInRedis != null) {
            System.out.println("Redis에 저장되어 있는 RT 삭제");
            REDIS_SERVICE.deleteValues(redisKey);
        }
        // Redis에 로그아웃 처리한 AT 저장
        //long expiration = JWT_PROVIDER.getTokenExpirationTime(accessToken, JWT_PROVIDER.ACCESS_KEY) - new Date().getTime();
        long expiration = JWT_PROVIDER.getTokenExpirationTime(accessToken, JWT_PROVIDER.ACCESS_KEY)
                - LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        log.info("date-getTime(): {}", new Date().getTime());
        log.info("localDateTime-getTime(): {}", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        log.info("expiration : {}", expiration);

        REDIS_SERVICE.setValuesWithTimeout(accessToken, "logout", expiration);  //남은시간 이후가 되면 삭제가 되도록 함.

    }

    public SignInResultDto refreshToken(HttpServletRequest req, String refreshToken) throws RuntimeException {
        if(!(JWT_PROVIDER.isValidateToken(refreshToken, JWT_PROVIDER.REFRESH_KEY))) {
            return null;
        }

        String ip = req.getRemoteAddr(); // ip주소 얻어오기
        String accessToken = JWT_PROVIDER.resolveToken(req, JWT_PROVIDER.TOKEN_TYPE);// accessToken 얻어오기
        Claims claims = JWT_PROVIDER.getClaims(refreshToken, JWT_PROVIDER.REFRESH_KEY); // refreshToken 얻어오기

        try {
            claims = JWT_PROVIDER.getClaims(refreshToken, JWT_PROVIDER.REFRESH_KEY);
        }catch (Exception e) {
            e.printStackTrace();
        }

        if (claims == null) {
            return null;
        }

        String strIuser = claims.getSubject();
        Long uid = Long.valueOf(strIuser);

        String redisKey = String.format("RT(%s):%s:%s", "Server", uid, ip);
        String value = REDIS_SERVICE.getValues(redisKey);
        System.out.println("Redis에 저장되어 있는 RT" + value);
        if (value == null) { // Redis에 저장되어 있는 RT가 없을 경우
            log.info("재로그인 요청");
            return null; // -> 재로그인 요청
        }

        try {
            RedisJwtVo redisJwtVo = OBJECT_MAPPER.readValue(value, RedisJwtVo.class);
            if(!redisJwtVo.getAccessToken().equals(accessToken)
                    || !redisJwtVo.getRefreshToken().equals(refreshToken)) {
                return null;
            }

            List<String> roles = (List<String>)claims.get("roles");
            String reAccessToken = JWT_PROVIDER.generateJwtToken(strIuser, roles, JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);

            //redis 업데이트
            RedisJwtVo updateRedisJwtVo = RedisJwtVo.builder()
                    .accessToken(reAccessToken)
                    .refreshToken(redisJwtVo.getRefreshToken())
                    .build();
            String upateValue = OBJECT_MAPPER.writeValueAsString(updateRedisJwtVo);
            REDIS_SERVICE.setValues(redisKey, upateValue);

            return SignInResultDto.builder()
                    .accessToken(reAccessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    // 회원정보 수정하는 곳
    public int updateUserInfo(UpdateUserInfoDto updateUserInfoDto, MultipartFile pic) throws Exception {
        updateUserInfoDto.setUpw(PW_ENCODER.encode(updateUserInfoDto.getUpw()));

        FinalUpdateUserInfo finalUpdateUserInfo = new FinalUpdateUserInfo();
        finalUpdateUserInfo.setUid(updateUserInfoDto.getUid());
        finalUpdateUserInfo.setUpw(updateUserInfoDto.getUpw());
        finalUpdateUserInfo.setEmail(updateUserInfoDto.getEmail());
        finalUpdateUserInfo.setName(updateUserInfoDto.getName());
        finalUpdateUserInfo.setBirth_date(updateUserInfoDto.getBirth_date());
        finalUpdateUserInfo.setPhone(updateUserInfoDto.getPhone());
        finalUpdateUserInfo.setUser_address(updateUserInfoDto.getUser_address());
        finalUpdateUserInfo.setUser_address_detail(updateUserInfoDto.getUser_address_detail());
        
        if (pic != null) {
            String centerPath = "user/" + FACADE.getLoginUserPk() + "/profile/";
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String originFile = pic.getOriginalFilename();
            String saveName = FileUtils.makeRandomFileNm(originFile);
            File fileTarget = new File(targetPath + "/" + saveName);
            try {
                pic.transferTo(fileTarget);
            } catch (IOException e) {
                throw new Exception("파일저장을 실패했습니다");
            }


            String profile_img = centerPath + saveName;
            finalUpdateUserInfo.setPic(profile_img);
            //.pic("/user/"+FACADE.getLoginUserPk()+"/profile/"+pic)
            System.out.println(finalUpdateUserInfo.toString());


        }

        int i = MAPPER.updateUserInfo(finalUpdateUserInfo);
        System.out.println("i = " + i);
        return 1;
    }

    public int searchPW(SearchUserDto userinfo){
        System.out.println("SearchUserDto = "+ userinfo);
        System.out.println("DB리턴값 = "+MAPPER.searchUser(userinfo));
        SearchUserDto correct = MAPPER.searchUser(userinfo);

        if(correct == null){
            throw new RuntimeException("없는 회원이거나 탈퇴한 회원입니다.");
        }

        String id = userinfo.getUid();
        String name = userinfo.getName();
        String email = userinfo.getEmail();

        // 임시 비밀번호 발급해서 DB에 저장하기
        UpdatePwDto updatePwDto = new UpdatePwDto();
        updatePwDto.setUid(id);
        String PW = UUID.randomUUID().toString();
        System.out.println("PW : " + PW);
        updatePwDto.setPassword(PW_ENCODER.encode(PW));
        updatePwDto.setName(name);
        updatePwDto.setEmail(email);
        System.out.println("updatePwDto = "+updatePwDto);

        // 임시 비밀번호 네이버 이메일로 쏴주기
        try {
            mail.sendMail(updatePwDto,PW);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return MAPPER.searchPW(updatePwDto); // 성공시 1 반환
    }


    public UserInfo getmyInfo(){
        System.out.println("로그인 상태 유뮤 = " + FACADE.getLoginUserPk());

        return MAPPER.getmyInfo(Math.toIntExact(FACADE.getLoginUserPk()));
    }

}

