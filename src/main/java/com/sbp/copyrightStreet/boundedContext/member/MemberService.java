package com.sbp.copyrightStreet.boundedContext.member;

import com.sbp.copyrightStreet.DataNotFoundException;
import com.sbp.copyrightStreet.base.rsData.RsData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;


    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional // SELECT 이외의 쿼리에 대한 가능성이 아주 조금이라도 있으면 붙인다.
    // 일반 회원가입(소셜 로그인을 통한 회원가입이 아님)
    public RsData<Member> join(String username, String loginId, String password, String email, String phoneNumber) {
        // "GRAMGRAM" 해당 회원이 일반회원가입으로 인해 생성되었다는걸 나타내기 위해서
        return join("copyrightStreet", username, loginId, password, phoneNumber, email);
    }

    // 내부 처리함수, 일반회원가입, 소셜로그인을 통한 회원가입(최초 로그인 시 한번만 발생)에서 이 함수를 사용함
    private RsData<Member> join(String providerTypeCode, String username, String loginId, String password, String phoneNumber, String email) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("F-1", "해당 아이디(%s)는 이미 사용중입니다.".formatted(username));
        }

        // 소셜 로그인을 통한 회원가입에서는 비번이 없다.
        if (StringUtils.hasText(password)) password = passwordEncoder.encode(password);

        Member member = Member
                .builder()
                .providerTypeCode(providerTypeCode)
                .loginId(loginId)
                .username(username)
                .phoneNumber(phoneNumber)
                .password(password)
                .email(email)
                .createDate(LocalDateTime.now())
                .build();

        memberRepository.save(member);

        return RsData.of("S-1", "회원가입이 완료되었습니다.", member);
    }


    // 소셜 로그인(카카오, 구글, 네이버) 로그인이 될 때 마다 실행되는 함수
    @Transactional
    public RsData<Member> whenSocialLogin(String providerTypeCode, String username) {
        Optional<Member> opMember = findByUsername(username);

        if (opMember.isPresent()) return RsData.of("S-2", "로그인 되었습니다.", opMember.get());

        // 소셜 로그인를 통한 가입시 비번은 없다.
        return join(providerTypeCode, username, "", "", ""); // 최초 로그인 시 딱 한번 실행
    }

    public Member getUserByLoginId(String loginId) {
        Optional<Member> memberInfo = this.memberRepository.findByLoginId(loginId);
        if (memberInfo.isPresent()) {
            return memberInfo.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }


    public Member getUser(String username) {
        Optional<Member> member = this.memberRepository.findByUsername(username);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("Member not found");
        }
    }

    public Member modifyPassword(String password, Member member) {
        member.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(member);
        log.info("비밀번호저장");
        return member;

    }

    public boolean confirmPassword(String password, Member member) {
        if (password == null) {
            return false; // 또는 예외 처리 등의 방법으로 처리할 수 있습니다.
        }
        return passwordEncoder.matches(password, member.getPassword());
    }
    public List<Member> getAll() {
        return this.memberRepository.findAll();
    }
    public void updateProfileImage(String username, String imageUrl) {
        Member member = getUser(username);
//            member.setProfileImage(imageUrl);
        memberRepository.save(member);
    }
}