package com.green.campingsmore.community.board;

import com.example.campingsmore.community.board.model.BoardEntity;
import com.example.campingsmore.community.board.model.BoardInsDto;
import com.example.campingsmore.community.board.model.BoardPicEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    Long insBoard(BoardEntity entity);
    Long insBoardPic(List<BoardPicEntity> pic);
}
