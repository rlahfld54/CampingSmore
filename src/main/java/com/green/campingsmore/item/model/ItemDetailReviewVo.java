package com.green.campingsmore.item.model;

import com.green.campingsmore.review.model.ReviewRes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class ItemDetailReviewVo {
    private ItemSelDetailVo item;
    private ReviewRes review;
}
