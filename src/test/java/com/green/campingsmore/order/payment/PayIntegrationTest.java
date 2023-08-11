//package com.green.campingsmore.order.payment;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.green.campingsmore.IntegrationTest;
//import com.green.campingsmore.config.security.model.MyUserDetails;
//import com.green.campingsmore.order.cart.model.InsCartDto;
//import com.green.campingsmore.order.payment.model.InsPayInfoDto;
//import com.green.campingsmore.order.payment.model.PayDetailInfoVo;
//import com.green.campingsmore.order.payment.model.ShippingInsDto;
//import com.green.campingsmore.order.payment.model.ShippingInsDto1;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Slf4j
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class PayIntegrationTest extends IntegrationTest {
//
//    @BeforeEach
//    void beforeEach() {
//
//        UserDetails user = createUserDetails();
//
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
//    }
//
//    private UserDetails createUserDetails() {
//        List<String> roles = new ArrayList<>();
//        roles.add("ROLE_USER");
//
//        UserDetails userDetails = MyUserDetails.builder()
//                .iuser(1L)
//                .name("신형주")
//                .roles(roles)
//                .build();
//        return userDetails;
//    }
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("Payment - 결제 정보 저장하기")
//    void postPayInfo() throws Exception {
//        InsPayInfoDto item = new InsPayInfoDto();
//        item.setAddress("주소주소주소");
//        item.setAddressDetail("상세주소주소");
//        item.setTotalPrice(50000L);
//        item.setShippingPrice(3000L);
//        item.setShippingMemo("빨리주세요");
//
//        List<PayDetailInfoVo> itemList = new ArrayList<>();
//        PayDetailInfoVo item1 = new PayDetailInfoVo(1L, 2L, 25000L);
//        PayDetailInfoVo item2 = new PayDetailInfoVo(2L, 5L, 25000L);
//
//        itemList.add(item1);
//        itemList.add(item2);
//
//        item.setPurchaseList(itemList);
//        String jsonItem = om.writeValueAsString(item);
//
//        MvcResult mr = mvc.perform(post("/api/payment")
//                .content(jsonItem)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        String content = mr.getResponse().getContentAsString();
//        om.readValue(content, Long.class);
//        log.info("result : {}", content);
//    }
//
//    @Test
//    void getPaymentComplete() {
//    }
//
//    @Test
//    void getPaymentList() {
//    }
//
//    @Test
//    void getDetailedItemPaymentInfo() {
//    }
//
//    @Test
//    void delPaymentDetail() {
//    }
//
//    @Test
//    void getPaymentItemList() {
//    }
//
//    @Test
//    void getPaymentItem() {
//    }
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("Payment - 결제 정보 저장하기")
//    void insAddress() throws Exception {
//        ShippingInsDto1 item = new ShippingInsDto1();
//        item.setAddress("주소주소주소");
//        item.setAddressDetail("상세주소주소");
//        item.setName("신형주");
//        item.setPhone("01022228888");
//        item.setIuser(user.getIuser());
//createUserDetails().getAuthorities().
//        String jsonItem = om.writeValueAsString(item);
//
//        MvcResult mr = mvc.perform(post("/api/payment/address")
//                        .content(jsonItem)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        String content = mr.getResponse().getContentAsString();
//        om.readValue(content, Long.class);
//        log.info("result : {}", content);
//    }
//
//    @Test
//    void selUserAddress() {
//    }
//
//    @Test
//    void selAddressList() {
//    }
//
//    @Test
//    void selOneAddress() {
//    }
//
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("Cart - 장바구니에 등록하기")
//    public void postCart() throws Exception {
//        InsCartDto item = new InsCartDto();
//        item.setIitem(3L);
//        item.setQuantity(20L);
//
//        String jsonItem = om.writeValueAsString(item);
//
//        MvcResult mr = mvc.perform(post("/api/cart")
//                .content(jsonItem)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        String content = mr.getResponse().getContentAsString();
//        om.readValue(content, Long.class);
//        log.info("result : {}", content);
//    }
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("Cart - 장바구니에 목록 보기")
//    public void getCart() throws Exception {
//        MvcResult mr = mvc.perform(get("/api/cart"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        String content = mr.getResponse().getContentAsString();
//        om.readValue(content, List.class);
//        log.info("result : {}", content);
//    }
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("Cart - 장바구니 목록 삭제")
//    public void delCart() throws Exception {
//        Long icart = 1L;
//        MvcResult mr = mvc.perform(delete("/api/cart/{icart}", icart))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        String content = mr.getResponse().getContentAsString();
//        om.readValue(content, Long.class);
//        log.info("result : {}", content);
//
//    }
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("Cart - 체크된 장바구니 정보들 삭제")
//    public void delCartAll() throws Exception {
//        List<Long> icart = new ArrayList<>();
//        icart.add(1L);
//        icart.add(2L);
//        Long testIcart = 1L;
//        MvcResult mr = mvc.perform(delete("/api/cart")
//                        .param("icart", String.valueOf(testIcart)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        String content = mr.getResponse().getContentAsString();
//        om.readValue(content, Long.class);
//        log.info("result : {}", content);
//
//    }
//
//}
