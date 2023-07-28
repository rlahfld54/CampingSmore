package com.green.campingsmore.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ItemVo {
    private Long iitem;
    private String name;
    private String pic;
    private int price;
    private LocalDateTime createdAt;
}
