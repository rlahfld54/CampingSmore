package com.green.campingsmore.admin.user;

import com.green.campingsmore.CommonRes;
import com.green.campingsmore.admin.user.model.UserDto;
import com.green.campingsmore.sign.SignService;
import com.green.campingsmore.sign.model.SignInResultDto;
import com.green.campingsmore.sign.model.UserLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "관리자 유저 관리 페이지")
@RequestMapping("/api/admin")
public class AdminUserController {
    // 관리자 로그아웃은 유저 로그아웃과 동일한 것을 쓴다.
    private final SignService signService;
    private final AdminUserService SERVICE;

    @PostMapping("/oauth/authorize")
    @Operation(summary = "관리자 로그인",
            description = "Try it out -> Execute 눌러주세요 \n\n " +
                    "id:  아이디 \n\n " +
                    "password : 비밀번호 \n\n "
    )
    public SignInResultDto signIn(HttpServletRequest req, @RequestBody UserLogin userLogin){
        String id = userLogin.getUid();
        String password = userLogin.getUpw();
        String ip = req.getRemoteAddr();
        log.info("[signIn] 로그인을 시도하고 있습니다. id: {}, pw: {}, ip: {}",id, password, ip);

        return SERVICE.adminSignIn(userLogin,ip);
    }

    @GetMapping("/users")
    @Operation(summary = "관리자 - 모든 유저 리스트 보기",
            description = "Try it out -> Execute 눌러주세요 \n\n "
    )
    public List<UserDto> selectAllusers(){
        return SERVICE.selectAllusers();
    }


}
