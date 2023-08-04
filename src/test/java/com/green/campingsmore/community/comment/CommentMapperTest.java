package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.comment.model.CommentEntity;
import com.green.campingsmore.community.comment.model.CommentInsDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentMapperTest {
    @Autowired
    private CommentMapper mapper;


    @Test
    void insComment() {
        CommentEntity entity = new CommentEntity();
        entity.setIboard(9L);
        entity.setIuser(1L);
        entity.setCtnt("테스트1");



        Long result = mapper.insComment(entity);
        assertEquals(1,result);
        assertEquals(9,entity.getIboard());
        assertEquals(1,entity.getIuser());
    }
    @Test
    void updComment() {
    }

    @Test
    void delComment() {
    }

    @Test
    void selComment() {
    }

    @Test
    void maxComment() {
    }
}