package com.green.campingsmore.item.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemInsParam {
    private Long itemCategory;
    private String name;
    private int price;
    private String pic;
}