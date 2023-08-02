package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    Long insBoard(BoardEntity entity);
    Long insBoardPic(List<BoardPicEntity> pic);
    List<BoardMyVo> selMyBoard(BoardMyDto dto);
    Long delBoard(BoardDelDto dto);
    List<BoardListVo> selBoardList(BoardPageDto dto);
    Long maxBoard();
    List<BoardListVo> categoryBoardList(BoardPageDto dto);
    List<BoardSelVo> selBoard(BoardSelPageDto dto);
    Long maxSelBoard(BoardSelPageDto dto);
    BoardDeVo deBoard(BoardDeDto dto);
    List<BoardPicVo> picBoard(BoardDeDto dto);
    Long updBoard(BoardEntity entity);
    Long delPic(BoardPicEntity pic);
}
