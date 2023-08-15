//package com.sbp.copyrightStreet.base.security;
//
//
//import com.sbp.copyrightStreet.boundedContext.member.Member;
//import com.sbp.copyrightStreet.boundedContext.member.MemberService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collection;
//import java.util.Map;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//@Slf4j
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//    private final MemberService memberService;
//
//    // 카카오톡 로그인이 성공할 때 마다 이 함수가 실행된다.
//    @Override
//    @Transactional
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        String oauthId = oAuth2User.getName();
//
//        String providerTypeCode = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
//
//        String username = providerTypeCode + "__%s".formatted(oauthId);
//
//        Member member = memberService.whenSocialLogin(providerTypeCode, username).getData();
//
//        return new CustomOAuth2User(member.getUsername(), member.getPassword(), member.getGrantedAuthorities());
//    }
//}
//
//class CustomOAuth2User extends User implements OAuth2User {
//
//    public CustomOAuth2User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return null;
//    }
//
//    @Override
//    public String getName() {
//        return getUsername();
//    }
//}