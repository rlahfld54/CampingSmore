package com.example.campingsmore.community.board.model;

import lombok.Data;

import java.util.List;

@Data
public class BoardInsDto {
    private Long iuser;
    private Long icategory;
    private String title;
    private String ctnt;
}