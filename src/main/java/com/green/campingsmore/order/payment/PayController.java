package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.PayDetailInfoVo;
import com.green.campingsmore.order.payment.model.PaymentDetailVo;
import com.green.campingsmore.order.payment.model.SelPaymentDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
@Tag(name = "결제")
public class PayController {


    private final PayService SERVICE;

    @PostMapping
        @Operation(summary = "결제 정보 저장")
        private int postPayInfo(InsPayInfoDto dto) {
            return SERVICE.insPayInfo(dto);
    }

    @GetMapping("/{iuser}/{iorder}")
    @Operation(summary = "결제 내역 보기")    //유저 결제시 띄움(결제창에서 바로띄움), 변경가능성 매우높음
    private SelPaymentDetailDto getPaymentDetail(@PathVariable int iuser, @PathVariable int iorder) {
        return SERVICE.selPaymentDetail(iorder);
    }

    @GetMapping("/{iuser}")
    @Operation(summary = "전체 결제 내역 보기") //유저마이페이지에서 조회
    private List<SelPaymentDetailDto> getPaymentList(@PathVariable int iuser) {
        return SERVICE.selPaymentDetailAll(iuser);
    }

//    @GetMapping
//    @Operation(summary = "결제 페이지 아이템 리스트 - 아이템 바로 구매")
//    private List<PaymentDetailVo> getPaymentItemList(int iitem, int iuser) {
//        return null;
//    }

    @GetMapping
    @Operation(summary = "결제 페이지 아이템 리스트 - 장바구니에 담아 구매")
    private List<PaymentDetailVo> getPaymentItemList(int iorder) {
        return SERVICE.selPaymentPageItemList(iorder);
    }
}