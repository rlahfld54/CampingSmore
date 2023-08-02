package com.green.campingsmore.community.board.model;

import com.green.campingsmore.community.comment.model.CommentRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class BoardCmtDeVo {
    private BoardDeVo boardDeVo;
    private List<BoardPicVo> picList;
    private CommentRes commentList;

}
