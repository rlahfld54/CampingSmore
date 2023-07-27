package com.green.campingsmore.order.cart;

import com.green.campingsmore.order.cart.model.InsCartDto;
import com.green.campingsmore.order.cart.model.SelCartVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService SERVICE;

    @GetMapping("/{iuser}")
    private List<SelCartVo> getCart(@PathVariable Long iuser) {
        return SERVICE.selCart(iuser);
    }

    @PostMapping
    private Long postCart(@RequestBody InsCartDto dto) {
        return SERVICE.insCart(dto);
    }

    @DeleteMapping("/{icart}")
    private Long delCart(@PathVariable Long icart) {
        return SERVICE.delCart(icart);
    }
}
