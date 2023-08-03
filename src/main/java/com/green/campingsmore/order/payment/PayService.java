package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;

import java.util.List;

public interface PayService {
    int insPayInfo(InsPayInfoDto dto);
    PaymentCompleteDto selPaymentComplete(Long iorder);
    List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser);
    PaymentDetailDto selPaymentPageItem(Long iitem, Long quantity);
    List<PaymentDetailDto> selPaymentPageItemList(CartPKDto dto);
    Long delPaymentDetail(Long iorder);
}