package com.green.campingsmore.config.security.model;

import lombok.Data;
import lombok.Getter;

@Data
public class UserTokenDto {
    private Long iuser;
    private String ip;
    private String accessToken;
    private String refreshToken;
}
