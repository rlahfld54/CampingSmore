package com.green.campingsmore.config.security.redis.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RedisJwtVo {
    private String accessToken;
    private String refreshToken;
}