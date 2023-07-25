package com.green.campingsmore.community.board.model;

import lombok.Data;

@Data
public class BoardPageDto {
    private Long icategory;
    private int row;
    private int page;
    private int startIdx;
    private int maxpage;
}
