package com.green.campingsmore.order.payment.model;
import lombok.Data;

@Data
public class PaymentDetailDto {
    private Long iitem;
    private String name;
    private Long price;
    private Long quantity;
    private Long totalPrice;
    private String Pic;
}
