package com.green.campingsmore.review;

import com.green.campingsmore.review.model.ReviewInsDto;
import com.green.campingsmore.review.model.ReviewPageDto;
import com.green.campingsmore.review.model.ReviewRes;
import com.green.campingsmore.review.model.ReviewSelVo;
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

    @PostMapping("/write")
    public int postReview(@RequestBody ReviewInsDto dto) {
        return SERVICE.insReview(dto);
    }

    @GetMapping("/{iitem}/detail")
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
