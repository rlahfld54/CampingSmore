package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.SelPaymentDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
@Tag(name = "결제")
public class PayController {


    private final PayService SERVICE;

    @PostMapping("/{iuser}")
    @Operation(summary = "결제 정보 저장")
    private int postPayInfo(InsPayInfoDto dto) {
        return SERVICE.insPayInfo(dto);
    }

    @GetMapping
    @Operation(summary = "결제 내역 보기")
    private SelPaymentDetailDto getPaymentDetail(int iorder) {
        return SERVICE.selPaymentDetail(iorder);
    }
}
