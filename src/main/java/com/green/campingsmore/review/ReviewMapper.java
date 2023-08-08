package com.green.campingsmore.review;

import com.green.campingsmore.review.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int insReview(ReviewEntity entity);
    List<ReviewSelVo> selReview(ReviewPageDto dto);
    int updReview(ReviewEntity entity);
    int updReviewPic(ReviewEntity entity);

}
