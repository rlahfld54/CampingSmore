package com.green.campingsmore.item;

import com.green.campingsmore.item.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    int insItem(ItemInsParam ipram);
    int insCate(String categoryName);
//    List<ItemVo> searchItem(ItemSearchDto dto);
    List<ItemVo> searchItem(ItemSearchDto2 dto);
    List<ItemSelCateVo> selCategory();
    String selCate(String categoryName);
    Long selIcate(String categoryName);

    ItemSelDetailVo selDetail(Long iitem);
    int insBestItem(ItemInsBest dto);
    List<ItemVo> selBestItem();


    int delDetail(Long iitem);
    int insDetail(ItemDetailInsDto dto);
}
