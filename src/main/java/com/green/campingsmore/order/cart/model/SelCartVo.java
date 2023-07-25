package com.green.campingsmore.order.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelCartVo {
    private int icart;
    private String pic;
    private String name;
    private int price;
    private int quantity;
    private int check;
}
