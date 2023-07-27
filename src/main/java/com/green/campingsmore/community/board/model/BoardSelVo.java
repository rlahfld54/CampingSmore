package com.green.campingsmore.community.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BoardSelVo {
    private String title;
    private String name;
    private LocalDate createdat;
    private int boardview;
}
