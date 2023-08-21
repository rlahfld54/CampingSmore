package com.green.campingsmore.review.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSelVo {
    private Long ireview;
    private String name;
    private String reviewCtnt;
    private String pic;
    private int starRating;
    private int reviewLike;
}
