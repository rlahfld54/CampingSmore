package com.green.campingsmore.sign.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SignUpResultDto {
    private boolean success;
    private int code;
    private String msg;
}
