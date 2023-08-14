package com.green.campingsmore.review.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ReviewRes {
    private Long iitem;
    private int startIdx;
    private int maxPage;
    private int isMore;
    private int page;
    private int row;
    private List<ReviewSelVo> list;
}
