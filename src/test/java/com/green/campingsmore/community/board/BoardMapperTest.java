package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.*;
import com.green.campingsmore.community.comment.CommentMapper;
import com.green.campingsmore.config.security.AuthenticationFacade;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)



class BoardMapperTest {
    @Autowired
    private BoardMapper mapper;

    @MockBean
    private AuthenticationFacade FACADE;

    @Test
    void insBoard() {
        BoardEntity entity = new BoardEntity();
        entity.setIuser(1L);
        entity.setIcategory(1L);
        entity.setCtnt("테스트2");
        entity.setTitle("테스트제목");

        Long board = mapper.insBoard(entity);
        assertEquals(1,board);

    }

    @Test
    void updBoardMain() {
        BoardEntity entity = new BoardEntity();
        entity.setIboard(1L);
        entity.setIuser(1L);
        entity.setIcategory(1L);
        entity.setTitle("hi");
        entity.setCtnt("23");
        Long result = mapper.updBoardMain(entity);
        assertEquals(1,result);
        assertEquals(1,entity.getIboard());
        assertEquals(1,entity.getIuser());
        assertEquals(1,entity.getIcategory());


    }

    @Test
    void insBoardPic() {//나중에
    }

    @Test
    void selMyBoard() {
        BoardMyDto dto = new BoardMyDto();
        dto.setIuser(1L);
        List<BoardMyVo> list = mapper.selMyBoard(dto);

        assertEquals(23,list.size());
        BoardMyVo item = list.get(0);


        assertEquals("123",item.getTitle());


    }

    @Test
    void delBoard() {
        BoardDelDto dto = new BoardDelDto();
        dto.setIboard(1L);
        dto.setIuser(1L);
        Long result = mapper.delBoard(dto);

        assertEquals(1,result);
    }

    @Test
    void selBoardList() {
        BoardPageDto dto = new BoardPageDto();
        dto.setRow(23);
        dto.setStartIdx(0);
        dto.setPage(1);
        dto.setIcategory(1L);
        dto.setTitle("123");
        dto.setMaxpage(1);
        List<BoardListVo> list = mapper.selBoardList(dto);
        assertEquals(23,list.size());

    }

    @Test
    void maxBoard() {
        Long result = mapper.maxBoard();
        assertEquals(24,result);
    }

    @Test
    void categoryBoardList() {
    }

    @Test
    void selBoard() {
    }

    @Test
    void maxSelBoard() {
    }

    @Test
    void deBoard() {
    }

    @Test
    void picBoard() {
    }

    @Test
    void viewBoard() {
    }

    @Test
    void delWriteBoard() {
    }

    @Test
    void delPicBoard() {
    }

    @Test
    void insBoardOnePic() {
    }

    @Test
    void delOnePic() {
    }
}