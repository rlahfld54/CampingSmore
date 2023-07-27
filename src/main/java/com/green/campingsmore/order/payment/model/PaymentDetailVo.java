package com.green.campingsmore.order.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class PaymentDetailVo {
    private Long iitem;
    private String name;
    private Long price;
    private Long quantity;
    private Long totalPrice;
    private String Pic;
}
