package com.sbp.copyrightStreet.boundedContext.home.controller;

import com.sbp.copyrightStreet.boundedContext.store.Store;
import com.sbp.copyrightStreet.boundedContext.store.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Enumeration;
import java.util.HashMap;

@Slf4j
@Controller
@RequiredArgsConstructor

public class HomeController {

    private final StoreService storeService;
    @GetMapping("/")
    public String showMain() {
        return "usr/home/main";
    }

    @GetMapping("/debugSession")
    @ResponseBody
    public String showDebugSession(HttpSession session) {
        StringBuilder sb = new StringBuilder("Session content:\n");

        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            sb.append(String.format("%s: %s\n", attributeName, attributeValue));
        }

        return sb.toString().replaceAll("\n", "<br>");
    }
    @GetMapping("/copy/store")
    public String store(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Store> paging = this.storeService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "usr/home/copy_store";
    }
    @GetMapping("/copy/list")
    public String list(){

        return "usr/home/copy_list";
    }

//    @PostMapping("/copy/author")
//    public String author1(){
//        log.info("emial:");
//        return "usr/member/author";
//
//    }

    @PostMapping("/copy/author")
    public void author1(HttpServletRequest req, HttpServletResponse resp){
        log.info("emial:");
        log.info(req.getParameter("subject"));
    }



}
