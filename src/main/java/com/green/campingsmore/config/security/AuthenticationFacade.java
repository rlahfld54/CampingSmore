package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public MyUserDetails getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();

        System.out.println("황주은 - AuthenticationFacade.getPrincipal() = "+auth.getPrincipal());
        System.out.println("황주은 - AuthenticationFacade.getPrincipal()의 MyUserDetails = "+userDetails);
        return userDetails;
    }

    public Long getLoginUserPk() {
        return getLoginUser().getIuser();
    }

    public boolean isLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            return true;
        }
        return false;
    }
}
