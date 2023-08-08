package com.green.campingsmore.sign;

import com.green.campingsmore.MockMvcConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcConfig
@WebMvcTest(SignController.class)
@AutoConfigureMockMvc(addFilters = false)
class SignControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SignService service;

    @Test // 로그인
    @DisplayName("로그인")
    void signIn() throws Exception {
//        mvc.perform(
//                get("/sign-in")
//
//                )
//                .andExpect(status().isOk());
    }

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