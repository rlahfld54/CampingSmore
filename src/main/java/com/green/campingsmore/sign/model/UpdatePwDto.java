package com.green.campingsmore.sign.model;

import lombok.Data;

@Data
public class UpdatePwDto {
    private String uid;
    private String password;
    private String name;
    private String email;
}
