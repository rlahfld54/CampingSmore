package com.green.campingsmore.item;

import com.green.campingsmore.item.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {


    //카테고리
    int insCate(String categoryName);
    //카테고리 보기
    List<ItemSelCateVo> selCategory();
//    String selCate(String categoryName);
    Long selIcate(String categoryName);

    // 아이템 추가
    int insItem(ItemEntity entity);
    int selLastItem(ItemSearchDto2 dto);
    List<ItemVo> searchItem(ItemSearchDto2 dto);
    // 아이템 상세페이지
    ItemSelDetailVo selDetail(Long iitem);
    List<String> selDetailPic(Long iitem);
    int delDetail(Long iitem);
    // 아이템 상세페이지 변경 del->ins
    int delDetailPic(Long iitem);
    int insDetailPic(ItemDetailInsDto dto);

    // 추천 아이템
    int insBestItem(ItemInsBest dto);
    List<ItemVo> selBestItem();
}
