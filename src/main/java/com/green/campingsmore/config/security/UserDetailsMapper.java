package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.LoginDto;
import com.green.campingsmore.config.security.model.SignUpDto;
import com.green.campingsmore.sign.model.UpdatePwDto;
import com.green.campingsmore.sign.model.UpdateUserInfoDto;
import com.green.campingsmore.sign.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {
    int signUp(SignUpDto dto);
    LoginDto getByUid(String uid);
    int delYnUser(int iuser);
    String searchID(String name,String phone,String birth);
    int updateUserInfo(UpdateUserInfoDto updateUserInfoDto);
    int searchPW(UpdatePwDto updatePwDto);
    UserInfo getmyInfo(int iuser);
}
