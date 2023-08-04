package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDetailDto2 {
    private Long iitem;
    private String name;
    private Long totalPrice;
    private String Pic;
    private LocalDate paymentDate;
}