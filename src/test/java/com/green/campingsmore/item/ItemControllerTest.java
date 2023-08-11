package com.green.campingsmore.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.green.campingsmore.MockMvcConfig;
import com.green.campingsmore.item.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@MockMvcConfig
@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService service;

    @Test
    void getCategory() {

    }

    @Test
    @DisplayName("Item - 아이템 추가")
    void postItem() throws Exception {
        Long result = 1L;

        given(service.insItem(any())).willReturn(result);

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
                        .andExpect(content().string("1"))
                        .andDo(print());

        verify(service).insItem(any());





    }

    @Test
    void getSearchItem() {

/*
        Long cate = 11L;
        String text = "고기";
        int page = 1;
        int row = 3;
        int sort = 0;

        ItemSearchDto dto = new ItemSearchDto();
        dto.setText(text);
        dto.setPage(page);
        dto.setRow(row);
        dto.setIitemCategory(cate);
        dto.setSort(sort);
*/



//        given(service.searchItem(dto).willReturn();





    }

    @Test
    void delItem() {

    }

    @Test
    void getItemDetail() {

    }

    @Test
    @DisplayName("Item - 아아템 상세이미지 업로드")
    void insDetailPic() throws  Exception{
        List<ItemInsDetailDto> listDto = new ArrayList<>();
        listDto.add(new ItemInsDetailDto(1L,"test1.jpg"));
        listDto.add(new ItemInsDetailDto(1L,"test2.jpg"));
        listDto.add(new ItemInsDetailDto(1L,"test3.jpg"));

        ItemInsDetailPicDto dto = new ItemInsDetailPicDto();
        dto.setIitem(1L);
        List<String> picUrl = new ArrayList<>();
        picUrl.add("test1.jpg");
        picUrl.add("test2.jpg");
        picUrl.add("test3.jpg");
        dto.setPicUrl(picUrl);

        int result = picUrl.size();

        given(service.insDetailPic(dto)).willReturn(result);


        ObjectMapper om = new ObjectMapper();
        String jsonDto = om.writeValueAsString(dto);


        mvc.perform(post("/api/item/detailpic")
                        .content(jsonDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("3"))
                .andDo(print());

        verify(service).insDetailPic(any());



    }

    @Test
    @DisplayName("Item - 아아템 상세이미지 삭제")
    void delDetailPic() throws Exception{
        Long iitem = 1L;

        int result = 1;
        given(service.delDetailPic(iitem)).willReturn(result);

        Long iitem1 = 1L;

        mvc.perform(delete("/api/item/detail/deletepic?iitem=" + iitem1))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(result)))
                .andDo(print());

        verify(service).delDetailPic(any());

    }

    @Test
    @DisplayName("Item - 추천 아이템 추가")
    void insBestItem() throws Exception{

        ItemInsBestDto dto = new ItemInsBestDto();
        dto.setIitem(8L);
        dto.setMonthLike(LocalDate.parse("2023-08-11"));

        int result = 1;
        given(service.insBestItem(dto)).willReturn(result);

        ObjectMapper om = new ObjectMapper();
        String json1 = om.registerModule(new JavaTimeModule()).writeValueAsString(dto);


        mvc.perform(post("/api/item/bestitem")
                        .content(json1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("1"))
                        .andDo(print());

        verify(service).insBestItem(any());
    }

    @Test
    @DisplayName("Item - 추천 아이템 리스트")
    void getBestItem() throws Exception {
        List<ItemVo> voList = new ArrayList<>();
        voList.add(new ItemVo(8L,"양꼬치","test1.jpg",18500, LocalDate.parse("2023-08-26")));
        voList.add(new ItemVo(9L,"바베큐 ","test2.jpg",20000, LocalDate.parse("2023-08-26")));
        voList.add(new ItemVo(10L,"바베큐 ","test3.jpg",15000, LocalDate.parse("2023-08-26")));
        voList.add(new ItemVo(11L,"바베큐 ","test4.jpg",10500, LocalDate.parse("2023-08-26")));
        given(service.selBestItem()).willReturn(voList);

        mvc.perform(get("/api/item/bestitem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[*].iitem").exists())
                .andExpect(jsonPath("$[*].name").exists())
                .andExpect(jsonPath("$[*].pic").exists())
                .andExpect(jsonPath("$[*].price").exists())
                .andExpect(jsonPath("$[*].createdAt").exists())

                .andExpect(jsonPath("$[*].iitem").isNotEmpty())
                .andExpect(jsonPath("$[*].name").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty())
                .andExpect(jsonPath("$[*].createdAt").isNotEmpty())
                .andDo(print());


    }
}
