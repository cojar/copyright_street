package com.sbp.copyrightStreet.boundedContext.product;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/product")
public class AdmProductController {
    private final ProductService productService;
//


    @GetMapping("adm/product/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        Product product = productService.getProduct(id);
        System.out.println(product.toString());
        model.addAttribute("product", product);

        return "adm/product/detail";
    }

    @GetMapping("/create")
    public String create(Model model){
        List<Product> productList = this.productService.getList();
        model.addAttribute("productList",productList);
        return "adm/product/create";
    }

    @PostMapping("/create")
    public String createContent(@RequestParam String name, @RequestParam String description,@RequestParam int price,
                                MultipartFile thumbnail){
        Long createdProductId = this.productService.create(name, description, price, thumbnail);
        return "redirect:/adm/product/detail/" + createdProductId;
    }

}
