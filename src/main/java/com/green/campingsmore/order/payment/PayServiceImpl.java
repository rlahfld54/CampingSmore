package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.cart.CartMapper;
import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.PayDetailInfoVo;
import com.green.campingsmore.order.payment.model.PaymentDetailDto;
import com.green.campingsmore.order.payment.model.SelPaymentDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayMapper MAPPER;
    private final CartMapper CartMAPPER;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int insPayInfo(InsPayInfoDto dto) {

        InsPayInfoDto orderDto = new InsPayInfoDto();

        try {
            orderDto.setIuser(dto.getIuser());
            orderDto.setShippingPrice(dto.getShippingPrice());
            orderDto.setTotalPrice(dto.getTotalPrice());
            MAPPER.insPayInfo(orderDto);

            List<PayDetailInfoVo> purchaseList = dto.getPurchaseList();
            MAPPER.insPayDetailInfo(purchaseList);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public SelPaymentDetailDto selPaymentDetail(Long iorder) {
        return MAPPER.selPaymentDetail1(iorder);
    }

    @Override
    public List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser) {
        return MAPPER.selPaymentDetailAll1(iuser);
    }

    @Override
    public List<PaymentDetailDto> selPaymentPageItemList(Long iuser, ) {

        CartMAPPER.updCheckBox(dto.getList());   //체크박스 업데이트

        List<PaymentDetailDto> paymentList = MAPPER.selPaymentPageItemList(iuser);
        for (int i = 0; i < paymentList.size(); i++) {
            Long getPrice = paymentList.get(i).getPrice();
            Long getQuantity = paymentList.get(i).getQuantity();
            Long totalPrice =  getPrice * getQuantity;
            paymentList.get(i).setTotalPrice(totalPrice);
        }   //리스트에 일일이 총가격 계산후 주입
        return paymentList;
    }

    @Override
    public PaymentDetailDto selPaymentPageItem(Long iuser, Long iitem) {
        return MAPPER.selPaymentPageItem(iitem, iuser);
    }
}
