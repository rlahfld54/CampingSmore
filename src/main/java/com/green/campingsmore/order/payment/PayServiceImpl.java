package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
            orderDto.setAddress(dto.getAddress());
            orderDto.setShippingPrice(dto.getShippingPrice());
            orderDto.setTotalPrice(dto.getTotalPrice());
            MAPPER.insPayInfo(orderDto);

            List<PayDetailInfoVo> purchaseList = dto.getPurchaseList();
            log.info("purchaseList = {}", purchaseList);
            MAPPER.insPayDetailInfo(purchaseList);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public PaymentCompleteDto selPaymentComplete(Long iorder) {
        return MAPPER.selPaymentComplete(iorder);
    }

    @Override
    public List<SelPaymentDetailDto> selPaymentDetailAll(Long iuser) {
        return MAPPER.selPaymentDetailAll1(iuser);
    }

    @Override
    public PaymentDetailDto selPaymentPageItem(Long iitem, Long quantity) {
        PaymentDetailDto dto = MAPPER.selPaymentPageItem(iitem);
        Long price = dto.getPrice();

        dto.setQuantity(quantity);
        dto.setTotalPrice(price * quantity);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<PaymentDetailDto> selPaymentPageItemList(CartPKDto dto) {
        List<PaymentDetailDto> paymentDetailDtos = MAPPER.selPaymentPageItemList(dto.getIcart());
        try {
            for (PaymentDetailDto paymentDetailDto : paymentDetailDtos) {
                Long price = paymentDetailDto.getPrice();
                Long quantity = paymentDetailDto.getQuantity();
                Long totalPrice = price * quantity;
                paymentDetailDto.setTotalPrice(totalPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentDetailDtos;
    }
}
