package com.sbp.copyrightStreet.boundedContext.membership;


import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import com.sbp.copyrightStreet.boundedContext.payment.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import com.sbp.copyrightStreet.boundedContext.payment.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;


@Controller
@AllArgsConstructor
@RequestMapping("/membership")
public class MembershipController {

    private final MemberService memberService;
    private final PaymentService paymentService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/price")
    public String Price(Principal principal, Model model) {

        Payment payment = new Payment();
        model.addAttribute("payment", payment);

        Optional<Member> memberOptional = memberService.findByUsername(principal.getName());
        memberOptional.ifPresent(member -> model.addAttribute("member", member));

        return "membership/price";
    }

}