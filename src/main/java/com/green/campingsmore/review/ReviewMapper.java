package com.green.campingsmore.review;

import com.green.campingsmore.review.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int insReview(ReviewInsDto dto);
    List<ReviewSelVo> selReview(ReviewPageDto dto);

}
