package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.util.List;

@Data
public class InsByOrderDto {
    private Long iuser;
    private Long shippingPrice;
    private Long totalPrice;
}
