package com.sbp.copyrightStreet.boundedContext.product;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;


    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page,
                       @RequestParam(value="kw", defaultValue ="") String kw) {
        Page<Product> paging = productService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw",  kw);

        return "product/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        Product product = productService.getProduct(id);
        System.out.println(product.toString());
        model.addAttribute("product", product);

        return "product/detail";
    }

    @GetMapping("/create")
    public String create(){
        return "product/create";
    }

    @PostMapping("/create")
    public String createContent(@RequestParam String name, @RequestParam String description,@RequestParam int price,
                                MultipartFile thumbnail){
        this.productService.create(name,description,price,thumbnail);
        return "product/create";
    }

}
