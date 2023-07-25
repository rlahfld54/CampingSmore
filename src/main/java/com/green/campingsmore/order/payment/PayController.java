package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.cart.CartService;
import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "결제")
public class PayController {


    private final PayService SERVICE;

    @PostMapping("/{iuser}")
    @Operation(summary = "결제 정보 저장")
    private int postPayInfo(InsPayInfoDto dto) {
        return SERVICE.insPayInfo(dto);
    }
}
