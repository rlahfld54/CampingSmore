package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.PaymentDetailDto;
import com.green.campingsmore.order.payment.model.SelPaymentDetailDto;

import java.util.List;

public interface PayService {
    int insPayInfo(InsPayInfoDto dto);
    SelPaymentDetailDto selPaymentDetail(Long iorder);
    List<SelPaymentDetailDto> selPaymentDetailAll(Long iorder);
    PaymentDetailDto selPaymentPageItem(Long iuser, Long iitem);
    List<PaymentDetailDto> selPaymentPageItemList(Long iuser,);
}