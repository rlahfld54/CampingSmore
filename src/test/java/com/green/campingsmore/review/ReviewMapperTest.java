/*
package com.green.campingsmore.review;

import com.green.campingsmore.review.model.ReviewEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewMapperTest {

    @Autowired
    private ReviewMapper mapper;

    @Test
    @DisplayName("ReviewMapperTest - 리뷰 추가")
    void insReview() {
        ReviewEntity entity = new ReviewEntity();
        entity.setIuser(1L);
        entity.setIorder(1L);
        entity.setIitem(1L);
        entity.setReviewCtnt("내용 테스트");
        entity.setStarRating(5);

        int result = mapper.insReview(entity);
        assertEquals(1, result);

    }

    @Test
    @DisplayName("ReviewMapperTest - 리뷰를 작성 할 주문 확인")
    void selReviewOrder() {
        ReviewEntity entity = new ReviewEntity();
        entity.setIuser(1L);
        entity.setIorder(1L);
        entity.setIitem(1L);

        int result = mapper.selReviewOrder(entity);
        assertEquals(1, result);
    }

    @Test
    void selLastReview() {
        Long iitem = 1L;

        int result = mapper.selLastReview(iitem);
        log.info("result :{}",result);
    }

    @Test
    void selReview() {
    }

    @Test
    void updReview() {
    }

    @Test
    void updReviewPic() {
    }

    @Test
    void delReview() {
    }
}*/
