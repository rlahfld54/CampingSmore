package com.green.campingsmore.community.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BoardDeVo {
    private Long iboard;
    private String category;
    private String title;
    private String ctnt;
    private String name;
    private LocalDateTime createdat;
}
