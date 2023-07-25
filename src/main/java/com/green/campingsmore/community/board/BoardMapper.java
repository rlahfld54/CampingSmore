package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.BoardEntity;
import com.green.campingsmore.community.board.model.BoardInsDto;
import com.green.campingsmore.community.board.model.BoardPicEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    Long insBoard(BoardEntity entity);
    Long insBoardPic(List<BoardPicEntity> pic);
}
