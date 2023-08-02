package com.green.campingsmore.item.model;

import lombok.Data;

@Data
public class ItemSelDto {
    private Long iitem;
    private int startIdx;
    private int page;
    private int row;
}
