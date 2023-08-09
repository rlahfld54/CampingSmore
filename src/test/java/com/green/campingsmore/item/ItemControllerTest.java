/*
package com.green.campingsmore.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.MockMvcConfig;
import com.green.campingsmore.item.model.ItemEntity;
import com.green.campingsmore.item.model.ItemInsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;



import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
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
    void postItem() throws Exception {
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
        picUrl1.add("DetailPic1.jpg");
        picUrl1.add("DetailPic2.jpg");
        picUrl1.add("DetailPic3.jpg");
        item.setPicUrl(picUrl1);

        ObjectMapper om = new ObjectMapper();
        String jsonParam = om.writeValueAsString(item);


        mvc.perform(post("/api/item/itempost")
                        .content(jsonParam)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("75"))
                        .andDo(print());

        verify(service).insItem(any());












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
*/
