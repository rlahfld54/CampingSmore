package com.green.campingsmore.config.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchUserDto {
    private String uid;
    private String name;
    private String email;
}
