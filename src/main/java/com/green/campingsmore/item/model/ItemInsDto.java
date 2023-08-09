package com.green.campingsmore.item.model;

import lombok.Data;

import java.util.List;

@Data
public class ItemInsDto {
    private Long iitemCategory;
    private String name;
    private String pic;
    private int price;
    private List<String> picUrl;
}
