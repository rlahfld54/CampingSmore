package com.green.campingsmore.review;

import com.green.campingsmore.review.model.ReviewInsDto;
import com.green.campingsmore.review.model.ReviewPageDto;
import com.green.campingsmore.review.model.ReviewRes;
import com.green.campingsmore.review.model.ReviewSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int insReview(ReviewInsDto dto);
    List<ReviewSelVo> selReview(ReviewPageDto dto);
}
