package com.green.campingsmore.order.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.MockMvcConfig;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.order.cart.CartController;
import com.green.campingsmore.order.cart.CartMapper;
import com.green.campingsmore.order.cart.CartService;
import com.green.campingsmore.order.cart.CartServiceImpl;
import com.green.campingsmore.order.cart.model.InsCartDto;
import com.green.campingsmore.order.cart.model.InsCartDto1;
import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.PayDetailInfoVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcConfig
@WebMvcTest(PayController.class)
@AutoConfigureMockMvc(addFilters = false)
class PayControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PayService service;

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
    @DisplayName("PayController - 결제 정보 저장")
    void postPayInfo() throws Exception {
        //given
        Long testResult = 1L;
        given(service.insPayInfo(any())).willReturn(testResult);

        //when
        InsPayInfoDto item = new InsPayInfoDto();
        item.setAddress("주소주소주소");
        item.setAddressDetail("상세주소주소");
        item.setTotalPrice(50000L);
        item.setShippingPrice(3000L);
        item.setShippingMemo("빨리주세요");

        List<PayDetailInfoVo> itemList = new ArrayList<>();
        PayDetailInfoVo item1 = new PayDetailInfoVo(1L, 2L, 25000L);
        PayDetailInfoVo item2 = new PayDetailInfoVo(2L, 5L, 25000L);

        itemList.add(item1);
        itemList.add(item2);

        item.setPurchaseList(itemList);

        ObjectMapper om = new ObjectMapper();
        String jsonItem = om.writeValueAsString(item);

        ResultActions ra = mvc.perform(post("/api/payment")
                .content(jsonItem)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        ra.andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());

        then(service).should(times(1)).insPayInfo(any());
    }

    @Test
    void insAddress() {
        Long testResult = 1L;
        given(service.insAddress(any())).willReturn(testResult);


    }

    @Test
    void selUserAddress() {
    }

    @Test
    void selAddressList() {
    }

    @Test
    void selOneAddress() {
    }
}