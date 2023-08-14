package com.green.campingsmore.order.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.green.campingsmore.IntegrationTest;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.order.cart.model.InsCartDto;
import com.green.campingsmore.order.payment.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PayIntegrationTest extends IntegrationTest {

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
                .iuser(1L)
                .name("신형주")
                .roles(roles)
                .build();
        return userDetails;
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 결제 정보 저장하기")
    void postPayInfo() throws Exception {
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
        String jsonItem = om.writeValueAsString(item);

        MvcResult mr = mvc.perform(post("/api/payment")
                        .content(jsonItem)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, Long.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 결제 내역 보기")
    void getPaymentComplete() throws Exception {
        Long iorder = 1L;
        MvcResult mr = mvc.perform(get("/api/payment/{iorder}", iorder))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, PaymentCompleteDto.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 전체 결제 내역 보기(마이 페이지)")
    void getPaymentList() throws Exception {
        MvcResult mr = mvc.perform(get("/api/payment/paymentList"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, List.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 상세 결제 내역 보기(마이 페이지)")
    void getDetailedItemPaymentInfo() throws Exception {
        Long iorder = 1L;
        Long iitem = 1L;

        MvcResult mr = mvc.perform(get("/api/payment/paymentList/detail/{iorder}", iorder)
                        .param("iitem", iitem.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, SelDetailedItemPaymentInfoVo.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 전체 결제 내역에서 하나의 결제 내역 삭제(아이템별, 마이 페이지)")
    void delPaymentDetail() throws Exception {

        Long iorder = 1L;
        Long iitem = 1L;

        MvcResult mr = mvc.perform(put("/api/payment/paymentList/{iorder}", iorder)
                        .param("iitem", iitem.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, Long.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 장바구니 결제 버튼 -> 체크된 장바구니 아이템 정보들을 결제 페이지에서 보여주기")
    void getPaymentItemList() throws Exception {
        CartPKDto icartList = new CartPKDto();
        List<Long> icart = new ArrayList<>();
        icart.add(1L);
        icart.add(2L);

        icartList.setIcart(icart);

        String jsonItem = om.writeValueAsString(icartList);


        MvcResult mr = mvc.perform(post("/api/payment/order/cart")
                        .content(jsonItem)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, List.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 아이템 구매 버튼 -> 단일 아이템 정보를 결제 페이지에서 보여주기")
    void getPaymentItem() throws Exception {
        Long iitem = 1L;
        Long quantity = 2L;

        MvcResult mr = mvc.perform(get("/api/payment/order/{iitem}", iitem)
                        .param("quantity", quantity.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, PaymentDetailDto.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 배송지 추가 등록")
    void insAddress() throws Exception {
        MyUserDetails user = (MyUserDetails) createUserDetails();
        ShippingInsDto1 item = new ShippingInsDto1();
        item.setAddress("주소주소주소");
        item.setAddressDetail("상세주소주소");
        item.setName("신형주");
        item.setPhone("01022228888");
        item.setIuser(user.getIuser());

        String jsonItem = om.writeValueAsString(item);

        MvcResult mr = mvc.perform(post("/api/payment/address")
                        .content(jsonItem)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, Long.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 기본 배송지(유저 주소) 출력")
    void selUserAddress() throws Exception {

        MvcResult mr = mvc.perform(get("/api/payment/address"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, SelUserAddressVo.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 등록된 배송지 리스트 출력")
    void selAddressList() throws Exception {
        MvcResult mr = mvc.perform(get("/api/payment/addressList"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, List.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 등록된 배송지 중 선택한 배송지 정보 출력")
    void selOneAddress() throws Exception {
        Long iaddress = 1L;
        MvcResult mr = mvc.perform(get("/api/payment/addressList/{iaddress}", iaddress))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, ShippingListSelVo.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Payment - 등록된 배송지 제거")
    void delAddress() throws Exception {
        Long iaddress = 1L;
        MvcResult mr = mvc.perform(delete("/api/payment/address/{iaddress}", iaddress))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, Long.class);
        log.info("result : {}", content);
    }
}
