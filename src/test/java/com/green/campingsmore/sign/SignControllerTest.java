package com.green.campingsmore.sign;

import com.green.campingsmore.MockMvcConfig;
import com.green.campingsmore.order.cart.CartController;
import com.green.campingsmore.order.cart.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@MockMvcConfig
@WebMvcTest(SignController.class)
@AutoConfigureMockMvc(addFilters = false)
class SignControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SignService service;

    @Test
    void signIn() {
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