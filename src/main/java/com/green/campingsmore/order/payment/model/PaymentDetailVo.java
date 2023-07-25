package com.green.campingsmore.order.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentDetailVo {
    private String name;
    private Long price;
    private Long quantity;
    private Long totalPrice;
    private String Pic;
}
