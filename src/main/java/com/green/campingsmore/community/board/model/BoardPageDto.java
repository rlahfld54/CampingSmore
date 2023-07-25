package com.green.campingsmore.community.board.model;

import lombok.Data;

@Data
public class BoardPageDto {
    private int row;
    private int page;
    private int startIdx;
    private int maxpage;
}
