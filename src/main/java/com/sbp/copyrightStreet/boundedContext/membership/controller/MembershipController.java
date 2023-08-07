package com.sbp.copyrightStreet.boundedContext.membership.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Membership")
public class MembershipController {
    @GetMapping("/Price")
    public String Price(){
        return "membership/price";
    }


}
