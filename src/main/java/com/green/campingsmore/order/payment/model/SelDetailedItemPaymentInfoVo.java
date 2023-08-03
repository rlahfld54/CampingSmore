package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SelDetailedItemPaymentInfoVo {
    private Long iitem;
    private String name;
    private Long price;
    private Long quantity;
    private Long totalPrice;
    private String Pic;
    private LocalDate paymentDate;
    private String address;
    private String addressDetail;
    private Long shippingPrice;
    private String shippingMemo;
}