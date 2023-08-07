package com.green.campingsmore.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ReviewInsDto {
    private Long iuser;
    private Long iorder;
    private Long iitem;
    private String reviewCtnt;
    private int starRating;
}
