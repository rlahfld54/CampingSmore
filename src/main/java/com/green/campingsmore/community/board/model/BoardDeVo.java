package com.green.campingsmore.community.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BoardDeVo {
    private Long iboard;
    private Long iuser;
    private String name;
    private String category;
    private String title;
    private String ctnt;
    private LocalDateTime createdat;
    private Long boardview;
}