package com.green.campingsmore.order.payment;

import com.green.campingsmore.CharEncodingConfiguration;
import com.green.campingsmore.order.cart.CartMapper;
import com.green.campingsmore.order.payment.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(CharEncodingConfiguration.class)
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
        PayDetailInfoVo item1 = new PayDetailInfoVo(5L, 11L, 16500 * 11L);
        PayDetailInfoVo item2 = new PayDetailInfoVo(6L, 7L, 39800L * 7L);
        itemList.add(item1);
        itemList.add(item2);

        Long result = mapper.insPayDetailInfo(itemList);
        assertEquals(2L, result);
        log.info("영향 받은 행 : {}줄", result);
    }

    @Test
    @DisplayName("PayMapper - 결제완료 시 뜨는 정보")
    void selPaymentComplete() {
        Long iorder = 1L;

        PaymentCompleteDto item = mapper.selPaymentComplete(iorder);

        assertEquals(40000L, item.getTotalPrice());
        assertEquals("서울특별시 마포구 상암동 495-81", item.getAddress());
        assertEquals(null, item.getAddressDetail());
        assertEquals("맛있는고기", item.getShippingMemo());
    }

    @Test
    @DisplayName("PayMapper - 한 유저의 전체 결제 내역")
    void selPaymentDetailAll1() {
        Long iuser = 1L;
        List<SelPaymentDetailDto> itemList1 = mapper.selPaymentDetailAll1(iuser);
        SelPaymentDetailDto item1 = itemList1.get(0);
        assertEquals(1L, item1.getIorder());

        //리스트 크기 비교
        assertEquals(2, item1.getItemList().size());

        //1번 리스트
        assertEquals(1L,item1.getItemList().get(0).getIitem());
        assertEquals("양의나라 유기농 양고기 양갈비 양꼬치 프렌치렉 숄더랙 캠핑 냉장 냉동"
                , item1.getItemList().get(0).getName());
        assertEquals(30000L,item1.getItemList().get(0).getTotalPrice());
        assertEquals("2023-07-25",item1.getItemList().get(0).getPaymentDate().toString());
        assertEquals("https://shopping-phinf.pstatic.net/main_8014052/80140522706.10.jpg"
                , item1.getItemList().get(0).getPic());

        //2번 리스트
        assertEquals(2L,item1.getItemList().get(1).getIitem());
        assertEquals("3개월미만 프렌치랙- 양갈비 양고기 밀키트 캠핑 쉽새끼 프랜치랙 프렌치렉 수육"
                , item1.getItemList().get(1).getName());
        assertEquals(10000L,item1.getItemList().get(1).getTotalPrice());
        assertEquals("2023-07-25",item1.getItemList().get(1).getPaymentDate().toString());
        assertEquals("https://shopping-phinf.pstatic.net/main_8211829/82118298959.5.jpg"
                , item1.getItemList().get(1).getPic());
    }

    @Test
    @DisplayName("PayMapper - 장바구니에 체크된 아이템리스트 정보")
    void selPaymentPageItemList() {
        List<Long> cartlist = new ArrayList();
        cartlist.add(1L);
        cartlist.add(2L);

        List<PaymentDetailDto> itemList = mapper.selPaymentPageItemList(cartlist);
        //리스트 크기 비교
        assertEquals(2, itemList.size());

        //1번 리스트
        assertEquals(1L,itemList.get(0).getIitem());
        assertEquals("양의나라 유기농 양고기 양갈비 양꼬치 프렌치렉 숄더랙 캠핑 냉장 냉동"
                , itemList.get(0).getName());
        assertEquals(16500L,itemList.get(0).getPrice());
        assertEquals(1L,itemList.get(0).getQuantity());
        assertEquals("https://shopping-phinf.pstatic.net/main_8014052/80140522706.10.jpg"
                ,itemList.get(0).getPic());

        //2번 리스트
        assertEquals(2L,itemList.get(1).getIitem());
        assertEquals("3개월미만 프렌치랙- 양갈비 양고기 밀키트 캠핑 쉽새끼 프랜치랙 프렌치렉 수육"
                , itemList.get(1).getName());
        assertEquals(39800L,itemList.get(1).getPrice());
        assertEquals(2L,itemList.get(1).getQuantity());
        assertEquals("https://shopping-phinf.pstatic.net/main_8211829/82118298959.5.jpg"
                ,itemList.get(1).getPic());
    }

    @Test
    @DisplayName("PayMapper - 아이템 구매 클릭시 넘기는 정보")
    void selPaymentPageItem() {
        Long iitem = 1L;

        PaymentDetailDto item = mapper.selPaymentPageItem(iitem);

        assertEquals(1L,item.getIitem());
        assertEquals("양의나라 유기농 양고기 양갈비 양꼬치 프렌치렉 숄더랙 캠핑 냉장 냉동"
                , item.getName());
        assertEquals(16500L,item.getPrice());
        assertEquals("https://shopping-phinf.pstatic.net/main_8014052/80140522706.10.jpg"
                ,item.getPic());

    }

    @Test
    @DisplayName("PayMapper - 전체 결제 내역에서 하나의 결제 내역 삭제(아이템별, 마이 페이지)")
    void delPaymentDetail() {
        Long iuser = 1L;
        Long iitem = 1L;
        Long delPaymentDetail = mapper.delPaymentDetail(iuser, iitem);

        assertEquals(1L, delPaymentDetail);
        log.info("delete 성공여부 : {} (1 = 성공, 0 = 실패)", delPaymentDetail);
    }

    @Test
    @DisplayName("PayMapper - 상세 결제 내역 보기(마이 페이지)")
    void selDetailedItemPaymentInfo() {
        Long iorder = 1L;
        Long iitem = 1L;

        SelDetailedItemPaymentInfoVo item = mapper.selDetailedItemPaymentInfo(iorder, iitem);

        assertEquals(1L,item.getIitem());
        assertEquals("양의나라 유기농 양고기 양갈비 양꼬치 프렌치렉 숄더랙 캠핑 냉장 냉동"
                ,item.getName());
        assertEquals(30000L,item.getPrice());
        assertEquals(1L,item.getQuantity());
        assertEquals("https://shopping-phinf.pstatic.net/main_8014052/80140522706.10.jpg",item.getPic());
        assertEquals("2023-07-25",item.getPaymentDate().toString());
        assertEquals("서울특별시 마포구 상암동 495-81",item.getAddress());
        assertEquals(null,item.getAddressDetail());
        assertEquals(3000L,item.getShippingPrice());
        assertEquals("맛있는고기",item.getShippingMemo());
    }

    @Test
    @DisplayName("PayMapper - 배송지 삭제")
    void delAddress() {
        Long iaddress = 1L;
        Long result = mapper.delAddress(iaddress);

        assertEquals(1L, result);
        log.info("배송지 삭제 성공여부 : {} (1 = 성공, 0 = 실패)", result);
    }
}