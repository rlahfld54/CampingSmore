package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.util.List;

@Data
public class SelPaymentDetailDto {
    private Long iorder;
    private Long iuser;
    private Long totalPrice;
    private Long shippingPrice;
    private String userAddress;
    private String address;
    private List<PaymentDetailVo> itemList;
}
