package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class InsPayInfoDto {
    private Long iuser;
    private Long shippingPrice;
    private Long totalPrice;
    private List<PayDetailInfoVo> purchaseList;
}
