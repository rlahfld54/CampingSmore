package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public MyUserDetails getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("황주은 - Authentication auth = "+auth);
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        System.out.println("황주은 - AuthenticationFacade의 MyUserDetails = "+userDetails);
        return userDetails;
    }

    public Long getLoginUserPk() {
        return getLoginUser().getIuser();
    }

    public boolean isLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("황주은 - Authentication auth = "+auth);
        if(auth != null){
            return true;
        }
        return false;
    }
}
