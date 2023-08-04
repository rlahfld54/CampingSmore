//package com.green.campingsmore.admin.user;
//
//import com.green.campingsmore.CommonRes;
//import com.green.campingsmore.sign.model.SignInResultDto;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseCookie;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@Tag(name = "관리자 유저 관리 페이지")
//@RequestMapping("/admin")
//public class AdminUserController {
//
//    @PostMapping("/sign-in")
//    @Operation(summary = "로그인",
//            description = "Try it out -> Execute 눌러주세요 \n\n " +
//                    "id:  아이디 \n\n " +
//                    "password : 비밀번호 \n\n "
//    )
//    public SignInResultDto signIn(HttpServletRequest req, @RequestParam String id, @RequestParam String password){
//
//        String ip = req.getRemoteAddr();
//        log.info("[signIn] 로그인을 시도하고 있습니다. id: {}, pw: {}, ip: {}", id, password, ip);
//
//        SignInResultDto dto = SERVICE.signIn(id, password, ip);
//        if (dto.getCode() == CommonRes.SUCCESS.getCode()) {
//            log.info("[signIn] 정상적으로 로그인 되었습니다. id: {}, token: {}", id, dto.getAccessToken());
//        }
//
//        return dto;
//    }
//
//    @PostMapping("/logout")
//    @Operation(summary = "로그아웃",
//            description = "Try it out -> Execute 눌러주세요 \n\n "
//    )
//    public ResponseEntity<?> logout(HttpServletRequest req) {
//        SERVICE.logout(req);
//        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
//                .maxAge(0)
//                .path("/")
//                .build();
//
//        log.info("// ResponseCookie :{}",responseCookie);
//        log.info("// 로그아웃 완료!!!");
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
//                .build();
//    }
//}
