package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.io.File;
import java.util.List;

@Mapper
public interface BoardMapper {
    Long insBoard(BoardEntity entity);//pk값 반환
    Long updBoardMain(BoardEntity entity);//게시글 작성
    Long insBoardPic(List<BoardPicEntity> pic);//사진 다중 업로드
    List<BoardMyVo> selMyBoard(BoardMyDto dto);//내가 작성한글 보기- 마이페이지에서 사용
    Long delBoard(BoardDelDto dto);//게시글 삭제 하기
    List<BoardListVo> selBoardList(BoardPageDto dto);//게시글 리스트 보기
    Long maxBoard();//맥스 페이지
    List<BoardListVo> categoryBoardList(BoardPageDto dto);//카테고리별 게시글 리스트 보기
    List<BoardSelVo> selBoard(BoardSelPageDto dto);//제목으로 검색
    Long maxSelBoard(BoardSelPageDto dto);// 검색된거 맥스개수
    BoardDeVo deBoard(BoardDeDto dto);//게시글 디테일
    List<BoardPicVo> picBoard(BoardDeDto dto); // 게시글에 있는 사진 여러장
    Long updBoard(BoardEntity entity);//
    Long delPic(BoardPicEntity pic);
    Long viewBoard(BoardDeDto dto); // 조회수 올리기
    Long delWriteBoard(Long iboard);//작성중인 글 내용 삭제
    Long delPicBoard(Long iboard); // 작성중인 사진 삭제
    Long insBoardOnePic(BoardPicEntity pic); // 사진 한장 업로드
    Long delOnePic(BoardPicDelDto dto); // 사진 한장 삭제
    Long delBoardPic(Long iboard);
    String selPicName(Long iboardPic);
}