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

    // 아이템 상세페이지
    ItemSelDetailVo selDetail(Long iitem);
    List<String> selDetailPic(Long iitem);

    // 추천 아이템
    int insBestItem(ItemInsBest dto);
    List<ItemVo> selBestItem();


    int delDetailPic(Long iitem);
    int insDetailPic(ItemDetailInsDto dto);
}
