package com.example.campingsmore.order.cart;

import com.example.campingsmore.order.cart.model.InsCartDto;
import com.example.campingsmore.order.cart.model.SelCartVo;

import java.util.List;


public interface CartService {
    Long insCart(InsCartDto dto);
    List<SelCartVo> selCart(Long iuser);
    Long delCart(Long icart);
}
