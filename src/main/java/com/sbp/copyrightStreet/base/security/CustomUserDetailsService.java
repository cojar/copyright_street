package com.sbp.copyrightStreet.base.security;


import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberRepository;
import com.sbp.copyrightStreet.util.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Trying to find user with loginId: " + username);

        Optional<Member> _member = memberRepository.findByLoginId(username);
        if (_member.isEmpty()) {
            System.out.println("User not found.");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        Member member = _member.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(member.getLoginId())) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else if ("artist".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ARTIST.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
