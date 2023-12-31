package com.sbp.copyrightStreet.boundedContext.member;

import com.sbp.copyrightStreet.base.rq.Rq;
import com.sbp.copyrightStreet.boundedContext.cart.Cart;
import com.sbp.copyrightStreet.boundedContext.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/member") // 액션 URL의 공통 접두어
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;
    private final Member member;
    private final CartService cartService;
//    private final StoreService storeService;

    @PreAuthorize("isAnonymous()") // 오직 로그인 안한 사람만 접근 가능하다.
    @GetMapping("/join") // 회원가입 폼
    public String showJoin() {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String signup(@Valid JoinForm joinForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            for (int i = 0; i < bindingResult.getErrorCount(); i++) {
                System.out.println(bindingResult.getAllErrors().get(i));
            }//.
            return "member/join";
            ///
        }

        if (!joinForm.getPassword().equals(joinForm.getPassword2())) {
            System.out.println("password confirm error");
            bindingResult.rejectValue("passwordCheck", "passwordInCorrect",
                    "입력한 비밀번호가 일치하지 않습니다.");
            return "member/join";
        }

        memberService.join(joinForm.getUsername(), joinForm.getLoginId(), joinForm.getPassword(), joinForm.getEmail(), joinForm.getPhoneNumber());

        redirectAttributes.addFlashAttribute("signupSuccess", true);
        return "redirect:/member/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login") // 로그인 폼, 로그인 폼 처리는 스프링 시큐리티가 구현, 폼 처리시에 CustomUserDetailsService 가 사용됨
    public String showLogin() {
        return "member/login";
    }

    @PreAuthorize("isAuthenticated()") // 로그인 해야만 접속가능
    @GetMapping("/me") // 로그인 한 나의 정보 보여주는 페이지
    public String showMe(Model model , Principal principal) {
        Optional<Member> member =this.memberService.findByUsername(principal.getName());
        List<Cart> cartList = this.cartService.getList(member.get());
//        List<Store> storeList = this.storeService.list(member.get());
        model.addAttribute("cartList",cartList);
        model.addAttribute("memberID",member.get().getId());
//        model.addAttribute("storeList",storeList);
        return "member/me";

    }





}


