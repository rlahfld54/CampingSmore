package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.PaymentDetailDto;
import com.green.campingsmore.order.payment.model.SelPaymentDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "결제 - 아직 덜함")
public class PayController {

    private final PayService SERVICE;

    @PostMapping
    @Operation(summary = "결제 정보 저장")
    private int postPayInfo(InsPayInfoDto dto) {
        return SERVICE.insPayInfo(dto);
    }

    @GetMapping("/{iuser}/{iorder}")
    @Operation(summary = "결제 내역 보기")    //유저 결제시 띄움(결제창에서 바로띄움), 변경가능성 매우높음
    private SelPaymentDetailDto getPaymentDetail(@PathVariable Long iuser, @PathVariable Long iorder) {
        return SERVICE.selPaymentDetail(iorder);
    }

    @GetMapping("/{iuser}")
    @Operation(summary = "전체 결제 내역 보기") //유저마이페이지에서 조회, 마이페이지 이동예정
    private List<SelPaymentDetailDto> getPaymentList(@PathVariable Long iuser) {
        return SERVICE.selPaymentDetailAll(iuser);
    }

    @GetMapping("/order/{iuser}")   //예정
    @Operation(summary = "아이템 구매 버튼 -> 단일 아이템을 결제 페이지에서 보여주기")
    private PaymentDetailDto getPaymentItem(@PathVariable Long iuser, Long iitem) {
        return SERVICE.selPaymentPageItem(iuser, iitem);
    }

//    @GetMapping("/order/list/{iuser}")
//    @Operation(summary = "장바구니 결제 버튼 -> 아이템 리스트를 결제 페이지에서 보여주기")
//    private List<PaymentDetailDto> getPaymentItemList(@PathVariable Long iuser, @RequestBody CheckCartDto dto) {
//        return SERVICE.selPaymentPageItemList(iuser, dto);
//    }

    @GetMapping("/order/list/{iuser}")  //예정
    @Operation(summary = "장바구니 결제 버튼 -> 아이템 리스트를 결제 페이지에서 보여주기")
    private List<PaymentDetailDto> getPaymentItemList(@PathVariable Long iuser) {
        return SERVICE.selPaymentPageItemList(iuser, );
    }
}