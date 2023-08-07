package com.green.campingsmore.review;

import com.green.campingsmore.review.model.ReviewInsDto;
import com.green.campingsmore.review.model.ReviewPageDto;
import com.green.campingsmore.review.model.ReviewRes;
import com.green.campingsmore.review.model.ReviewSelVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewMapper MAPPER;

/*    public int insReview(ReviewInsDto dto) {

        return MAPPER.insReview(dto);
    }*/

    public ReviewRes selReview(ReviewPageDto dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());
        List<ReviewSelVo> list = MAPPER.selReview(dto);
        return ReviewRes.builder()
                .iitem(dto.getIitem())
                .page(dto.getPage())
                .row(dto.getRow())
                .startIdx(dto.getStartIdx())
                .list(list)
                .build();
    }


}
