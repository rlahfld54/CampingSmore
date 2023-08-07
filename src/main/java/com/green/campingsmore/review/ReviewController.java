package com.green.campingsmore.review;

import com.green.campingsmore.review.model.ReviewInsDto;
import com.green.campingsmore.review.model.ReviewPageDto;
import com.green.campingsmore.review.model.ReviewRes;
import com.green.campingsmore.review.model.ReviewSelVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="리뷰")
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService SERVICE;

/*    @PostMapping("/write")
    @Operation(summary = "리뷰 추가"
            , description = "" +
            "\"iuser\": [-] 유저 PK,<br>" +
            "\"iorder\": [-]  아이템 썸네일 pic url,<br>" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"reviewCtnt\": [-] 리뷰 내용,<br>" +
            "\"starRating\": [-] 별점,<br>" +
            "\"pic\": [-] 사진 이미지<br>")
    public int postReview(@RequestBody ReviewInsDto dto) {
        return SERVICE.insReview(dto);
    }*/

    @GetMapping("/{iitem}/detail")
    @Operation(summary = "리뷰 리스트"
            , description = "" +
            "\"iitem\": [-] 아이템 PK<br>" )
    public ReviewRes getReview(@PathVariable Long iitem,
                               @RequestParam(defaultValue = "1")int page,
                               @RequestParam(defaultValue = "5") int row) {
        ReviewPageDto dto = new ReviewPageDto();
        dto.setIitem(iitem);
        dto.setPage(page);
        dto.setRow(row);
        return SERVICE.selReview(dto);
    }

}
