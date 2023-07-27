package com.green.campingsmore.item.model;

import lombok.Data;

@Data
public class ItemSelPageDto {
    private Long iitemCategory;
    private int startIdx;
    private int page;
    private int row;
}
