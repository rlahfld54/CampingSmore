package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {
    int insPayInfo(InsPayInfoDto dto);
    int insPayDetailInfo(List<PayDetailInfoVo> list);
    PaymentCompleteDto selPaymentComplete(Long iorder);
    List<SelPaymentDetailDto> selPaymentDetailAll1(Long iuser);
    List<PaymentDetailDto> selPaymentPageItemList(List<Long> list);
    PaymentDetailDto selPaymentPageItem(Long iitem);
    Long delPaymentDetail(Long iorder);
}
