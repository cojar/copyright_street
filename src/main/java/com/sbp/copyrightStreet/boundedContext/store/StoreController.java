package com.sbp.copyrightStreet.boundedContext.store;


import com.sbp.copyrightStreet.boundedContext.cart.Cart;
import com.sbp.copyrightStreet.boundedContext.cart.CartRepository;
import com.sbp.copyrightStreet.boundedContext.file.File;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;
    private final CartRepository cartRepository;


    @GetMapping("/list")
    public String store(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Store> paging = this.storeService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "store/list";
    }
    @GetMapping("/copy/list")
    public String list(){

        return "store/list";
    }
    @GetMapping("/create")
    public String create(Store store) {

        return "store/form";
    }

    @PostMapping("/create")
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         @RequestParam String category,
                         @RequestParam(required = false) MultipartFile file) throws Exception {
        this.storeService.create(title, content, category, file);
        return "redirect:/store/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        StoreService.increaseHitCount();

        Store store = this.storeService.getStore(id);
        model.addAttribute("store", store);
        model.addAttribute("id", id); // id 값을 모델에 추가하여 장바구니에 추가할 때 사용
        return "store/detail";
    }

    @GetMapping("/modify/{id}")
    public String modify(Store store)
    {

        return "store/modify";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id, @RequestParam String title, @RequestParam String content, @RequestParam String category, @RequestParam(required = false) MultipartFile file)
            throws Exception {
        Store store = this.storeService.getStore(id);
        this.storeService.modify(id, title, content, category, file);
        return "redirect:/store/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        this.storeService.delete(id);
        return "redirect:/store/list";
    }



}
