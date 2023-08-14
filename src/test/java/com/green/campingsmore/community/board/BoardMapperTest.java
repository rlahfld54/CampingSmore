//package com.green.campingsmore.community.board;
//
//import com.green.campingsmore.community.board.model.*;
//import com.green.campingsmore.community.comment.CommentMapper;
//import com.green.campingsmore.config.security.AuthenticationFacade;
//import org.junit.jupiter.api.Test;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//@MybatisTest
//@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//
//
//
//class BoardMapperTest {
//    @Autowired
//    private BoardMapper mapper;
//
//    @MockBean
//    private AuthenticationFacade FACADE;
//
//    @Test
//    void insBoard() {
//        BoardEntity entity = new BoardEntity();
//        entity.setIuser(1L);
//        entity.setIcategory(1L);
//        entity.setCtnt("테스트2");
//        entity.setTitle("테스트제목");
//
//        Long board = mapper.insBoard(entity);
//        assertEquals(1,board);
//
//    }
//
//    @Test
//    void updBoardMain() {
//        BoardEntity entity = new BoardEntity();
//        entity.setIboard(1L);
//        entity.setIuser(1L);
//        entity.setIcategory(1L);
//        entity.setTitle("hi");
//        entity.setCtnt("23");
//        Long result = mapper.updBoardMain(entity);
//        assertEquals(1,result);
//        assertEquals(1,entity.getIboard());
//        assertEquals(1,entity.getIuser());
//        assertEquals(1,entity.getIcategory());
//
//
//    }
//
//    @Test
//    void insBoardPic() {
//        List<BoardPicEntity> list = new ArrayList<>();
//
//        BoardPicEntity entity1 = new BoardPicEntity();
//        entity1.setIboard(17L);
//        entity1.setPic("asdf.jpg");
//
//        BoardPicEntity entity2 = new BoardPicEntity();
//        entity2.setIboard(17L);
//        entity2.setPic("asdf1.jpg");
//
//        list.add(entity1);
//        list.add(entity2);
//
//        Long result = mapper.insBoardPic(list);
//
//        assertEquals(2L, result);
//    }
//
//    @Test
//    void selMyBoard() {
//        BoardMyDto dto = new BoardMyDto();
//        dto.setIuser(1L);
//        List<BoardMyVo> list = mapper.selMyBoard(dto);
//
//        assertEquals(23,list.size());
//        BoardMyVo item = list.get(0);
//
//
//        assertEquals("123",item.getTitle());
//
//
//    }
//
//    @Test
//    void delBoard() {
//        BoardDelDto dto = new BoardDelDto();
//        dto.setIboard(1L);
//        dto.setIuser(1L);
//        Long result = mapper.delBoard(dto);
//
//        assertEquals(1,result);
//    }
//
//    @Test
//    void selBoardList() {
//        BoardPageDto dto = new BoardPageDto();
//        dto.setRow(50);
//        dto.setStartIdx(0);
//        dto.setPage(1);
//        dto.setIcategory(1L);
//        dto.setTitle("123");
//        dto.setMaxpage(1);
//        List<BoardListVo> list = mapper.selBoardList(dto);
//        assertEquals(24,list.size());
//
//    }
//
//    @Test
//    void maxBoard() {
//        Long result = mapper.maxBoard();
//        assertEquals(24,result);
//    }
//
//    @Test
//    void categoryBoardList() {
//        BoardPageDto dto = new BoardPageDto();
//        dto.setMaxpage(20);
//        dto.setRow(50);
//        dto.setStartIdx(0);
//        dto.setPage(1);
//        dto.setIcategory(1L);
//        List<BoardListVo> list = mapper.categoryBoardList(dto);
//        assertEquals(24,list.size());
//
//        BoardListVo vo = list.get(0);
//
//        assertEquals(1L,vo.getIcategory());
//
//    }
//
//    @Test
//    void selBoard() {
//        BoardSelPageDto dto = new BoardSelPageDto();
//        dto.setMaxpage(3);
//        dto.setRow(50);
//        dto.setTitle("123");
//        dto.setPage(1);
//        dto.setStartIdx(0);
//        List<BoardSelVo> list = mapper.selBoard(dto);
//        assertEquals(23,list.size());
//        BoardSelVo item = list.get(0);
//        assertEquals("123",item.getTitle());
//    }
//
//    @Test
//    void maxSelBoard() {
//        BoardSelPageDto dto = new BoardSelPageDto();
//        dto.setTitle("123");
//        dto.setRow(50);
//        dto.setMaxpage(1);
//        dto.setStartIdx(1);
//        dto.setPage(1);
//        Long result = mapper.maxSelBoard(dto);
//        assertEquals(23,result);
//    }
//
//    @Test
//    void deBoard() {
//        BoardDeDto dto = new BoardDeDto();
//        dto.setIboard(1L);
//        BoardDeVo result = mapper.deBoard(dto);
//
//        assertEquals(1L,result.getIuser());
//        assertEquals("중고거래",result.getCategory());
//        assertEquals("123",result.getTitle());
//        assertEquals("123",result.getCtnt());
//    }
//
//    @Test
//    void picBoard() {
//        BoardDeDto dto = new BoardDeDto();
//        dto.setIboard(17L);
//
//        List<BoardPicVo> list = mapper.picBoard(dto);
//        assertEquals(3,list.size());
//        BoardPicVo item1 = list.get(0);
//        assertEquals(17L, item1.getIboard());
//        assertEquals("333844fe-6c82-4d0a-87ec-fc2510af978b.jpeg",item1.getPic());
//
//    }
//
//    @Test
//    void viewBoard() {
//
//        BoardDeDto dto = new BoardDeDto();
//        dto.setIboard(1L);
//        Long result = mapper.viewBoard(dto);
//
//        assertEquals(1L,result);
//    }
//
//    @Test
//    void delWriteBoard() {
//        Long iboard = 2L;
//        Long result = mapper.delWriteBoard(iboard);
//
//        assertEquals(1L,result);
//    }
//
//    @Test
//    void delPicBoard() {
//        Long iboard = 17L;
//        Long result = mapper.delPicBoard(iboard);
//
//        assertEquals(3L,result);
//
//    }
//
//    @Test
//    void insBoardOnePic() {
//        BoardPicEntity entity = new BoardPicEntity();
//        entity.setPic("asdf.jpg");
//        entity.setIboard(17L);
//        Long result = mapper.insBoardOnePic(entity);
//
//        BoardPicEntity entity1 = new BoardPicEntity();
//        entity1.setPic("asdf.jpg");
//        entity1.setIboard(17L);
//        Long result1 = mapper.insBoardOnePic(entity1);
//
////        assertEquals(17L,entity.getIboard());
////        assertEquals(41L,entity.getIboardpic());
////        assertEquals("asdf.jpg",entity.getPic());
//        assertEquals(1L,result);
//        assertEquals(1L,result1);
//    }
//
//    @Test
//    void delOnePic() {
//        BoardPicDelDto dto = new BoardPicDelDto();
//        dto.setIboard(17L);
//        dto.setIboardpic(1L);
//
//        Long result = mapper.delOnePic(dto);
//        assertEquals(1L,result);
//    }
//
//    @Test
//    void insCategory() {
//        String name = "hi";
//        Long result = mapper.insCategory(name);
//
//        assertEquals(1L,result);
//    }
//}