package com.sbp.copyrightStreet.boundedContext.member;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// MemberRepository => member 테이블을 다루는 클래스
// JpaRepository<Member, Long> => Member == member 테이블, Long == member 의 주키의 타입
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByEmail(String email);


}