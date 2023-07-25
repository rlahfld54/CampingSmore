package com.green.campingsmore.community.board.model;

import lombok.Data;

import java.util.List;

@Data
public class BoardEntity {
    private Long iboard;
    private Long iuser;
    private Long icategory;
    private String title;
    private String ctnt;
    private String createdat;
    private String updatedat;
    private int delyn;
    private int boardview;
    private Long iboardpic;
    private String pic;
}
