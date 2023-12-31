package com.green.campingsmore.order.cart;

import com.green.campingsmore.order.cart.model.InsCartDto2;
import com.green.campingsmore.order.cart.model.SelCartVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    List<SelCartVo> selCart(Long iuser);
    Long insCart(InsCartDto2 dto);
    Long plusCart(Long iuser, Long quantity, Long iitem);
    Long checkCartUser(Long iuser, Long iitem);
    Long delCart(Long icart);
}
