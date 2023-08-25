package com.sbp.copyrightStreet;

import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestAppTest {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입")
    void contextLoads() {
        String username = "user1";
        String password = "1234";
        String email = "test@test.com";
        String loginId = "tester";
        String phoneNumber = "00011112222";
        memberService.join(username, password, email, loginId, phoneNumber);
    }
}
