package com.green.campingsmore.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.security.JwtTokenProvider;
import com.green.campingsmore.config.security.SecurityConfiguration;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.config.security.model.SignUpDto;
import com.green.campingsmore.config.security.redis.RedisService;
import com.green.campingsmore.config.security.redis.model.RedisJwtVo;
import com.green.campingsmore.sign.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = SignController.class)
@Import({SecurityConfiguration.class, JwtTokenProvider.class})
class SignControllerTest {
//    시큐리티 테스트 할때는
//    JWT 필터를 거치지 않고 바로 컨트롤러로 간다.

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HttpServletRequest request;

    @MockBean
    private SignService service;

    @MockBean
    private RedisService redisService;

    @MockBean
    private JwtTokenProvider JWT_PROVIDER;

    @MockBean
    private AuthenticationFacade FACADE;

    @BeforeEach
    void beforeEach() {
        UserDetails user = createUserDetails();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));

    }

    private UserDetails createUserDetails() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        Long IUSER = 7L; //  "rlahfld54" , iuser : 7

        return MyUserDetails.builder()
                .iuser(IUSER)
                .uid("rlahfld54")
                .upw("0000")
                .roles(roles)
                .build();
    }


    @Test
    @DisplayName("SignController - 로그인")
    void signIn() throws Exception {
        UserLogin userLogin = new UserLogin();
        userLogin.setUid("rlahfld54");
        userLogin.setUpw("0000");

        // jackson objectmapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String user_info = objectMapper.writeValueAsString(userLogin);
        System.out.println("user_info : " + user_info);

        String accessToken = "fjhfljkashd.asdfjaksjflskdjfklasdlfkadfasdf.asdfjalskdfjalsdkfs.sdasddfadf";
        String refreshToken = "asdfasdfhd.asdfjaksjflskdjfklasdlfkadfasdf.asdfjalskdfjalsdkfs.sdasddfadf";

        RedisJwtVo redisJwtVo = RedisJwtVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        SignInResultDto result = SignInResultDto.builder()
                .accessToken(redisJwtVo.getAccessToken())
                .refreshToken(redisJwtVo.getRefreshToken())
                .build();

        given(service.signIn(userLogin,"127.0.0.1")).willReturn(result);

        mvc.perform(
                        post("/api/oauth/authorize")
                                .content(user_info)
                                .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(service).signIn(userLogin,"127.0.0.1");
    }

    @Test
    @DisplayName("SignController - 로그아웃")
    void logout() throws Exception {
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY5MTYzOTUwMywiZXhwIjoxNjkyOTM1NTAzfQ.ynFAAxFl-78mFCto5afzqmLQqmzRAaQ7bJgkECfAit4";
        Long iuser = FACADE.getLoginUserPk();
        String ip = request.getRemoteAddr();

        // Redis에 저장되어 있는 RT 삭제
        String redisKey = String.format("RT(%s):%s:%s", "Server", iuser, ip);
        String refreshTokenInRedis = redisService.getValues(redisKey);

        redisService.getValues(redisKey);
        if (refreshTokenInRedis != null) {
            System.out.println("Redis에 저장되어 있는 RT 삭제");
            redisService.deleteValues(redisKey);
        }

        // Redis에 로그아웃 처리한 AT 저장
        long expiration = JWT_PROVIDER.getTokenExpirationTime(accessToken, JWT_PROVIDER.ACCESS_KEY)
                - LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        redisService.setValuesWithTimeout(accessToken, "logout", expiration);

        mvc.perform(
                post("/api/oauth/logout")
                        .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("SignController - 회원가입")
    void signUp() throws Exception {
        SignUpDto signUpDto = SignUpDto.builder()
                .uid("rlahfld54")
                .upw("0000")
                .email("rlahfld54@naver.com")
                .name("황주은")
                .birth_date("1998-06-12")
                .phone("01025521549")
                .gender(1)
                .user_address("대구광역시")
                .role("ROLE_USER")
                .build();

        // jackson objectmapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String signup = objectMapper.writeValueAsString(signUpDto);
        System.out.println("signup : " + signup);

        SignUpResultDto result = new SignUpResultDto();
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg("성공~!!!");

        given(service.signUp(signUpDto)).willReturn(result);

        mvc.perform(
                post("/api/user")
                        .content(signup)
                        .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andDo(print());

        verify(service).signUp(signUpDto);
    }

//    @Test // 리프레쉬 토큰...일단 로그인을 한 생태로 해야하는데 401 에러
//    @DisplayName("SignController - 리프레쉬 토큰 요청")
//    void refreshToken() throws Exception{
//        UserRefreshToken userRefreshToken = new UserRefreshToken();
//        userRefreshToken.setRefreshToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY5MTYzOTUwMywiZXhwIjoxNjkyOTM1NTAzfQ.ynFAAxFl-78mFCto5afzqmLQqmzRAaQ7bJgkECfAit4");
//        // jackson objectmapper 객체 생성
//        ObjectMapper objectMapper = new ObjectMapper();
//        String result = objectMapper.writeValueAsString(userRefreshToken);
//        System.out.println("userRefreshToken : " + userRefreshToken);
//
//        SignInResultDto signInResultDto = SignInResultDto.builder()
//                .refreshToken(userRefreshToken.getRefreshToken())
//                .build();
//
//        given(service.refreshToken(request,userRefreshToken.getRefreshToken())).willReturn(signInResultDto);
//
//        mvc.perform(
//                post("/api/oauth/token")
//                        .content(result)
//                        .contentType("application/json")
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        verify(service.refreshToken(request,userRefreshToken.getRefreshToken()));
//
//    }

    @Test
    @DisplayName("SignController - 아이디 찾기")
    void searchID() throws Exception {
        String name = "황주은";
        String phone = "01025521549";
        String birth = "1998-06-12";

        given(service.searchID(name, phone, birth)).willReturn("rlahfld54");

        mvc.perform(
                get("/api/search/id")
                        .param("name",name)
                        .param("phone",phone)
                        .param("birth",birth)
        ).andExpect(status().isOk())
                .andDo(print());

        verify(service).searchID(name, phone, birth);
    }

    @Test
    @DisplayName("SignController - 본인 회원탈퇴")
    void deleteUser() throws Exception {
        int iuser = 7;

        given(service.deleteUser(iuser)).willReturn(1);

        mvc.perform(delete("/api/user/delete").param("iuser", String.valueOf(iuser)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service).deleteUser(iuser);
    }

    @Test
    @DisplayName("SignController - 본인 회원정보 조회")
    void getmyInfo() throws Exception {
        UserInfo user = new UserInfo();
        user.setIuser(7);
        user.setUser_id("rlahfld54");
        user.setName("황주은");
        given(service.getmyInfo()).willReturn(user);

        mvc.perform(
                get("/api/user/me")
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("SignController - 회원정보 수정")
    void updateUserInfo() throws Exception{
        UpdateUserInfoDto updateUserInfoDto = UpdateUserInfoDto.builder()
                .name("황주은")
                .email("rlahfld54@naver.com")
                .birth_date("1998-06-12")
                .phone("01025521549")
                .user_address("대구광역시 남구")
                .user_address_detail("니 마음속(하트)")
                .build();

//        given(service.updateUserInfo(updateUserInfoDto)).willReturn(1);

        // jackson objectmapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(updateUserInfoDto);
        System.out.println("result : " + result);

        mvc.perform(
                post("/api/user/update-profile")
                        .content(result)
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("SignController - 비밀번호 찾기")
    void searchPW() throws Exception {
        String id = "rlahfld54";
        String name = "황주은";
        String email = "rlahfld54@gmail.com";


        given(service.searchPW(id,name,email)).willReturn(1);

        mvc.perform(
                        get("/api/search/pw")
                                .param("id",id)
                                .param("name",name)
                                .param("email",email)
                ).andExpect(status().isOk())
                .andDo(print());

        verify(service).searchPW(id,name,email);
    }
}