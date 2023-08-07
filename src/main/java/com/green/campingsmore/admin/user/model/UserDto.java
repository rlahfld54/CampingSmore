package com.green.campingsmore.admin.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private int iuser;
    private String user_id;
    private String name;
    private String birth_date;
    private String phone;
    private String gender;
    private String role;
}
