package com.green.campingsmore.item.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemEntity {
    private Long iitem;
    private Long iitemCategory;
    private String name;
    private int price;
    private String info;
    private String pic;
    private List<String> picUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int delYn;
}
