package com.green.campingsmore.review;

import com.green.campingsmore.community.board.utils.FileUtils;
import com.green.campingsmore.review.model.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper MAPPER;
//    private final String FILE_DIR;
//    @Value("${file.dir}")
//    private String fileDir;



    public int insReview(ReviewInsDto dto ) {/*
        ReviewEntity entity = new ReviewEntity();
        entity.setIuser(dto.getIuser());
        entity.setIorder(dto.getIorder());
        entity.setIitem(dto.getIitem());
        entity.setReviewCtnt(dto.getReviewCtnt());
        entity.setStarRating(dto.getStarRating());*/

        return MAPPER.insReview(dto);

    }

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
