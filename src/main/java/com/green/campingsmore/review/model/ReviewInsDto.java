package com.green.campingsmore.review.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class ReviewInsDto {
    private Long iuser;
    private Long iorder;
    private Long iitem;
    private String reviewCtnt;
    private int starRating;
    private String pic;
}
