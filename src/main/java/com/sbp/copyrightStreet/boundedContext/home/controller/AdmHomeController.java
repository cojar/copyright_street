package com.sbp.copyrightStreet.boundedContext.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm")
public class AdmHomeController {
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
    public String index() {
        return "redirect:/adm/home/main";
    }

    @GetMapping("/home/main")
    @PreAuthorize("hasAuthority('ADMIN')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
    public String showMain(){
        return "adm/home/main";
    }
}
