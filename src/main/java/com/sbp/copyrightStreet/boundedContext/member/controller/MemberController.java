package com.sbp.copyrightStreet.boundedContext.member.controller;

import com.sbp.copyrightStreet.base.rq.Rq;
import com.sbp.copyrightStreet.base.rsData.RsData;
import com.sbp.copyrightStreet.boundedContext.member.entity.Member;
import com.sbp.copyrightStreet.boundedContext.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member") // 액션 URL의 공통 접두어
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @PreAuthorize("isAnonymous()") // 오직 로그인 안한 사람만 접근 가능하다.
    @GetMapping("/join") // 회원가입 폼
    public String showJoin() {

        return "usr/member/join";
    }

    @AllArgsConstructor // @Setter 도 가능, 데이터를 저장할 방편을 마련하기 위해서
    @Getter // joinForm.getUsername() 이런 코드 가능하게
    public static class JoinForm {
        @NotBlank(message = "사용자ID는 필수항목입니다.")
        @Size(min = 4, max = 30) // 4자 이상, 30자 이하
        private final String username;
        @NotBlank(message = "비밀번호는 필수항목입니다.")
        @Size(min = 4, max = 30)
        private final String password1;

        @Size(min = 4, max = 30)
        @NotBlank(message = "비밀번호확인은 필수항목입니다.")
        private final String password2;

        @Size(min = 4, max = 30)
        @NotBlank(message = "이메일은 필수항목입니다.")
        @Email
        private final String email;

        @Size(min = 4, max = 30)
        @NotBlank
        private final String birth;

    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm, BindingResult bindingResult) { // @Valid 가 없으면 @NotBlank 등이 작동하지 않음, 만약에 유효성 문제가 있다면 즉시 정지
        RsData<Member> joinRs = memberService.join(joinForm.getUsername(), joinForm.getPassword1());
        if(bindingResult.hasErrors()){
            return "usr/member/join";
        }
        if(joinForm.getPassword1().equals(joinForm.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect","비밀번호가 일치하지 않습니다.");
            return "usr/member/join";
        }
        if (joinRs.isFail()) {
            // 뒤로가기 하고 거기서 메세지 보여줘
            return rq.historyBack(joinRs);
        }

        // 아래 링크로 리다이렉트(302, 이동) 하고 그 페이지에서 메세지 보여줘
        return rq.redirectWithMsg("/member/login", joinRs);
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login") // 로그인 폼, 로그인 폼 처리는 스프링 시큐리티가 구현, 폼 처리시에 CustomUserDetailsService 가 사용됨
    public String showLogin() {

        return "usr/member/login";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 해야만 접속가능
    @GetMapping("/me") // 로그인 한 나의 정보 보여주는 페이지
    public String showMe() {

        return "usr/member/me";
    }
}
