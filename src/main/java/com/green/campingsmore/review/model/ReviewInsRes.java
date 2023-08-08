package com.green.campingsmore.review.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@ToString
public class ReviewInsRes {
    private Long ireview;
    private Long iuser;
    private Long iorder;
    private Long iitem;
    private String reviewCtnt;
    private String pic;
    private LocalDateTime createdAt;
    private int finishYn;

/*    public ReviewInsRes(ReviewInsDto dto) {
        this.iuser = dto.getIuser();
        this.iorder = dto.getIorder();
        this.iitem = dto.getIitem();
        this.reviewCtnt = dto.getReviewCtnt();
        this.pic = dto.pic;
    }*/
}
