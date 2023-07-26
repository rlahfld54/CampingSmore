package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.PayDetailInfoVo;
import com.green.campingsmore.order.payment.model.PaymentDetailVo;
import com.green.campingsmore.order.payment.model.SelPaymentDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayMapper MAPPER;

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
    public SelPaymentDetailDto selPaymentDetail(int iorder) {
        return MAPPER.selPaymentDetail1(iorder);
    }

    @Override
    public List<SelPaymentDetailDto> selPaymentDetailAll(int iuser) {
        return MAPPER.selPaymentDetailAll1(iuser);
    }

    @Override
    public List<PaymentDetailVo> selPaymentPageItemList(int iorder) {
        List<PaymentDetailVo> list = MAPPER.selPaymentPageItemList(iorder);

        for (int i = 0; i < list.size(); i++) {
            PaymentDetailVo vo = new PaymentDetailVo();
            Long totalPrice = vo.getPrice() * vo.getQuantity();
            list.get(i).setTotalPrice(totalPrice);
        }
        return list;
    }

}
