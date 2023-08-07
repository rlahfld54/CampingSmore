package com.green.campingsmore.admin.user;

import com.green.campingsmore.admin.user.model.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminUserMapper {
    List<UserDto> selectAllusers();
}
