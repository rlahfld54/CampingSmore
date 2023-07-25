package com.green.campingsmore.item;

import com.green.campingsmore.item.model.ItemInsDto;
import com.green.campingsmore.item.model.ItemInsParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
    int insItem(ItemInsParam p);
    int selCate(ItemInsParam cate);
}
