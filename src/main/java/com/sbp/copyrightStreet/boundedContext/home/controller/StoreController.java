package com.sbp.copyrightStreet.boundedContext.home.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;


    @GetMapping("/store/create")
    public String create(Store store){

        return "usr/home/copy_form";
    }
    @PostMapping("/store/create")
    public String create(@RequestParam String title, @RequestParam String content, @RequestParam MultipartFile file)throws Exception{
        this.storeService.create(title, content,file);
        return "redirect:/copy/store";
    }
    @GetMapping("/store/detail/{id}")
    public String detail(Model model,@PathVariable("id") Integer id){
        Store store =this.storeService.getStore(id);
        model.addAttribute("store",store);
        return "/usr/home/copy_detail";
    }
    @GetMapping("/store/modify/{id}")
    public String modify(Store store){
        return "usr/home/copy_modify";
    }

    @PostMapping("/store/modify/{id}")
    public String modify(@PathVariable("id") Integer id, @RequestParam String title, @RequestParam String content,@RequestParam(required = false) MultipartFile file)
            throws Exception{
       Store store= this.storeService.getStore(id);
        this.storeService.modify(id ,title, content, file);
        return "redirect:/copy/store";
    }


}
