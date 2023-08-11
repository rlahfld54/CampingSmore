package com.green.campingsmore.order.cart;

import com.green.campingsmore.IntegrationTest;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.order.cart.model.InsCartDto;
import com.green.campingsmore.order.cart.model.SelCartVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class CartIntegrationTest extends IntegrationTest {

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
                .iuser(2L)
                .name("신형주")
                .roles(roles)
                .build();
        return userDetails;
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Cart - 장바구니에 등록하기")
    public void postCart() throws Exception {
        InsCartDto item = new InsCartDto();
        item.setIitem(3L);
        item.setQuantity(20L);

        String jsonItem = om.writeValueAsString(item);

        MvcResult mr = mvc.perform(post("/api/cart")
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
    @DisplayName("Cart - 장바구니에 목록 보기")
    public void getCart() throws Exception {
        MvcResult mr = mvc.perform(get("/api/cart"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, List.class);
        log.info("result : {}", content);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("Cart - 장바구니 목록 삭제")
    public void delCart() throws Exception {
        Long icart = 1L;
        MvcResult mr = mvc.perform(delete("/api/cart/{icart}", icart))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, Long.class);
        log.info("result : {}", content);

    }

//    @Test
//    @Rollback(value = false)
//    @DisplayName("Cart - 장바구니 목록 삭제")
//    public void delCartAll() throws Exception {
//        List<Long> icart = new ArrayList<>();
//        icart.add(1L);
//        Long icart = 1L;
//        MvcResult mr = mvc.perform(delete("/api/cart")
//                        .param("icart", String.valueOf(icart)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        String content = mr.getResponse().getContentAsString();
//        om.readValue(content, Long.class);
//        log.info("result : {}", content);
//
//    }

}
