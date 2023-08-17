package com.green.campingsmore.community.board.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardEntity {
    private Long iboard;
    private Long iuser;
    private Long icategory;
    private String title;
    private String ctnt;
    private String pic;
    private Long iboardpic;
}
