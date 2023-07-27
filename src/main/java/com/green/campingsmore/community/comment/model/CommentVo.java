package com.green.campingsmore.community.comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentVo {
    public Long iboard;
    private String name;
    private String ctnt;
    private LocalDateTime createdAt;
}
