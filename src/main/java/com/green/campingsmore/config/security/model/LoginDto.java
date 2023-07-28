package com.green.campingsmore.config.security.model;

import lombok.Data;

@Data
public class LoginDto {
    private Long iuser;
    private String uid;
    private String upw;
    private String name;
    private String role;
}
