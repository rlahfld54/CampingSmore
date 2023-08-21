package com.green.campingsmore.sign.model;

import lombok.Data;

@Data
public class UpdateUserInfoDto {
    private String uid;
    private String upw;
    private String email;
    private String name;
    private String birth_date;
    private String phone;
    private String user_address;
    private String user_address_detail;
}
