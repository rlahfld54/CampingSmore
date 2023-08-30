package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.LoginDto;
import com.green.campingsmore.config.security.model.SearchUserDto;
import com.green.campingsmore.config.security.model.SignUpDto;
import com.green.campingsmore.sign.model.FinalUpdateUserInfo;
import com.green.campingsmore.sign.model.UpdatePwDto;
import com.green.campingsmore.sign.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {
    int signUp(SignUpDto dto);
    LoginDto getByUid(String uid);
    int delYnUser(int iuser);
    String searchID(String name,String phone,String birth);
    int updateUserInfo(FinalUpdateUserInfo finalUpdateUserInfo);
    int searchPW(UpdatePwDto updatePwDto);
    UserInfo getmyInfo(int iuser);
    SearchUserDto searchUser(SearchUserDto searchUserDto);
}
