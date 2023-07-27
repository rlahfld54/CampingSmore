package com.green.campingsmore.config.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenEntity {
    private Long iuser;
    private String ip;
    private String access_token;
    private String refresh_token;
}
