package com.green.campingsmore.order.cart;

import com.green.campingsmore.order.cart.model.InsCartDto;
import com.green.campingsmore.order.cart.model.SelCartVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "장바구니")
public class CartController {

    private final CartService SERVICE;

    @GetMapping("/{iuser}")
    @Operation(summary = "장바구니 목록 보기")
    private List<SelCartVo> getCart(@PathVariable Long iuser) {
        return SERVICE.selCart(iuser);
    }

    @PostMapping
    @Operation(summary = "장바구니에 등록하기")
    private Long postCart(@RequestBody InsCartDto dto) {
        return SERVICE.insCart(dto);
    }

    @DeleteMapping("/{icart}")
    @Operation(summary = "장바구니 목록 삭제")
    private Long delCart(@PathVariable Long icart) {
        return SERVICE.delCart(icart);
    }

    @DeleteMapping
    @Operation(summary = "장바구니 목록 선택삭제")
    private Long delCartAll(@RequestParam List<Long> icart) {
        return SERVICE.delCartAll(icart);
//
    }
}
