package com.green.campingsmore.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
public class ReviewEntity {
    private Long ireview;
    private Long iuser;
    private Long iorder;
    private Long iitem;
    private String reviewCtnt;
    private String pic;
    private int starRating;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private int reviewLike;
}
