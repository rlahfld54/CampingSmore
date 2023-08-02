package com.green.campingsmore.item.model;

import lombok.Data;

@Data
public class ItemSearchDto2 {
    private String text;
    private Long iitemCategory;
    private int sort;
    private int startIdx;
    private int page;
    private int row;
}
