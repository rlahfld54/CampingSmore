package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {
    //결제, 주문 관련
    Long insPayInfo(InsPayInfoDto1 dto);
    Long insPayDetailInfo(List<PayDetailInfoVo> list);
    PaymentCompleteDto selPaymentComplete(Long iorder);
    List<SelPaymentDetailDto> selPaymentDetailAll1(Long iuser);
    List<PaymentDetailDto> selPaymentPageItemList(List<Long> list);
    PaymentDetailDto selPaymentPageItem(Long iitem);
    Long delPaymentDetail(Long iorder, Long iitem);
    SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorder, Long iitem);
    Long delOrder(Long iorder);

    //결제 내역 널 체크
    List<Long> paymentDetailNullCheck(Long iorder);

    //주소 관련
    Long insAddress(ShippingInsDto1 dto);
    SelUserAddressVo selUserAddress(Long iuser);
    List<ShippingListSelVo> selAddressList(Long iuser);
    ShippingListSelVo selOneAddress(SelUserAddressDto dto);
}
