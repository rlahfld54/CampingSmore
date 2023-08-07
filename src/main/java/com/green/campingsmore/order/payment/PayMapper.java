package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {
    //결제, 주문 관련
    int insPayInfo(InsPayInfoDto dto);
    int insPayDetailInfo(List<PayDetailInfoVo> list);
    PaymentCompleteDto selPaymentComplete(Long iorder);
    List<SelPaymentDetailDto> selPaymentDetailAll1(Long iuser);
    List<PaymentDetailDto> selPaymentPageItemList(List<Long> list);
    PaymentDetailDto selPaymentPageItem(Long iitem);
    Long delPaymentDetail(Long iorder, Long iitem);
    SelDetailedItemPaymentInfoVo SelDetailedItemPaymentInfo(Long iorder, Long iitem);

    //주소 관련
    Long insAddress(ShippingInsDto dto);
    SelUserAdrressVo selUserAddress(Long iuser);
    List<ShippingListSelVo> selAddressList(Long iuser);
    ShippingListSelVo selOneAddress(SelUserAdressDto dto);
}
