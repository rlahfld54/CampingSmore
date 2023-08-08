package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.cart.CartMapper;
import com.green.campingsmore.order.payment.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PayMapperTest {

    @Autowired
    private PayMapper mapper;

    @Test
    @DisplayName("PayMapper - 결제 정보 저장")
    void insPayInfo() {
        InsPayInfoDto1 item = new InsPayInfoDto1();
        item.setIuser(1L);
        item.setAddress("대구시 동성로");
        item.setAddressDetail("칸다소바 1층");
        item.setTotalPrice(50000L);
        item.setShippingPrice(3000L);
        item.setShippingMemo("빨리 주세요 현기증 난단 말이에요");

        Long result = mapper.insPayInfo(item);
        assertEquals(1L, result);
        log.info("영향 받은 행 : {}줄", result);
    }

    @Test
    @DisplayName("PayMapper - 결제 상세 정보 저장")
    void insPayDetailInfo() {
        List<PayDetailInfoVo> itemList = new ArrayList<>();
        PayDetailInfoVo item1 = new PayDetailInfoVo(5L,11L,16500*11L);
        PayDetailInfoVo item2 = new PayDetailInfoVo(6L,7L,39800L*7L);
        itemList.add(item1);
        itemList.add(item2);

        Long result = mapper.insPayDetailInfo(itemList);
        assertEquals(2L,result);
        log.info("영향 받은 행 : {}줄", result);
    }

    @Test
    @DisplayName("PayMapper - 결제완료 시 뜨는 정보")
    void selPaymentComplete() {
        Long iorder = 1L;

        PaymentCompleteDto item = mapper.selPaymentComplete(iorder);

        assertEquals(40000L,item.getTotalPrice());
        assertEquals("서울특별시 마포구 상암동 495-81",item.getAddress());
        assertEquals(null,item.getAddressDetail());
        assertEquals("맛있는고기",item.getShippingMemo());
    }

    @Test
    void selPaymentDetailAll1() {
        Long iuser = 1L;
        List<SelPaymentDetailDto> itemList1 = mapper.selPaymentDetailAll1(iuser);
        for (SelPaymentDetailDto item1 : itemList1) {
            List<PaymentDetailDto2> itemList2 = item1.getItemList();
            for (PaymentDetailDto2 item2 : itemList2) {

            }
        }

    }

    @Test
    void selPaymentPageItemList() {
    }

    @Test
    void selPaymentPageItem() {
    }

    @Test
    void delPaymentDetail() {
    }

    @Test
    void selDetailedItemPaymentInfo() {
    }
}