package com.sbp.copyrightStreet.boundedContext.home.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Enumeration;
import java.util.List;

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
    public String store(Model model) {
        List<Store>storeList = this.storeService.getList();
        model.addAttribute("storeList",storeList);
        return "usr/home/copy_store";
    }
    @GetMapping("/copy/list")
    public String list(){

        return "usr/home/copy_list";
    }
}
