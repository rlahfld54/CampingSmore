package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.comment.model.*;
import com.green.campingsmore.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
        given(mapper.insComment(any())).willReturn(2L);
        CommentInsDto dto = new CommentInsDto();
        CommentEntity entity = new CommentEntity();
        dto.setIboard(1L);
        dto.setCtnt("hi");
        entity.setIuser(FACADE.getLoginUserPk());
        entity.setIboard(dto.getIboard());
        entity.setCtnt(dto.getCtnt());

        Long result = service.insComment(dto);
        assertEquals(2L, result);
        verify(mapper).insComment(any());
    }

    @Test
    void updComment() {
        given(mapper.updComment(any())).willReturn(1L);
        CommentEntity entity = new CommentEntity();
        entity.setIuser(FACADE.getLoginUserPk());
        entity.setCtnt("hi");
        Long result = service.updComment(entity);
        assertEquals("hi",entity.getCtnt());
        assertEquals(1L,result);
        verify(mapper).updComment(any());
    }

    @Test
    void delComment() {

        CommentEntity entity = new CommentEntity();
        entity.setIuser(FACADE.getLoginUserPk());
        given(mapper.delComment(any())).willReturn(1L);
        Long result = service.delComment(entity);
        assertEquals(1L,result);
        verify(mapper).delComment(any());
    }

    @Test
    public void selComment() {
        CommentPageDto dto = new CommentPageDto();
        // 설정: dto에 필요한 값들을 설정합니다.
        dto.setPage(1);
        dto.setRow(10);
        // 이하 필요한 설정들을 추가합니다.

        // 가짜 데이터와 메서드 동작 설정
        List<CommentVo> list= new ArrayList<>(); // 가짜 댓글 리스트 생성
        given(mapper.selComment(dto)).willReturn(list); // selComment 메서드가 호출될 때 가짜 리스트를 반환하도록 설정
        given(mapper.maxComment(dto)).willReturn(35L); // maxComment 메서드가 호출될 때 가짜 최대 페이지 값을 반환하도록 설정

        // 테스트 실행
        CommentRes result = service.selComment(dto);

        // 예상 결과와 실제 결과 비교
        assertEquals(1, result.getNowPage()); // 예상: 1 페이지
        assertEquals(4, result.getMaxpage()); // 예상: 35개의 댓글을 10개씩 보여줄 때 4 페이지
        assertEquals(1, result.getIsMore()); // 예상: 현재 페이지가 최대 페이지보다 작으므로 더 많은 댓글이 있는 상태
        // 이하 필요한 비교 및 검증들을 추가합니다.

    }
}