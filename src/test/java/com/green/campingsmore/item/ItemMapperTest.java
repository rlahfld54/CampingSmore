package com.green.campingsmore.item;

import com.green.campingsmore.item.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemMapperTest {

    @Autowired
    private ItemMapper mapper;

    @Test
    @DisplayName("Item - 카테고리 리스트")
    void selCategory() {
        List<ItemSelCateVo> voList = mapper.selCategory();
        assertEquals(5, voList.size());

        ItemSelCateVo vo1 = voList.get(0);
        assertEquals(11L, vo1.getIitemCategory());
        assertEquals("축산물",vo1.getName());


        ItemSelCateVo vo2 = voList.get(1);
        assertEquals(16L, vo2.getIitemCategory());
        assertEquals("수산물",vo2.getName());

    }

    @Test
    @DisplayName("Item - 아이템 추가")
    void insItem() {
        ItemEntity entity = new ItemEntity();
        entity.setIitemCategory(11L);
        entity.setName("상품명");
        entity.setPic("main.jpg");
        entity.setPrice(16500);

        List<String> picUrl = new ArrayList<>();
        picUrl.add("test1.jpg");
        picUrl.add("test2.jpg");
        picUrl.add("test3.jpg");
        entity.setPicUrl(picUrl);

        Long result = mapper.insItem(entity);
        log.info("result:{}",result);
        assertEquals(1, result);
        assertEquals(75L , entity.getIitem());



    }

    @Test
    void selLastItem() {
        ItemSearchDto dto = new ItemSearchDto();
        dto.setIitemCategory(11L);
        dto.setText("양고기");

        int result = mapper.selLastItem(dto);
        assertEquals(2, result);


    }

    @Test
    void searchItem() {
        ItemSearchDto dto = new ItemSearchDto();
        dto.setIitemCategory(11L);
        dto.setText("양고기");
        dto.setPage(1);
        dto.setRow(15);
        dto.setSort(0);

        List<ItemVo> voList = new ArrayList<>();
        voList.add(new ItemVo(8L,"램 양꼬치 양깍두기 양고기 캠핑음식 1kg","https://shopping-phinf.pstatic.net/main_8245209/82452098289.5.jpg",18500, LocalDate.parse("2023-07-26")));
        voList.add(new ItemVo(1L, "양의나라 유기농 양고기 양갈비 양꼬치 프렌치렉 숄더랙 캠핑 냉장 냉동","https://shopping-phinf.pstatic.net/main_8014052/80140522706.10.jpg",16500,LocalDate.parse("2023-07-25")));



        List<ItemVo> result = mapper.searchItem(dto);
        assertEquals(voList,result);

    }

    @Test
    void delItem() {
    }

    @Test
    void selDetail() {
    }

    @Test
    void selDetailPic() {
    }

    @Test
    void delDetailPic() {
    }

    @Test
    void insDetailPic() {
    }

    @Test
    void insBestItem() {
    }

    @Test
    void selBestItem() {
    }
}