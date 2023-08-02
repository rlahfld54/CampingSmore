package com.green.campingsmore.community.board.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardUpdDto {
    private Long iboard;
    private Long iuser;
    private String title;
    private String ctnt;
    private Long iboardpic;
}
