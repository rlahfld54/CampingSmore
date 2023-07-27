package com.green.campingsmore.item;

import com.green.campingsmore.item.model.ItemInsParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
    int insItem(ItemInsParam ipram);
    int insCate(String categoryName);
    String selCate(String categoryName);
    Long selIcate(String categoryName);

    /*selCateItem();*/
}
