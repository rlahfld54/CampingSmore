package com.green.campingsmore.review.model;

import lombok.Data;

@Data
public class ReviewPageDto {
    private Long iitem;
    private int startIdx;
    private int page;
    private int row;
}
