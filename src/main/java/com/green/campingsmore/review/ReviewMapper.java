package com.green.campingsmore.review;

import com.green.campingsmore.review.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int insReview(ReviewEntity entity);
//    int selReviewOrder(Long iorder, Long iuser, Long iitem);
    int selReviewCheck(ReviewEntity entity);
    int selReviewOrder(ReviewEntity entity);
    int selLastReview(Long iitem);
    List<ReviewSelVo> selReview(ReviewPageDto dto);
    int updReview(ReviewEntity entity);
    int updReviewPic(ReviewEntity entity);
    int delReview(ReviewDelDto dto);


}
