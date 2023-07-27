package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.util.List;

@Data
public class SelPaymentDetailDto {
    private Long iorder;
    private Long iuser;
    private String address;
    private Long totalPrice;
    private Long shippingPrice;
    private String shippingMemo;
    private List<PaymentDetailDto> itemList;
}