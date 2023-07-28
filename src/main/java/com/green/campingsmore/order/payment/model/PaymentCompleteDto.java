package com.green.campingsmore.order.payment.model;

import lombok.Data;

@Data
public class PaymentCompleteDto {
    private String address;
    private String addressDetail ;
    private String shippingMemo;
    private Long totalPrice;
}
