package com.green.campingsmore.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSelDetailDto {
    private Long iitem;
    private int startIdx;
    private int page;
    private int row;
}
