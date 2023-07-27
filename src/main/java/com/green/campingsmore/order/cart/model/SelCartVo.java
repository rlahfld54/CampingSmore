package com.green.campingsmore.order.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelCartVo {
    private int icart;
    private String name;
    private int quantity;
    private int check;
}
