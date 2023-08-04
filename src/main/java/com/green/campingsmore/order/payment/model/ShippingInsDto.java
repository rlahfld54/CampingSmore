package com.green.campingsmore.order.payment.model;

import lombok.Data;

@Data
public class ShippingInsDto {
    private Long iuser;
    private String address;
    private String addressdetail;
    private String name;
    private String phone;
}
