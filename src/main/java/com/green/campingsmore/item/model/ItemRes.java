package com.green.campingsmore.item.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ItemRes {
    private int row;
    private int maxPage;
    private int page;
    private List<ItemVo> list;
}
