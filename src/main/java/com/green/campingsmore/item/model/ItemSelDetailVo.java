package com.green.campingsmore.item.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemSelDetailVo {
    private Long iitem;
    private String name;
    private String pic;
    private int price;
    private LocalDateTime createdAt;
    private List<String> picList;
}
