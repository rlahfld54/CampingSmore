package com.green.campingsmore.sign;

import com.green.campingsmore.CommonRes;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.security.model.LoginDto;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.config.security.model.SignUpDto;
import com.green.campingsmore.sign.model.SignInResultDto;
import com.green.campingsmore.sign.model.SignUpResultDto;
import com.green.campingsmore.sign.model.UpdatePwDto;
import com.green.campingsmore.sign.model.UpdateUserInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "회원")
@RequestMapping("/sign-api")
public class SignController {
    private final SignService SERVICE;

    //ApiParam은 문서 자동화를 위한 Swagger에서 쓰이는 어노테이션이고
    //RequestParam은 http 로부터 요청 온 정보를 받아오기 위한 스프링 어노테이션이다.
    //@AuthenticationPrincipal을 통해 로그인한 사용자 정보,PK를 받아 사용할 수 있다.
    @PostMapping("/sign-in")
    @Operation(summary = "로그인",
            description = "Try it out -> Execute 눌러주세요 \n\n " +
                    "id:  아이디 \n\n " +
                    "password : 비밀번호 \n\n "
    )
    public SignInResultDto signIn(HttpServletRequest req, @RequestParam String id, @RequestParam String password) throws RuntimeException {

        String ip = req.getRemoteAddr();
        log.info("[signIn] 로그인을 시도하고 있습니다. id: {}, pw: {}, ip: {}", id, password, ip);

        SignInResultDto dto = SERVICE.signIn(id, password, ip);
        if (dto.getCode() == CommonRes.SUCCESS.getCode()) {
            log.info("[signIn] 정상적으로 로그인 되었습니다. id: {}, token: {}", id, dto.getAccessToken());
        }

        return dto;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입",
            description = "Try it out -> Execute 눌러주세요 \n\n " +
                    "uid: 회원가입 아이디 \n\n " +
                    "upw : 비밀번호 ex)1234 \n\n " +
                    "email : 이메일 ex)rlahfld54@gmail.com \n\n " +
                    "name: 이름 \n\n " +
                    "birth_date: 생년월일 ex)1998-06-12 \n\n " +
                    "phone: 핸드폰 번호 \n\n " +
                    "user_address: 주소 \n\n " +
                    "role: USER 이거나 ADMIN\n\n "
    )
    public SignUpResultDto signUp(@RequestBody SignUpDto signUpDto) {
        log.info("[signUp] 회원가입을 수행합니다. id: {}, pw: {}, nm: {}, role: {}", signUpDto.getUid(), signUpDto.getUpw(), signUpDto.getName(), signUpDto.getRole());
        SignUpResultDto dto = SERVICE.signUp(signUpDto);
        log.info("[signUp] 회원가입 완료 id: {}", signUpDto.getUid());
        return dto;
    }

    @GetMapping("/refresh-token")
    @Operation(summary = "리프레쉬 토큰",
            description = "Try it out -> Execute 눌러주세요 \n\n " +
                    "refreshToken :  리프레쉬 토큰 \n\n "
    )
    public ResponseEntity<SignUpResultDto> refreshToken(HttpServletRequest req, @RequestParam String refreshToken) {
        SignUpResultDto dto = SERVICE.refreshToken(req, refreshToken);
        return dto == null ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null) : ResponseEntity.ok(dto);
    }

    @GetMapping("/search-id")
    @Operation(summary = "아이디 찾기",
            description = "Try it out -> Execute 눌러주세요 \n\n " +
                    "user_id :  아이디 찾기 \n\n "
    )
    public String searchID(@RequestParam String name
            , @RequestParam String phone
            , @RequestParam String birth
    ) {
        return SERVICE.searchID(name, phone, birth);
    }

    @DeleteMapping("/delete-user")
    @Operation(summary = "회원탈퇴",
            description = "Try it out -> Execute 눌러주세요 \n\n " +
                    "iuser:  iuser PK \n\n "
    )
    public void deleteUser(@RequestParam int iuser) {
        SERVICE.deleteUser(iuser);
    }


    @PostMapping("/updateUserInfo")
    @Operation(summary = "회원 정보 수정 => 회원이 자신의 정보를 수정할 수 있도록 하는 것",
            description = "Try it out -> Execute 눌러주세요 \n\n "+
                    "아이디,이름은 못 바꾸고 프론트에서도 고정시켜야함 \n\n "+
                    "프론트에서 로그인후 아이디를 백엔드로 보내줘야함- 본인인증..? \n\n "+
                    "uid : 아이디 => 프론트에서 아이디 보내줘야함\n\n " +
                    "upw : 비밀번호 \n\n " +
                    "email : 이메일\n\n " +
                    "name: 이름 \n\n " +
                    "birth_date: 생년월일 \n\n " +
                    "phone: 핸드폰 번호 \n\n " +
                    "user_address: 주소 \n\n "
    )
    public int updateUserInfo(@AuthenticationPrincipal MyUserDetails user,@RequestBody UpdateUserInfoDto updateUserInfoDto) {
        // 로그인 했을때만 수정할 수 있도록 해야함  // 본인 자신만 수정할 수 있도록 해야함..
        log.info("controller-iuser {}", user.getIuser());
        SERVICE.test();

        System.out.println(updateUserInfoDto);
        return SERVICE.updateUserInfo(updateUserInfoDto);
    }

    @PostMapping("/search-pw")
    @Operation(summary = "비밀번호 찾기 - 이메일로 임시 비밀번호 제공",
            description = "Try it out -> Execute 눌러주세요 \n\n " +
                        "user_id :  아이디 \n\n "+
                        "name :  이름 \n\n "+
                        "email :  이메일 \n\n "
    )
    public int searchPW(@RequestParam String id,
                        @RequestParam String name,
                        @RequestParam String email
                        ) {
        return SERVICE.searchPW(id,name,email);
    }
}
