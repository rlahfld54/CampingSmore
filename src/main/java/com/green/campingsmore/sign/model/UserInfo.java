package com.green.campingsmore.sign.model;

import lombok.Data;

@Data
public class UserInfo {
    private int iuser;
    private String user_id;
    private String name;
    private String email;
    private String birth_date;
    private String phone;
    private String gender;
    private String user_address;
    private String user_address_detail;
    private String role;
    private String pic;
}
