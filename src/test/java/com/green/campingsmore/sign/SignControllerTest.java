package com.green.campingsmore.sign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.config.security.JwtTokenProvider;
import com.green.campingsmore.config.security.SecurityConfiguration;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.config.security.model.SignUpDto;
import com.green.campingsmore.config.security.redis.RedisService;
import com.green.campingsmore.config.security.redis.model.RedisJwtVo;
import com.green.campingsmore.sign.model.SignInResultDto;
import com.green.campingsmore.sign.model.SignUpResultDto;
import com.green.campingsmore.sign.model.UserLogin;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.Collections;
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
    private SignService service;

    @MockBean
    private RedisService redisService;

    @MockBean
    private JwtTokenProvider JWT_PROVIDER;

    @BeforeEach
    void beforeEach() {
        UserDetails user = createUserDetails();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));

    }

    private UserDetails createUserDetails() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        //roles.add("ROLE_ADMIN");
        Long IUSER = 7L; //  "rlahfld54" , iuser : 7

        UserDetails userDetails = MyUserDetails.builder()
                .iuser(IUSER)
                .uid("rlahfld54")
                .upw("0000")
                .roles(roles)
                .build();
        return userDetails;
    }


    @Test // 로그인
    void signIn() throws Exception {
        UserLogin userLogin = new UserLogin();
        userLogin.setUid("rlahfld54");
        userLogin.setUpw("0000");

        // jackson objectmapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String user_info = objectMapper.writeValueAsString(userLogin);
        System.out.println("user_info : " + user_info);

        String accessToken = JWT_PROVIDER.generateJwtToken("7", Collections.singletonList("ROLE_USER"), JWT_PROVIDER.ACCESS_TOKEN_VALID_MS, JWT_PROVIDER.ACCESS_KEY);
        String refreshToken = JWT_PROVIDER.generateJwtToken("7", Collections.singletonList("ROLE_USER"), JWT_PROVIDER.REFRESH_TOKEN_VALID_MS, JWT_PROVIDER.REFRESH_KEY);

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
                post("/sign-api/sign-in")
                        .content(user_info)
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(service).signIn(userLogin,"127.0.0.1");

    }

    @Test
    void logout() {
    }

    @Test // 회원가입
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
                post("/sign-api/sign-up")
                        .content(signup)
                        .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andDo(print());

        verify(service).signUp(signUpDto);
    }

    @Test
    void refreshToken() {
    }

    @Test
    void searchID() {
    }

    @Test
    void deleteUser() throws Exception {
        int iuser = 7;

        given(service.deleteUser(iuser)).willReturn(1);

        mvc.perform(delete("/sign-api/delete-user").param("iuser", String.valueOf(iuser)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service).deleteUser(iuser);
    }

    @Test
    void getmyInfo() {
    }

    @Test
    void updateUserInfo() {
    }

    @Test
    void searchPW() {
    }
}