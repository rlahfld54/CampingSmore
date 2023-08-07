package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;

import java.util.List;

public interface PayService {
    int insPayInfo(InsPayInfoDto1 dto);
    PaymentCompleteDto selPaymentComplete(Long iorder);
    List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser);
    PaymentDetailDto selPaymentPageItem(Long iitem, Long quantity);
    List<PaymentDetailDto> selPaymentPageItemList(CartPKDto dto);
    Long delPaymentDetail(Long iorder, Long iitem);
    SelDetailedItemPaymentInfoVo SelDetailedItemPaymentInfo(Long iorder, Long iitem);

    //주소 관련
    Long insAddress(ShippingInsDto1 dto);
    SelUserAdrressVo selUserAddress(Long iuser);
    List<ShippingListSelVo> selAddressList(Long iuser);
    ShippingListSelVo selOneAddress(SelUserAdressDto dto);
}