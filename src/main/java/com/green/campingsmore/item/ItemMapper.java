package com.green.campingsmore.item;

import com.green.campingsmore.item.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    int insItem(ItemInsParam ipram);
    int insCate(String categoryName);
    List<ItemSelCateVo> selCategory();
    String selCate(String categoryName);
    Long selIcate(String categoryName);
    List<ItemVo> selCateItem(ItemSelCateDto dto);
    ItemSelDetailVo selDetail(Long iitem);
    int insBestItem(ItemInsBest dto);


    int delDetail(Long iitem);
    int insDetail(ItemDetailInsDto dto);
}
