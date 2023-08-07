package com.sbp.copyrightStreet.boundedContext.home.controller;

import com.sbp.copyrightStreet.boundedContext.store.Store;
import com.sbp.copyrightStreet.boundedContext.store.StoreService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Enumeration;

@Controller
@RequiredArgsConstructor

public class HomeController {

    private final StoreService storeService;
    @GetMapping("/")
    public String showMain() {
        return "main";
    }

    @GetMapping("/debugSession")
    @ResponseBody
    public String showDebugSession(HttpSession session) {
        StringBuilder sb = new StringBuilder("Session content:\n");
////
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
        return "store/copy_store";
    }
    @GetMapping("/copy/list")
    public String list(){

        return "store/copy_list";
    }
}
