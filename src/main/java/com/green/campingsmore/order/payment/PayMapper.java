package com.green.campingsmore.order.payment;

import com.green.campingsmore.order.payment.model.PayDetailInfoVo;
import com.green.campingsmore.order.payment.model.InsPayInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {
    int insPayInfo(InsPayInfoDto dto);
    int insPayDetailInfo(List<PayDetailInfoVo> list);
}
