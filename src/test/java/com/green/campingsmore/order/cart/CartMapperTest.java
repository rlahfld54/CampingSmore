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
        assertEquals(2, list.size());

        SelCartVo item1 = list.get(0);
        assertEquals(1L, item1.getIcart());
        log.info("첫번째 cart pk : {}", item1.getIcart());

        assertNotNull(item1.getPrice());
        assertEquals(16500L, item1.getPrice());
        log.info("첫번째 cart itemPrice : {}", item1.getPrice());

        assertNotNull(item1.getQuantity());
        assertEquals(1L, item1.getQuantity());
        log.info("첫번째 cart itemQuantity : {}", item1.getQuantity());

        assertNotNull(item1.getPic());
        assertEquals("https://shopping-phinf.pstatic.net/main_8014052/80140522706.10.jpg", item1.getPic());
        log.info("첫번째 cart itemPic : {}", item1.getPic());

        assertNotNull(item1.getName());
        assertEquals("양의나라 유기농 양고기 양갈비 양꼬치 프렌치렉 숄더랙 캠핑 냉장 냉동", item1.getName());
        log.info("첫번째 cart itemName : {}", item1.getName());

        //----

        SelCartVo item2 = list.get(1);
        assertEquals(2L, item2.getIcart());
        log.info("두번째 cart pk : {}", item2.getIcart());
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
        Long iuser = 1L;
        Long delCart = mapper.delCart(iuser);

        assertEquals(1L, delCart);
        log.info("Cart delete 성공여부 : {} (1 = 성공, 0 = 실패)", delCart);
    }
}