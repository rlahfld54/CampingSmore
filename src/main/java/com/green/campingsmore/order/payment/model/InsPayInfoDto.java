package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.util.List;

@Data
public class InsPayInfoDto {
    private Long iuser;
    private String address;
    private Long totalPrice;
    private Long shippingPrice;
    private String shippingMemo;
    private List<PayDetailInfoVo> purchaseList;
}
