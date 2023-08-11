package com.green.campingsmore.order.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.IntegrationTest;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.order.cart.model.InsCartDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
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
                .iuser(1L)
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
        item.setQuantity(2L);

        String jsonItem = om.writeValueAsString(item);

        MvcResult mr = mvc.perform(post("/api/cart")
                .content(jsonItem)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        om.readValue(content, Long.class);
        log.info("Long : {}", content);
    }


}
