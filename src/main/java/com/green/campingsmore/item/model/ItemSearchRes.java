package com.green.campingsmore.item.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class ItemSearchRes {
    private String text;
    private int row;
    private int startIdx;
    private int page;
    private List<ItemVo> list;
}
