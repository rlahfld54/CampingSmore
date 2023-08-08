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
    public int insPayInfo(InsPayInfoDto1 dto) {

        InsPayInfoDto1 orderDto = new InsPayInfoDto1();

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

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long delPaymentDetail(Long iorder, Long iitem) {

        try {
            Long result1 = MAPPER.delPaymentDetail(iorder, iitem);
            List<Long> result2 = MAPPER.paymentDetailNullCheck(iorder);

            if (result1 == 1L && result2 == null) {
                MAPPER.delOrder(iorder);
                return 1L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public SelDetailedItemPaymentInfoVo selDetailedItemPaymentInfo(Long iorder, Long iitem) {
        return MAPPER.selDetailedItemPaymentInfo(iorder, iitem);
    }

    @Override
    public Long insAddress(ShippingInsDto1 dto) {
        return MAPPER.insAddress(dto);
    }

    @Override
    public SelUserAddressVo selUserAddress(Long iuser) {
        return MAPPER.selUserAddress(iuser);
    }

    @Override
    public List<ShippingListSelVo> selAddressList(Long iuser) {
        return MAPPER.selAddressList(iuser);
    }

    @Override
    public ShippingListSelVo selOneAddress(SelUserAddressDto dto) {
        return MAPPER.selOneAddress(dto);
    }
}
