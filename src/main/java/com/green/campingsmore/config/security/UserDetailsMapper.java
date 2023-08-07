package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.LoginDto;
import com.green.campingsmore.config.security.model.SignUpDto;
import com.green.campingsmore.config.security.model.UserTokenDto;
import com.green.campingsmore.config.security.model.UserTokenEntity;
import com.green.campingsmore.sign.model.UpdatePwDto;
import com.green.campingsmore.sign.model.UpdateUserInfoDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {
    int signUp(SignUpDto dto);
    LoginDto getByUid(String uid);
    int updUserToken(UserTokenDto dto);
//    UserTokenEntity selUserToken(UserTokenEntity p);
    int delYnUser(int iuser);
    String searchID(String name,String phone,String birth);
    int updateUserInfo(UpdateUserInfoDto updateUserInfoDto);
    int searchPW(UpdatePwDto updatePwDto);
}
