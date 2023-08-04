package com.green.campingsmore.order.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.MockMvcConfig;
import com.green.campingsmore.order.cart.model.InsCartDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcConfig
@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService service;

    @Test
    @DisplayName("CartController - 장바구니 정보 저장")
    void postCart() throws Exception {
        //given
        Long testResult = 1L;
        given(service.insCart(any())).willReturn(testResult);

        //when
        InsCartDto item = new InsCartDto();
        item.setIuser(3L);
        item.setIitem(5L);
        item.setQuantity(10L);

        ResultActions ra = mvc.perform(post("/api/cart"));


        //then
//        ra.andExpect(status().isOk())
//                .andExpect(content().string("1L"))
//                .andExpect()
//                .andDo(print());

        then(service).should(times(1)).insCart(any());
    }
}