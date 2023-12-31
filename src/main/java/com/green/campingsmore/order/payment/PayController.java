package com.green.campingsmore.order.payment;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.order.payment.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "결제")
public class PayController {

    private final PayService SERVICE;

    @PostMapping
    @Operation(summary = "결제 정보 저장하기",
            description =
                    "<h3> address : 배송지\n" +
                            "<h3> addressDetail : 상세 배송지\n" +
                            "<h3> totalPrice : 총 결제 금액\n" +
                            "<h3> shippingPrice : 배송비\n" +
                            "<h3> shippingMemo : 배송 메모\n" +
                            "<h3>purchaseList : 구입 목록\n" +
                            "<h3>   └iitem : 결제한 아이템 PK\n" +
                            "<h3>   └quantity : 아이템 수량\n" +
                            "<h3>   └totalPrice : 아이템별 총 가격\n" +
                            "<h3>-----------------------------------\n" +
                            "<h3>   CODE 1 : DB 정보 저장 성공\n" +
                            "<h3>   CODE 0 : DB 정보 저장 실패\n"
    )
    public Long postPayInfo(@AuthenticationPrincipal MyUserDetails user,
                            @RequestBody InsPayInfoDto dto) {
        InsPayInfoDto1 dto1 = new InsPayInfoDto1();
        dto1.setIuser(user.getIuser());
        dto1.setAddress(dto.getAddress());
        dto1.setAddressDetail(dto.getAddressDetail());
        dto1.setTotalPrice(dto.getTotalPrice());
        dto1.setShippingMemo(dto.getShippingMemo());
        dto1.setShippingPrice(dto.getShippingPrice());
        dto1.setPurchaseList(dto.getPurchaseList());
        return SERVICE.insPayInfo(dto1);
    }

    @GetMapping("/{iorder}")
    @Operation(summary = "결제 내역 보기",
            description = "<h3> iorder : 주문 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3> address : 주소\n" +
                    "<h3> addressDetail : 상세주소\n" +
                    "<h3> shippingMemo : 배송 메모\n" +
                    "<h3> totalPrice : 총 가격\n"


    )    //유저 결제시 띄움(결제창에서 바로 띄움)
    public PaymentCompleteDto getPaymentComplete(@PathVariable Long iorder) {
        return SERVICE.selPaymentComplete(iorder);
    }

    @GetMapping("/paymentList")
    @Operation(summary = "전체 결제 내역 보기(마이 페이지)",
            description = "<h3> iorder : 주문 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3> itemList : 결제 내역 리스트 PK\n" +
                    "<h3>   └iitem : 아이템 PK\n" +
                    "<h3>   └name : 아이템 이름 PK\n" +
                    "<h3>   └totalPrice : 총 가격 PK\n" +
                    "<h3>   └Pic : 사진 PK\n" +
                    "<h3>   └paymentDate : 결제일\n" +
                    "<h3>   └reviewYn : 리뷰 존재 여부 (있을 시 ireview 반환, 없을 시 0 반환)\n"
    )
    //유저마이페이지에서 조회
    public List<SelPaymentDetailDto> getPaymentList(@AuthenticationPrincipal MyUserDetails user) {
        Long iuser = user.getIuser();
        return SERVICE.selPaymentDetailAll(iuser);
    }

    @GetMapping("/paymentList/detail/{iorder}")
    @Operation(summary = "상세 결제 내역 보기(마이 페이지)",
            description = "<h3> iorder : 결제내역 PK\n" +
                    "<h3> iitem : 아이템 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3> iitem : 아이템 PK\n" +
                    "<h3> name : 아이템 이름\n" +
                    "<h3> price : 아이템 가격\n" +
                    "<h3> quantity : 아이템 수량\n" +
                    "<h3> totalPrice : 총 가격\n" +
                    "<h3> Pic : 사진\n" +
                    "<h3> paymentDate : 결제일\n" +
                    "<h3> address : 주소\n" +
                    "<h3> addressDetail : 상세 주소\n" +
                    "<h3> shippingPrice : 배송 가격\n" +
                    "<h3> shippingMemo : 배송 메모\n"

    ) //유저마이페이지에서 조회
    public SelDetailedItemPaymentInfoVo getDetailedItemPaymentInfo(@AuthenticationPrincipal MyUserDetails user,
                                                                   @PathVariable Long iorder, @RequestParam Long iitem) {
        return SERVICE.selDetailedItemPaymentInfo(iorder, iitem);
    }

    @PutMapping("/paymentList/{iorder}")
    @Operation(summary = "전체 결제 내역에서 하나의 결제 내역 삭제(아이템별, 마이 페이지)",
            description = "<h3> iorder : 결제내역 PK\n" +
                    "<h3> iitem : 아이템 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3>CODE 1 : 선택한 결제내역 삭제\n" +
                    "<h3>CODE 2 : 선택한 결제내역 삭제 + 텅빈 결제내역 틀 삭제\n" +
                    "<h3>CODE 0 : 삭제되지 않음\n"
    ) //유저마이페이지에서 조회
    public Long delPaymentDetail(@PathVariable Long iorder, @RequestParam Long iitem) {
        return SERVICE.delPaymentDetail(iorder, iitem);
    }

    @PostMapping("/order/cart")
    @Operation(summary = "장바구니 결제 버튼 -> 체크된 장바구니 아이템 정보들을 결제 페이지에서 보여주기",
            description = "<h3> icart : 장바구니 PK\n" +
                    "<h3>-----------------------------------\n" +
                    "<h4># Post 요청이지만 아이템 정보를 보여줌 #\n" +
                    "<h3>-----------------------------------\n" +
                    "<h3> iitem : 아이템 PK\n" +
                    "<h3> name : 아이템 이름\n" +
                    "<h3> price : 아이템 가격\n" +
                    "<h3> quantity : 아이템 수량\n" +
                    "<h3> totalPrice : 아이템 총 가격\n" +
                    "<h3> Pic : 이미지\n"
    )
    public List<PaymentDetailDto> getPaymentItemList(@AuthenticationPrincipal MyUserDetails user,
                                                     @RequestBody CartPKDto dto) {
        return SERVICE.selPaymentPageItemList(dto);
    }

    @GetMapping("/order/{iitem}")
    @Operation(summary = "아이템 구매 버튼 -> 단일 아이템 정보를 결제 페이지에서 보여주기",
            description =
                    "<h3> iitem : 결제 클릭한 아이템의 PK\n" +
                            "<h3> quantity : 올려놓은 아이템의 수량\n" +
                            "<h3>-----------------------------------\n" +
                            "<h3> iitem : 아이템 PK\n" +
                            "<h3> name : 아이템 이름\n" +
                            "<h3> price : 아이템 가격\n" +
                            "<h3> quantity : 아이템 수량\n" +
                            "<h3> totalPrice : 아이템 총 가격\n" +
                            "<h3> Pic : 이미지\n")
    public PaymentDetailDto getPaymentItem(@AuthenticationPrincipal MyUserDetails user,
                                           @PathVariable Long iitem, @RequestParam Long quantity) {
        return SERVICE.selPaymentPageItem(iitem, quantity);
    }

    @PostMapping("/address")
    @Operation(summary = "배송지 추가 등록",
            description =
                    "<h3> address : 주소\n" +
                            "<h3> addressDetail : 상세 주소\n" +
                            "<h3> name : 수령인\n" +
                            "<h3> phone : 전화번호 (하이픈 '-'없이)\n" +
                            "<h3>-----------------------------------\n" +
                            "CODE 1 : 등록 성공\n"
    )
    public Long insAddress(@AuthenticationPrincipal MyUserDetails user,
                           @RequestBody ShippingInsDto dto) {
        ShippingInsDto1 dto1 = new ShippingInsDto1();
        dto1.setAddress(dto.getAddress());
        dto1.setName(dto.getName());
        dto1.setAddressDetail(dto.getAddressDetail());
        dto1.setPhone(dto.getPhone());
        dto1.setIuser(user.getIuser());
        return SERVICE.insAddress(dto1);
    }

    @GetMapping("/address")
    @Operation(summary = "기본 배송지(유저 주소) 출력",
            description =
                    "<h3>-----------------------------------\n" +
                            "<h3> userAddress : 해당 유저의 주소\n" +
                            "<h3> userAddressDetail : 해당 유저의 상세 주소\n" +
                            "<h3> name : 수령인\n" +
                            "<h3> phone : 전화번호 (하이픈 '-'없이)\n"
    )
    public SelUserAddressVo selUserAddress(@AuthenticationPrincipal MyUserDetails user) {
        Long iuser = user.getIuser();
        return SERVICE.selUserAddress(iuser);
    }

    @GetMapping("/addressList")
    @Operation(summary = "등록된 배송지 리스트 출력",
            description =
                    "<h3>-----------------------------------\n" +
                            "<h3> address : 주소\n" +
                            "<h3> addressDetail : 상세 주소\n" +
                            "<h3> name : 수령인\n" +
                            "<h3> phone : 전화번호 (하이픈 '-'없이)\n"
    )
    public List<ShippingListSelVo> selAddressList(@AuthenticationPrincipal MyUserDetails user) {
        Long iuser = user.getIuser();
        return SERVICE.selAddressList(iuser);
    }

    @GetMapping("/addressList/{iaddress}")
    @Operation(summary = "등록된 배송지 중 선택한 배송지 정보 출력",
            description =
                    "<h3> iaddress : 등록한 배송지PK\n" +
                            "<h3>-----------------------------------\n" +
                            "<h3> address : 주소\n" +
                            "<h3> addressDetail : 상세 주소\n" +
                            "<h3> name : 수령인\n" +
                            "<h3> phone : 전화번호 (하이픈 '-'없이)\n"
    )
    public ShippingListSelVo selOneAddress(@AuthenticationPrincipal MyUserDetails user,
                                           @PathVariable Long iaddress) {
        SelUserAddressDto dto1 = new SelUserAddressDto();
        Long iuser = user.getIuser();
        dto1.setIuser(iuser);
        dto1.setIaddress(iaddress);
        return SERVICE.selOneAddress(dto1);
    }

    @DeleteMapping("/address/{iaddress}")
    @Operation(summary = "등록된 배송지 제거",
            description = "<h3> iaddress : 등록한 배송지PK\n" +
                    "<h3>-----------------------------------\n" +
                    "Code 1 : 삭제 성공\n"
    )
    public Long delAddress(@PathVariable Long iaddress) {
        return SERVICE.delAddress(iaddress);
    }
}