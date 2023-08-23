package com.green.campingsmore.community.board.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardMyVo {
    private Long iboard;
    private Long icategory;
    private String title;
    private LocalDateTime createdat;
    private Long boardview;
}
