package com.sbp.copyrightStreet.boundedContext.product;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
