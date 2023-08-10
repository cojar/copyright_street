package com.sbp.copyrightStreet.boundedContext.store;


import com.sbp.copyrightStreet.boundedContext.cart.Cart;
import com.sbp.copyrightStreet.boundedContext.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final CartRepository cartRepository;


    @GetMapping("/store/create")
    public String create(Store store) {

        return "store/copy_form";
    }

    @PostMapping("/store/create")
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         @RequestParam String category,
                         @RequestParam(required = false) MultipartFile file) throws Exception {
        this.storeService.create(title, content, category, file);
        return "redirect:/copy/store";
    }

    @GetMapping("/store/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        StoreService.increaseHitCount();

        Store store = this.storeService.getStore(id);
        model.addAttribute("store", store);
        model.addAttribute("id", id); // id 값을 모델에 추가하여 장바구니에 추가할 때 사용
        return "store/copy_detail";
    }

    @GetMapping("/store/modify/{id}")
    public String modify(Store store)
    {
        return "store/copy_modify";
    }

    @PostMapping("/store/modify/{id}")
    public String modify(@PathVariable("id") Integer id, @RequestParam String title, @RequestParam String content, @RequestParam String category, @RequestParam(required = false) MultipartFile file)
            throws Exception {
        Store store = this.storeService.getStore(id);
        this.storeService.modify(id, title, content, category, file);
        return "redirect:/copy/store";
    }

    @GetMapping("/store/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        this.storeService.delete(id);
        return "redirect:/copy/store";
    }



}
