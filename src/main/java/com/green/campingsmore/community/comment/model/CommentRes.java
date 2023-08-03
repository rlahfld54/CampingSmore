package com.green.campingsmore.community.comment.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class CommentRes {
    private int isMore;
    private int row;
    private int maxpage;
    private int nowPage;
    private List<CommentVo> list;
}
