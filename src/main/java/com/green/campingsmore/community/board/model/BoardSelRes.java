package com.green.campingsmore.community.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BoardSelRes {
    private String title;
    private int isMore;
    private int row;
    private int maxPage;
    private int nowPage;
    private int midPage;
    private List<BoardSelVo> list;
}
