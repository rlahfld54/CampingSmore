package com.example.campingsmore.order.cart;

import com.example.campingsmore.order.cart.model.InsCartDto;
import com.example.campingsmore.order.cart.model.SelCartVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper MAPPER;

    @Override
    public Long insCart(InsCartDto dto) {
        return MAPPER.insCart(dto);
    }

    @Override
    public List<SelCartVo> selCart(Long iuser) {
        return MAPPER.selCart(iuser);
    }

    @Override
    public Long delCart(Long icart){
        return MAPPER.delCart(icart);
    }
}