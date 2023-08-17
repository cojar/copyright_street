package com.sbp.copyrightStreet.boundedContext.mypage;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public List<Member> getList() {
        return this.memberRepository.findAll();
    }


}
