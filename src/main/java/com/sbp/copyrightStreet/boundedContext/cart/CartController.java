package com.sbp.copyrightStreet.boundedContext.cart;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import com.sbp.copyrightStreet.boundedContext.product.Product;
import com.sbp.copyrightStreet.boundedContext.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final CartService cartService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String list (Principal principal, Model model) {
        Member member = memberService.getUser(principal.getName());
        List<Cart> cartList = this.cartService.getList(member);
        model.addAttribute("cartList", cartList);

        return "cart/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add/{id}")
    public String add (@PathVariable Long id, Principal principal) {
        Product product = productService.getProduct(id);
        Member member = memberService.getUser(principal.getName());

        this.cartService.add(product, member);

        return "redirect:/cart/list";
    }

    @GetMapping("/store/cart/delete/{id}")
    public String deleteCartItem(@PathVariable("id") Integer id) {
        this.cartService.deleteCartItem(id); // 카트 내용 삭제 메서드 추가
        return "redirect:/store/cart";
    }


}