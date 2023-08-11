package com.green.campingsmore.order.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.MockMvcConfig;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.order.cart.model.InsCartDto;
import com.green.campingsmore.order.cart.model.SelCartVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcConfig
@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false)
class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService service;

    @BeforeEach
    void beforeEach() {

        UserDetails user = createUserDetails();

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));

    }

    private UserDetails createUserDetails() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        UserDetails userDetails = MyUserDetails.builder()
                .iuser(10L)
                .name("김철수")
                .roles(roles)
                .build();
        return userDetails;
    }

    @Test
    @DisplayName("CartController - 장바구니 정보 저장")
    void postCart() throws Exception {
        //given
        Long testResult = 1L;
        given(service.insCart(any())).willReturn(testResult);

        //when
        InsCartDto item = new InsCartDto();
        item.setIitem(5L);
        item.setQuantity(10L);

        ObjectMapper om = new ObjectMapper();
        String jsonItem = om.writeValueAsString(item);

        ResultActions ra = mvc.perform(post("/api/cart")
                .content(jsonItem)
                .contentType(MediaType.APPLICATION_JSON));


        //then
        ra.andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());

        then(service).should(times(1)).insCart(any());
    }

    @Test
    @DisplayName("CartController - 장바구니 정보 보여주기")
    void getCart() throws Exception{
        //given
        List<SelCartVo> mockList = new ArrayList<>();
        mockList.add(new SelCartVo(1L, "양고기사진.jpg", "양고기 구이",  23000L, 5L));
        mockList.add(new SelCartVo(2L, "소고기사진.jpg", "소고기 전골",  30000L, 3L));
        given(service.selCart(anyLong())).willReturn(mockList);

        //when
        ResultActions ra = mvc.perform(get("/api/cart"));

        // then
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockList.size())))
                .andExpect(jsonPath("$[*].icart").exists())
                .andExpect(jsonPath("$[0].icart").value(1L))
                .andExpect(jsonPath("$[0].pic").value("양고기사진.jpg"))
                .andExpect(jsonPath("$[0].name").value("양고기 구이"))
                .andExpect(jsonPath("$[0].price").value(23000L))
                .andExpect(jsonPath("$[0].quantity").value(5L))

                .andExpect(jsonPath("$[1].icart").value(2L))
                .andExpect(jsonPath("$[1].pic").value("소고기사진.jpg"))
                .andExpect(jsonPath("$[1].name").value("소고기 전골"))
                .andExpect(jsonPath("$[1].price").value(30000L))
                .andExpect(jsonPath("$[1].quantity").value(3L))
                .andDo(print());

        then(service).should(times(1)).selCart(anyLong());
    }

    @Test
    @DisplayName("CartController - 장바구니 하나 삭제")
    void delCart() throws Exception{
        //get
        Long mockIcart = 2L;
        given(service.delCart(mockIcart)).willReturn(1L);

        //when
        ResultActions ra = mvc.perform(delete("/api/cart/{mockIcart}", mockIcart));

        //then
        ra.andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());

        then(service).should(times(1)).delCart(anyLong());
    }

    @Test
    @DisplayName("CartController - 장바구니 선택 삭제")
    void delCartAll() throws Exception{
        //get
        List<Long> mockIcartList = new ArrayList<>();
        mockIcartList.add(1L);
        mockIcartList.add(2L);

        given(service.delCartAll(mockIcartList)).willReturn(2L);

        //when
        String[] strMockList = {
                String.valueOf(mockIcartList.get(0)),
                String.valueOf(mockIcartList.get(1))
        };

        ResultActions ra = mvc.perform(delete("/api/cart")
                .param("icart", strMockList));

        //then
        ra.andExpect(status().isOk())
                .andExpect(content().string("2"))
                .andDo(print());

        then(service).should(times(1)).delCartAll(any());
    }
}