package com.green.campingsmore.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.MockMvcConfig;
import com.green.campingsmore.sign.model.UserLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

@MockMvcConfig
@WebMvcTest(SignController.class)
@AutoConfigureMockMvc(addFilters = false)
class SignControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SignService service;

//    @Test // 로그인
//    @DisplayName("로그인")
//    void signIn() throws Exception {
//        mvc.perform(post("/sign-in")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }

    @Test
    void logout() {
    }

    @Test
    void signUp() {
    }

    @Test
    void refreshToken() {
    }

    @Test
    void searchID() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUserInfo() {
    }

    @Test
    void searchPW() {
    }
}