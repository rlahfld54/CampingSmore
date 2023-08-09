package com.green.campingsmore.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.MockMvcConfig;
import com.green.campingsmore.item.model.ItemEntity;
import com.green.campingsmore.item.model.ItemInsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@MockMvcConfig
@WebMvcTest(ItemControllerTest.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService service;

    @Test
    void getCategory() {

    }

    @Test
    void postItem() throws JsonProcessingException {
        ItemInsDto dto = new ItemInsDto();
        dto.setIitemCategory(11L);
        dto.setName("상품명");
        dto.setPic("main.jpg");
        dto.setPrice(5000);

        List<String> picUrl = new ArrayList<>();
        picUrl.add("DetailPic1.jpg");
        picUrl.add("DetailPic2.jpg");
        picUrl.add("DetailPic3.jpg");
        dto.setPicUrl(picUrl);

        Long result = 75L;

        given(service.insItem(dto)).willReturn(result);

        //
        ItemInsDto item = new ItemInsDto();
        item.setIitemCategory(11L);
        item.setName("상품명");
        item.setPic("main.jpg");
        item.setPrice(5000);

        List<String> picUrl1 = new ArrayList<>();
        picUrl.add("DetailPic1.jpg");
        picUrl.add("DetailPic2.jpg");
        picUrl.add("DetailPic3.jpg");

        ObjectMapper om = new ObjectMapper();
        String jsonParam = om.writeValueAsString(item);










    }

    @Test
    void getSearchItem() {
    }

    @Test
    void delItem() {
    }

    @Test
    void getItemDetail() {
    }

    @Test
    void insDetailPic() {
    }

    @Test
    void delDetailPic() {
    }

    @Test
    void insBestItem() {
    }

    @Test
    void getBestItem() {
    }
}