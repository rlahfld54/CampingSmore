package com.green.campingsmore.order.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelCartVo {
    private Long icart;
    private String pic;
    private String name;
    private Long price;
    private Long quantity;
}
