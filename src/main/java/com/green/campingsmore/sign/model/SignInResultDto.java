package com.green.campingsmore.sign.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class SignInResultDto extends SignUpResultDto {
    private int iuser;
    private String accessToken;
    private String refreshToken;
}
