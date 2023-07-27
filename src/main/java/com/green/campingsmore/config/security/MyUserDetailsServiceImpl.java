package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.LoginDto;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.config.security.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginDto loginDto = mapper.getByUid(username);
        return MyUserDetails.builder()
                .uid(loginDto.getUid())
                .iuser(loginDto.getIuser())
                .upw(loginDto.getUpw())
                .roles(Collections.singletonList(loginDto.getRole()))
                .build();
    }
}
