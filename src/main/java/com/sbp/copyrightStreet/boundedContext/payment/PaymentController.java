package com.sbp.copyrightStreet.boundedContext.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/membership")
public class PaymentController {
    @GetMapping("/price")
    public String payment() {
        return "membership/price";
    }
}
