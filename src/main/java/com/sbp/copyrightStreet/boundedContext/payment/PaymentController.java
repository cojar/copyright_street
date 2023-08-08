package com.sbp.copyrightStreet.boundedContext.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {
    @GetMapping("/membership")
    public String payment(){
        return "membership/price";
    }
}
