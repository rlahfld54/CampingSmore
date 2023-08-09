package com.green.campingsmore.order.cart;

import com.green.campingsmore.order.cart.model.InsCartDto1;
import com.green.campingsmore.order.cart.model.InsCartDto2;
import com.green.campingsmore.order.cart.model.SelCartVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper MAPPER;

    @Override
    public Long insCart(InsCartDto1 dto) {
        InsCartDto2 dto2 = new InsCartDto2();
        dto2.setIuser(dto.getIuser());
        dto2.setIitem(dto.getIitem());
        dto2.setQuantity(dto.getQuantity());
        Long userExistCheck = MAPPER.checkCartUser(dto2.getIuser(), dto2.getIitem());
        if (userExistCheck == null) {
            return MAPPER.insCart(dto2);
        } else {
            return MAPPER.plusCart(dto2.getIuser(), dto2.getQuantity(), dto2.getIitem());
        }
    }

    @Override
    public List<SelCartVo> selCart(Long iuser) {
        return MAPPER.selCart(iuser);
    }

    @Override
    public Long delCart(Long icart) {
        return MAPPER.delCart(icart);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long delCartAll(List<Long> icart) {
        Long count = 0L;
        try {
            for (Long aLong : icart) {
                MAPPER.delCart(aLong);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
