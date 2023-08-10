package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.comment.model.CommentEntity;
import com.green.campingsmore.community.comment.model.CommentInsDto;
import com.green.campingsmore.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import({CommentService.class})
class CommentServiceTest {

    @MockBean
    private CommentMapper mapper;

    @Autowired
    private CommentService service;

    @MockBean
    private AuthenticationFacade FACADE;

    @Test
    void insComment() {
        when(mapper.insComment(any())).thenReturn(1L);
        CommentInsDto dto = new CommentInsDto();
        CommentEntity entity = new CommentEntity();
        dto.setIboard(1L);
        dto.setCtnt("hi");
        entity.setIuser(FACADE.getLoginUserPk());
        entity.setIboard(dto.getIboard());
        entity.setCtnt(dto.getCtnt());

        Long result = service.insComment(dto);
        assertEquals(1L, result);
        verify(mapper).insComment(any());
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
}