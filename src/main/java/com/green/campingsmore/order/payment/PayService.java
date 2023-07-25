package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.SelPaymentDetailDto;

public interface PayService {
    int insPayInfo(InsPayInfoDto dto);
    SelPaymentDetailDto selPaymentDetail(int iorder);
}
