package com.green.campingsmore.item.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemSelBest {
    private Long iitem;
    private String name;
    private String pic;
    private int price;
    private LocalDate monthLike;
}
