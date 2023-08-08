package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({PayServiceImpl.class})
@Slf4j
class PayServiceTest {

    @MockBean
    private PayMapper mapper;

    @Autowired
    private PayService service;

    @Test
    @DisplayName("PayService - 결제 정보 저장")
    void insPayInfo() {
        InsPayInfoDto1 orderdto = new InsPayInfoDto1();
        orderdto.setIuser(1L);
        orderdto.setAddress("서울 분당동");
        orderdto.setAddressDetail("판자집 2층");
        orderdto.setTotalPrice(42000L);
        orderdto.setShippingPrice(3000L);

        List<PayDetailInfoVo> purchaseList = new ArrayList<>();
        PayDetailInfoVo item1 = new PayDetailInfoVo(1L, 2L, 33000L);
        PayDetailInfoVo item2 = new PayDetailInfoVo(7L, 2L, 9000L);
        purchaseList.add(item1);
        purchaseList.add(item2);

        Long testResult = 1L;

        given(mapper.insPayInfo(any())).willReturn(testResult);
        given(mapper.insPayDetailInfo(any())).willReturn(testResult);

        Long result1 = mapper.insPayInfo(orderdto);
        Long result2 = mapper.insPayDetailInfo(purchaseList);

        assertEquals(testResult, result1);
        assertEquals(testResult, result2);

        then(mapper).should(times(1)).insPayInfo(any());
        then(mapper).should(times(1)).insPayDetailInfo(any());
    }

    @Test
    @DisplayName("PayService - 결제 내역 보기")
    void selPaymentComplete() {
        Long iorder = 1L;
        PaymentCompleteDto testItem = new PaymentCompleteDto();
        testItem.setAddress("주소주소주소");
        testItem.setAddressDetail("상세주소주소");
        testItem.setTotalPrice(30000L);
        testItem.setShippingMemo("빨리주세요");

        given(mapper.selPaymentComplete(anyLong())).willReturn(testItem);

        PaymentCompleteDto item = mapper.selPaymentComplete(iorder);
        assertEquals(testItem.getAddress(), item.getAddress());
        assertEquals(testItem.getAddressDetail(), item.getAddressDetail());
        assertEquals(testItem.getTotalPrice(), item.getTotalPrice());
        assertEquals(testItem.getShippingMemo(), item.getShippingMemo());

        then(mapper).should(times(1)).selPaymentComplete(any());
    }

    @Test
    @DisplayName("PayService - 한 유저의 전체 결제 내역(마이 페이지)")
    void selPaymentDetailAll() {
        Long iuser = 1L;

        List<PaymentDetailDto2> itemList1 = new ArrayList<>();
        PaymentDetailDto2 item2 = new PaymentDetailDto2();
        item2.setIitem(1L);
        item2.setName("양의나라 유기농 양고기 양갈비 양꼬치 프렌치렉 숄더랙 캠핑 냉장 냉동");
        item2.setPaymentDate(LocalDate.ofEpochDay(2023 - 07 - 25));
        item2.setPic("https://shopping-phinf.pstatic.net/main_8014052/80140522706.10.jpg");
        item2.setTotalPrice(16500L);

        itemList1.add(item2);

        SelPaymentDetailDto item1 = new SelPaymentDetailDto();
        item1.setIorder(1L);
        item1.setItemList(itemList1);

        List<SelPaymentDetailDto> itemList = new ArrayList<>();
        itemList.add(item1);

        given(mapper.selPaymentDetailAll1(any())).willReturn(itemList);

        List<SelPaymentDetailDto> result = mapper.selPaymentDetailAll1(iuser);

        assertEquals(result.size(), 1L);
        assertEquals(result.get(0).getItemList().size(), 1L);

        assertEquals(item1.getIorder(), result.get(0).getIorder());
        assertEquals(item1.getItemList().get(0).getIitem()
                , result.get(0).getItemList().get(0).getIitem());
        assertEquals(item1.getItemList().get(0).getName()
                , result.get(0).getItemList().get(0).getName());
        assertEquals(item1.getItemList().get(0).getPic()
                , result.get(0).getItemList().get(0).getPic());
        assertEquals(item1.getItemList().get(0).getPaymentDate()
                , result.get(0).getItemList().get(0).getPaymentDate());
        assertEquals(item1.getItemList().get(0).getTotalPrice()
                , result.get(0).getItemList().get(0).getTotalPrice());

        then(mapper).should(times(1)).selPaymentDetailAll1(any());
    }

    @Test
    @DisplayName("PayService - 아이템 구매 클릭시 넘기는 정보")
    void selPaymentPageItem() {
        Long iitem = 1L;
        Long quantity = 1L;

        PaymentDetailDto item1 = new PaymentDetailDto();
        item1.setIitem(1L);
        item1.setPic("테스트지겹다.jpg");
        item1.setName("괴기괴기");
        item1.setPrice(70000L);

        List<PaymentDetailDto> itemList = new ArrayList<>();
        itemList.add(item1);

        given(mapper.selPaymentPageItem(iitem)).willReturn(item1);

        PaymentDetailDto result = mapper.selPaymentPageItem(iitem);
        Long price = result.getPrice();
        result.setQuantity(quantity);
        result.setTotalPrice(price * quantity);

        assertEquals(result.getPrice(), item1.getPrice());
        assertEquals(result.getQuantity(), 1L);
        assertEquals(result.getIitem(), item1.getIitem());
        assertEquals(result.getPic(), item1.getPic());
        assertEquals(result.getTotalPrice(), 70000L);
        assertEquals(result.getName(), item1.getName());

        then(mapper).should(times(1)).selPaymentPageItem(any());
    }

    @Test
    @DisplayName("PayService - 장바구니 결제 버튼 -> 체크된 장바구니 아이템 정보들을 결제 페이지에서 보여주기")
    void selPaymentPageItemList() {
        CartPKDto testDto = new CartPKDto();
        List<Long> icart = new ArrayList<>();
        icart.add(1L);
        icart.add(2L);
        testDto.setIcart(icart);

        List<PaymentDetailDto> itemList = new ArrayList<>();
        PaymentDetailDto item1 = new PaymentDetailDto();
        item1.setIitem(1L);
        item1.setPic("테스트지겹다.jpg");
        item1.setName("괴기괴기");
        item1.setPrice(50000L);
        item1.setQuantity(1L);

        PaymentDetailDto item2 = new PaymentDetailDto();
        item2.setIitem(2L);
        item2.setPic("맛있는 과자.jpg");
        item2.setName("새우깡");
        item2.setPrice(10000L);
        item2.setQuantity(2L);

        itemList.add(item1);
        itemList.add(item2);

        given(mapper.selPaymentPageItemList(any())).willReturn(itemList);

        List<PaymentDetailDto> result = mapper.selPaymentPageItemList(icart);

        assertEquals(result.get(0).getPrice(), item1.getPrice());
        assertEquals(result.get(0).getQuantity(), item1.getQuantity());
        assertEquals(result.get(0).getPic(), item1.getPic());
        assertEquals(result.get(0).getName(), item1.getName());

        assertEquals(result.get(1).getPrice(), item2.getPrice());
        assertEquals(result.get(1).getQuantity(), item2.getQuantity());
        assertEquals(result.get(1).getPic(), item2.getPic());
        assertEquals(result.get(1).getName(), item2.getName());

        for (PaymentDetailDto item : result) {
            Long price = item.getPrice();
            Long quantity = item.getQuantity();
            Long totalPrice = price * quantity;
            item.setTotalPrice(totalPrice);
        }
        assertEquals(result.get(0).getTotalPrice(), 50000L);
        assertEquals(result.get(1).getTotalPrice(), 20000L);

        then(mapper).should(times(1)).selPaymentPageItemList(any());
    }

    @Test
    @DisplayName("PayService - 결제 내역 부분 삭제")
    void delPaymentDetail() {
        Long testIorder = 1L;
        Long testIitem = 1L;
        Long testResult1 = 1L;

        List<Long> testResult2 = null;

        given(mapper.delPaymentDetail(any(), any())).willReturn(testResult1);
        given(mapper.paymentDetailNullCheck(any())).willReturn(testResult2);
        given(mapper.delOrder(any())).willReturn(1L);

        Long result1 = mapper.delPaymentDetail(testIorder, testIitem);
        List<Long> result2 = mapper.paymentDetailNullCheck(testIorder);


        if (result1 == 1L && result2 == null) {
            Long result = mapper.delOrder(testIorder);
            log.info("result : {}, 1은 삭제완료", result);
        }

        assertEquals(result1, testResult1);

        then(mapper).should(times(1)).delPaymentDetail(any(), any());
        then(mapper).should(times(1)).paymentDetailNullCheck(any());
        then(mapper).should(times(1)).delOrder(any());
    }

    @Test
    @DisplayName("PayService - 상세 결제 내역 보기(마이 페이지)")
    void selDetailedItemPaymentInfo() {
        Long testIorder = 1L;
        Long testIitem = 1L;

        SelDetailedItemPaymentInfoVo item = new SelDetailedItemPaymentInfoVo();
        item.setIitem(1L);
        item.setName("양의나라 유기농 양고기 양갈비 양꼬치 프렌치렉 숄더랙 캠핑 냉장 냉동");
        item.setPrice(30000L);
        item.setQuantity(1L);
        item.setPic("https://shopping-phinf.pstatic.net/main_8014052/80140522706.10.jpg");
        item.setPaymentDate(LocalDate.parse("2023-07-25"));
        item.setAddress("서울특별시 마포구 상암동 495-81");
        item.setAddressDetail("null같은 상세주소");
        item.setShippingPrice(3000L);
        item.setShippingMemo("빨리 배송해줘요");

        when(mapper.selDetailedItemPaymentInfo(any(), any())).thenReturn(item);

        SelDetailedItemPaymentInfoVo result = mapper.selDetailedItemPaymentInfo(testIorder, testIitem);

        assertEquals(result.getIitem(), item.getIitem());
        assertEquals(result.getName(), item.getName());
        assertEquals(result.getPrice(), item.getPrice());
        assertEquals(result.getQuantity(), item.getQuantity());
        assertEquals(result.getPic(), item.getPic());
        assertEquals(result.getPaymentDate(), item.getPaymentDate());
        assertEquals(result.getAddress(), item.getAddress());
        assertEquals(result.getAddressDetail(), item.getAddressDetail());
        assertEquals(result.getShippingPrice(), item.getShippingPrice());
        assertEquals(result.getShippingMemo(), item.getShippingMemo());

        then(mapper).should(times(1)).selDetailedItemPaymentInfo(any(), any());
    }

    @Test
    @DisplayName("PayService - 배송지 추가 등록")
    void insAddress() {
        Long testResult = 1L;

        ShippingInsDto1 item = new ShippingInsDto1();
        item.setIuser(1L);
        item.setName("신형주");
        item.setPhone("01055558888");
        item.setAddress("주소주소주소");
        item.setAddressDetail("상세주소주소");

        given(mapper.insAddress(any())).willReturn(testResult);


        Long result = mapper.insAddress(item);
        assertEquals(result, testResult);

        then(mapper).should(times(1)).insAddress(any());
    }

    @Test
    @DisplayName("PayService - 선택한 배송지 정보 출력")
    void selUserAddress() {
        Long testIuser = 1L;
        SelUserAddressVo item = new SelUserAddressVo
                ("주소주소", "상세주소주소", "신형주", "01066229988");

        given(mapper.selUserAddress(any())).willReturn(item);

        SelUserAddressVo result = mapper.selUserAddress(testIuser);

        assertEquals(result.getName(), item.getName());
        assertEquals(result.getPhone(), item.getPhone());
        assertEquals(result.getUserAddress(), item.getUserAddress());
        assertEquals(result.getUserAddressDetail(), item.getUserAddressDetail());

        then(mapper).should(times(1)).selUserAddress(any());
    }

    @Test
    @DisplayName("PayService - 등록된 배송지 리스트 출력")
    void selAddressList() {
        Long testUser = 1L;
        ShippingListSelVo item = new ShippingListSelVo
                ("주소주소", "상세주소주소", "신형주", "01066229988");

        List<ShippingListSelVo> itemList = new ArrayList<>();
        itemList.add(item);

        given(mapper.selAddressList(any())).willReturn(itemList);

        List<ShippingListSelVo> result = mapper.selAddressList(testUser);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getAddress(), item.getAddress());
        assertEquals(result.get(0).getAddressDetail(), item.getAddressDetail());
        assertEquals(result.get(0).getPhone(), item.getPhone());
        assertEquals(result.get(0).getName(), item.getName());

        then(mapper).should(times(1)).selAddressList(any());
    }

    @Test
    @DisplayName("PayService - 등록된 배송지 중 하나 출력")
    void selOneAddress() {
        SelUserAddressDto testDto = new SelUserAddressDto();
        testDto.setIaddress(1L);
        testDto.setIuser(1L);

        ShippingListSelVo item = new ShippingListSelVo
                ("주소주소", "상세주소주소", "신형주", "01066229988");

        given(mapper.selOneAddress(any())).willReturn(item);

        ShippingListSelVo result = mapper.selOneAddress(testDto);

        assertEquals(result.getAddress(), item.getAddress());
        assertEquals(result.getAddressDetail(), item.getAddressDetail());
        assertEquals(result.getPhone(), item.getPhone());
        assertEquals(result.getName(), item.getName());

        then(mapper).should(times(1)).selOneAddress(any());
    }
}