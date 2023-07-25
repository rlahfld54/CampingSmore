package com.green.campingsmore.item.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemInsDto {
    private String Category;
    private String name;
    private int price;
    private String pic;
}
