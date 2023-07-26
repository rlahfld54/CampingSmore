package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.PaymentDetailVo;
import com.green.campingsmore.order.payment.model.SelPaymentDetailDto;

import java.util.List;

public interface PayService {
    int insPayInfo(InsPayInfoDto dto);
    SelPaymentDetailDto selPaymentDetail(int iorder);
    List<SelPaymentDetailDto> selPaymentDetailAll(int iorder);
    List<PaymentDetailVo> selPaymentPageItemList(int iorder);
}
