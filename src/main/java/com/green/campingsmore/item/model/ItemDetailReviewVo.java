package com.green.campingsmore.item.model;

import com.green.campingsmore.review.model.ReviewRes;
import com.green.campingsmore.review.model.ReviewSelVo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class ItemDetailReviewVo {
    private ItemSelDetailVo item;
    private ReviewRes review;
}
