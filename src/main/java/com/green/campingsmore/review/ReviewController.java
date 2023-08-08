package com.green.campingsmore.review;

import com.green.campingsmore.review.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name="리뷰")
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService SERVICE;

/*    @PostMapping
    @Operation(summary = "리뷰 추가"
            , description = "" +
            "\"iuser\": [-] 유저 PK,<br>" +
            "\"iorder\": [-]  아이템 썸네일 pic url,<br>" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"reviewCtnt\": [-] 리뷰 내용,<br>" +
            "\"starRating\": [-] 별점,<br>" +
            "\"pic\": [-] 사진 이미지<br>")
    public String postReview(@RequestBody ReviewInsDto dto) {
        return SERVICE.insReview(dto);
    }*/

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 추가"
            , description = "" +
            "\"iuser\": [-] 유저 PK,<br>" +
            "\"iorder\": [-]  아이템 썸네일 pic url,<br>" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"reviewCtnt\": [-] 리뷰 내용,<br>" +
            "\"starRating\": [-] 별점,<br>" +
            "\"pic\": [-] 사진 이미지<br>")
    public String postReview( ReviewInsDto dto,
                           @RequestPart(required = false) MultipartFile pic) {
        return SERVICE.insReview(dto, pic);
    }

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

    @PutMapping(value = "리뷰 수정",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 수정"
            , description = "" +
            "\"iuser\": [-] 유저 PK,<br>" +
            "\"iorder\": [-]  아이템 썸네일 pic url,<br>" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"reviewCtnt\": [-] 리뷰 내용,<br>" +
            "\"starRating\": [-] 별점,<br>" +
            "\"pic\": [-] 사진 이미지<br>")
    public String updReview(ReviewUpdDto dto,
                             @RequestPart(required = false) MultipartFile pic) {
        return SERVICE.updReview(dto, pic);
    }

/*    @PutMapping(value = "리뷰 사진 수정",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 사진 수정"
            , description = "" +
            "\"iuser\": [-] 유저 P,K<br>"+
            "\"ireview\": [-] 리뷰 PK,<br>"+
            "\"pic\": [-] 사진 이미지<br>" )
    public String putReviewPic(@RequestPart MultipartFile pic,
                               @RequestParam Long iuser,
                               @RequestParam Long ireview) {
        ReviewPicDto dto = new ReviewPicDto();
        dto.setIuser(iuser);
        dto.setIreview(ireview);
        return SERVICE.updReviewPic(pic, dto);
    }*/

}
