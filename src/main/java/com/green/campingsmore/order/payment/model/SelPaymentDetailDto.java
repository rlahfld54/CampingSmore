package com.green.campingsmore.order.payment.model;

import lombok.Data;

import java.util.List;

@Data
public class SelPaymentDetailDto {
    private Long iorder;
    private List<PaymentDetailDto2> itemList;
}