package com.green.campingsmore.order.cart;

import com.green.campingsmore.order.cart.model.InsCartDto2;
import com.green.campingsmore.order.cart.model.SelCartVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartMapperTest {

    @Autowired
    private CartMapper mapper;

    @Test
    @DisplayName("CartMapper - 장바구니 목록 보여주기")
    void selCart() {
        Long iuser = 1L;
        List<SelCartVo> list = mapper.selCart(iuser);
        assertEquals(2,list.size());

        SelCartVo item1 = list.get(0);
        assertEquals(1L, item1.getIcart());
        assertEquals(1L, item1.getIcart());
    }

    @Test
    @DisplayName("CartMapper - 장바구니 정보 저장")
    void insCart() {
        log.info("-----수량 기입을 안했을 경우-----");
        InsCartDto2 dto1 = new InsCartDto2();
        dto1.setIuser(1L);
        dto1.setIitem(1L);

        Long result1 = mapper.insCart(dto1);
        assertEquals(1L, result1);
        log.info("영향 받은 행 : {}줄", result1);

        assertEquals(16, dto1.getIcart());
        log.info("INSERT 행의 pk값 : {}", dto1.getIcart());

        log.info("-----수량 기입을 했을 경우-----");
        InsCartDto2 dto2 = new InsCartDto2();
        dto2.setIuser(2L);
        dto2.setIitem(2L);
        dto2.setQuantity(2L);

        Long result2 = mapper.insCart(dto2);
        assertEquals(1, result2);
        log.info("영향 받은 행 : {}줄", result2);

        assertEquals(17, dto2.getIcart());
        log.info("INSERT 행의 pk값 : {}", dto2.getIcart());
    }

    @Test
    @DisplayName("CartMapper - 장바구니 내용 삭제")
    void delCart() {

    }
}