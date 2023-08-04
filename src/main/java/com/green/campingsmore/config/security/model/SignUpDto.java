package com.green.campingsmore.config.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String uid;
    private String upw;
    private String email;
    private String name;
    private String birth_date;
    private String phone;
    private int gender;
    private String user_address;
    private String role;
}
