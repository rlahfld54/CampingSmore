package com.green.campingsmore.order.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShippingListSelVo {
    private String address;
    private String addressDetail;
    private String name;
    private String phone;
}
