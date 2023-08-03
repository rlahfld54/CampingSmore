package com.green.campingsmore.item.model;

import lombok.Data;
@Data
public class ItemSearchDto {
    private String text;
    private int startIdx;
    private int page;
    private int row;
}