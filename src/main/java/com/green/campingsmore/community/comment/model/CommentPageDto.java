package com.green.campingsmore.community.comment.model;

import lombok.Data;

@Data
public class CommentPageDto {
    private Long iboard;
    private int page;
    private int row;
    private int startIdx;
}
