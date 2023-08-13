package com.sbp.copyrightStreet.boundedContext.membership;

import org.springframework.ui.Model;
import com.sbp.copyrightStreet.boundedContext.payment.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Membership")
public class MembershipController {
    @GetMapping("/Price")
    public String Price(Model model) {
        Payment payment = new Payment();

        model.addAttribute("payment", payment);

        return "membership/price";
    }
}
