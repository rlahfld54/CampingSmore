package com.green.campingsmore.review.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReviewRes {
    private Long iitem;
    private int startIdx;
    private int maxPage;
    private int isMore;
    private int page;
    private int row;
    private List<ReviewSelVo> list;
}
