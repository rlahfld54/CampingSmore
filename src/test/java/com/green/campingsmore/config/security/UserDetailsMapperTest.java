package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.SignUpDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDetailsMapperTest {
    @Autowired
    private UserDetailsMapper mapper;

    @Test
    void signUp() {
        // given
        SignUpDto dto = new SignUpDto();
        dto.setUid("rlahfld54");
        dto.setUpw("0000");
        dto.setEmail("rlahfld54@gmail.com");
        dto.setName("황주은");
        dto.setBirth_date("1998-06-12");
        dto.setPhone("01025521540");
        dto.setGender(1);
        dto.setUser_address("경기 남양주시 화도읍 북한강로 1630-18");
        dto.setRole("ROLE_ADMIN");

        // when
        int result = mapper.signUp(dto);

        assertEquals(1,result);
    }
}