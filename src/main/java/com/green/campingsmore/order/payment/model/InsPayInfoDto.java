package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.util.List;

@Data
public class InsPayInfoDto {
    private String address;
    private String addressDetail;
    private Long totalPrice;
    private Long shippingPrice;
    private String shippingMemo;
    private List<PayDetailInfoVo> purchaseList;
}
