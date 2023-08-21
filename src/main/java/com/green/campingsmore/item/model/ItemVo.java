package com.green.campingsmore.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVo {
    private Long iitem;
    private String name;
    private String pic;
    private int price;
    private LocalDate createdAt;


}
