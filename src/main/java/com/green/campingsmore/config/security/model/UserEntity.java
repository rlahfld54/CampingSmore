package com.green.campingsmore.config.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long iuser;
    private String uid;
    private String upw;
    private String email;
    private String name;
    private String birth_date;
    private String phone;
    private int gender; // 0이 남자, 1이 여자
    private String user_address;
    private String user_address_detail;
    private String role;
    private String pic;
    private LocalDateTime  created_at;
    private LocalDateTime  updated_at;
    private int del_yn; // default 1
}
