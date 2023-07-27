package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.LoginDto;
import com.green.campingsmore.config.security.model.SignUpDto;
import com.green.campingsmore.config.security.model.UserTokenDto;
import com.green.campingsmore.config.security.model.UserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {
    int save(SignUpDto dto);
    LoginDto getByUid(String uid);
    int updUserToken(UserTokenDto dto);
    UserTokenEntity selUserToken(UserTokenEntity p);
    int delYnUser(int iuser);
}
