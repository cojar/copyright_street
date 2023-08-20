package com.sbp.copyrightStreet.boundedContext.mypage;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberModifyForm;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myProfile")
    public String mypage(Model model, Principal principal) {
        Member member = this.memberService.getUser(principal.getName());
        model.addAttribute("member",member);
        return "member/me";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(MemberModifyForm memberModifyForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "member/me";
        }

        Member member = this.memberService.getUser(principal.getName());
        if (!memberService.confirmPassword(memberModifyForm.getPassword(), member)) {
            bindingResult.rejectValue("password", "passwordInCorrect",
                    "현재 비밀번호를 바르게 입력해주세요.");
            return "member/me";
        }

        // 비밀번호와 비밀번호 확인에 입력한 문자열이 서로 다르면 다시 입력 하도록
        if (!memberModifyForm.getNewPW().equals(memberModifyForm.getNewPW2())) {
            bindingResult.rejectValue("newPW2", "passwordInCorrect",
                    "입력한 비밀번호가 일치하지 않습니다.");
            return "member/me";
        }
        memberService.modifyPassword(memberModifyForm.getNewPW(), member);

        return "redirect:/member/me";
    }

}





