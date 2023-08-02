package com.green.campingsmore.review.model;

import lombok.Data;

@Data
public class ReviewSelVo {
    private Long ireview;
    private String name;
    private String reviewCtnt;
    private int starRating;
    private int reviewLike;
}
