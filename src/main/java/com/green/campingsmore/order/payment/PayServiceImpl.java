package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import com.green.campingsmore.order.payment.model.PayDetailInfoVo;
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
}
