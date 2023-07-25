package com.green.campingsmore.community.board.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListVo {
    private Long iboard;
    private Long icategory;
    private String name;
    private String title;
    private String ctnt;
    private LocalDateTime createdat;
    private Long boardview;
}
