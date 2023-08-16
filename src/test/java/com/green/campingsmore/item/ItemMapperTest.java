package com.green.campingsmore.item;

import com.green.campingsmore.item.model.ItemSelCateVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

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
    void selCategory() {
/*        List<ItemSelCateVo> voList = mapper.selCategory();
        assertEquals(5, voList.size());

        for(ItemSelCateVo vo : voList) {


        }*/
    }

    @Test
    void insItem() {
    }

    @Test
    void selLastItem() {
    }

    @Test
    void searchItem() {
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