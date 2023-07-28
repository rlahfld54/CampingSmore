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
    @Operation(
            summary = "장바구니 목록 보기",
            description = "<h3>iuser : 유저 PK\n"
    )
    private List<SelCartVo> getCart(@PathVariable Long iuser) {
        return SERVICE.selCart(iuser);
    }

    @PostMapping
    @Operation(summary = "장바구니에 등록하기",
            description = "<h3> iuser : 유저 PK\n" +
                    "<h3> iitem : 아이템 PK\n" +
                    "<h3> quantity : 아이템 수량\n" +
                    "\n" +
                    "\n" +
                    "<h3>CODE 1 : 저장 성공\n"
    )
    private Long postCart(@RequestBody InsCartDto dto) {
        return SERVICE.insCart(dto);
    }

    @DeleteMapping("/{icart}")
    @Operation(summary = "장바구니 목록 삭제",
            description = "<h3> icart : 삭제할 장바구니의 PK (직접 삭제시)\n" +
                    "\n" +
                    "\n" +
                    "<h3>CODE 1 : 삭제성공\n"
    )
    private Long delCart(@PathVariable Long icart) {
        return SERVICE.delCart(icart);
    }

    @DeleteMapping
    @Operation(summary = "장바구니 목록 선택삭제",
            description = "<h3> icart : 체크된 장바구니의 PK (여러개),(선택 삭제시)\n" +
                    "\n" +
                    "\n" +
                    "<h3>CODE 1 : 삭제성공\n"
    )
    private Long delCartAll(@RequestParam List<Long> icart) {
        return SERVICE.delCartAll(icart);
//
    }
}
