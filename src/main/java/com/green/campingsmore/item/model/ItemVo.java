package com.green.campingsmore.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ItemVo {
    private Long iitem;
    private String name;
    private String pic;
    private int price;
    private LocalDate createdAt;

    public ItemVo(long l, String s, String url, int i, LocalDate parse) {
    }
}
