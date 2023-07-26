package com.green.campingsmore.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
public class ItemInsParam {
    private Long iitemCategory;
    private String name;
    private int price;
    private String pic;
}
