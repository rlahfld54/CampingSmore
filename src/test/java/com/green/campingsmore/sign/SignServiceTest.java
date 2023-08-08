package com.green.campingsmore.sign;

import com.green.campingsmore.config.security.UserDetailsMapper;
import com.green.campingsmore.config.security.model.LoginDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mariadb.jdbc.client.tls.HostnameVerifier.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import( { SignService.class })
class SignServiceTest {
    @MockBean
    private UserDetailsMapper mapper;

    @MockBean
    public SignService service;

    @Test
    void signUp() {
    }

    @Test // 로그인
    void signIn() {
        String id = "rlahfld54";
        String password = "0000";
        String ip = "1";

        LoginDto loginDto = new LoginDto();
        loginDto.setUpw(password);
        loginDto.setUid(id);
        when(mapper.getByUid(id)).thenReturn(loginDto);
    }

    @Test
    void logout() {
    }

    @Test
    void refreshToken() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void searchID() {
    }

    @Test
    void updateUserInfo() {
    }

    @Test
    void searchPW() {
    }
}