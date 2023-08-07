package com.green.campingsmore.admin.user;

import com.green.campingsmore.admin.user.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminUserService {
    private final AdminUserMapper adminUserMapper;

    public List<UserDto> selectAllusers(){
        return adminUserMapper.selectAllusers();
    }

}
