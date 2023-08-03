//package com.green.campingsmore.config.security;
//
//import com.green.campingsmore.config.security.model.SignUpDto;
//import org.junit.jupiter.api.Test;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@MybatisTest
//@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class UserDetailsMapperTest {
//
//    @Autowired
//    private UserDetailsMapper mapper;
//
//    @Test
//    void save() {
//        SignUpDto dto = new SignUpDto();
//        dto.setUid("testid");
//        dto.setName("황주은");
//        dto.setUpw("0000");
//        dto.setEmail("rlahfld54@naver.com");
//        dto.setBirth_date("2023-08-02");
//        dto.setPhone("010-2552-1549");
//        dto.setRole("ROLE_USER");
//        dto.setUser_address("대구광역시 중구");
//
//        int r1 = mapper.save(dto);
//        assertEquals(1,r1);
//    }
//
//    @Test
//    void getByUid() {
//    }
//
//    @Test
//    void updUserToken() {
//    }
//
//    @Test
//    void selUserToken() {
//    }
//
//    @Test
//    void delYnUser() {
//    }
//
//    @Test
//    void searchID() {
//    }
//
//    @Test
//    void updateUserInfo() {
//    }
//
//    @Test
//    void searchPW() {
//    }
//}