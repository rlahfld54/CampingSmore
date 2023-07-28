package com.green.campingsmore.item.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemSelDetailVo {
    private Long iitem;
    private String name;
    private String pic;
    private int price;
    private LocalDateTime createdAt;
}
