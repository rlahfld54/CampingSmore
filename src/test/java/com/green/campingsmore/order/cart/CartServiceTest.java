package com.green.campingsmore.order.cart;

import com.green.campingsmore.order.cart.model.InsCartDto;
import com.green.campingsmore.order.cart.model.SelCartVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import( {CartServiceImpl.class} )
class CartServiceTest {

    @MockBean
    private CartMapper mapper;

    @Autowired
    private CartService service;


    @Test
    @DisplayName("CartService - 장바구니 정보 저장")
    void insCart() {
        Long testResult = 1L;
        when(mapper.insCart(any())).thenReturn(testResult);

        InsCartDto dto = new InsCartDto();
        dto.setIuser(3L);
        dto.setIitem(5L);
        dto.setQuantity(10L);

        Long result = service.insCart(dto);
        assertEquals(testResult, result);

        verify(mapper).insCart(any());
        //select 작성후 추가예정
    }

    @Test
    @DisplayName("CartService - 장바구니 목록 보여주기")
    void selCart() {
        //given
        SelCartVo vo1 = new SelCartVo(1L,"바베큐.jpg","음모오",12345L,2L);
        SelCartVo vo2 = new SelCartVo(2L,"양고기.jpg","메에에",54321L,3L);

        List<SelCartVo> testList = new ArrayList();
        testList.add(vo1);
        testList.add(vo2);

        when(mapper.selCart(anyLong())).thenReturn(testList);

        List<SelCartVo> realList = service.selCart(anyLong());

        assertEquals(realList.size(),testList.size());
        assertEquals(realList.get(0).getIcart(),testList.get(0).getIcart());
        assertEquals(realList.get(0).getQuantity(),testList.get(0).getQuantity());
        assertEquals(realList.get(0).getPrice(),testList.get(0).getPrice());
        assertEquals(realList.get(0).getPic(),testList.get(0).getPic());
        assertEquals(realList.get(0).getName(),testList.get(0).getName());

        assertEquals(realList.get(1).getIcart(),testList.get(1).getIcart());
        assertEquals(realList.get(1).getQuantity(),testList.get(1).getQuantity());
        assertEquals(realList.get(1).getPrice(),testList.get(1).getPrice());
        assertEquals(realList.get(1).getPic(),testList.get(1).getPic());
        assertEquals(realList.get(1).getName(),testList.get(1).getName());
    }

    @Test
    @DisplayName("CartService - 하나의 장바구니 정보 삭제")
    void delCart() {
        Long testResult = 1L;
        when(mapper.delCart(any())).thenReturn(testResult);

        Long result = service.delCart(anyLong());
        assertEquals(testResult,result);

        verify(mapper).delCart(anyLong());
    }

    @Test
    @DisplayName("CartService - 체크된 장바구니 정보들 삭제")
    void delCartAll() {
        Long testIcart1 = 1L;
        Long testIcart2 = 2L;
        Long testResult = 1L;
        Long delCount = 2L;

        List<Long> testIcart = new ArrayList();
        testIcart.add(testIcart1);
        testIcart.add(testIcart2);

        when(mapper.delCart(anyLong())).thenReturn(testResult);

        Long result = service.delCartAll(testIcart);
        assertEquals(result, delCount);

        verify(mapper, times(2)).delCart(anyLong());
    }
}