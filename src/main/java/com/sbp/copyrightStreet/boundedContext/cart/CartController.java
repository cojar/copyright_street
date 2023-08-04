package com.sbp.copyrightStreet.boundedContext.cart;

import com.sbp.copyrightStreet.boundedContext.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final StoreService storeService;
    private final CartService cartService;

    @GetMapping("/store/cart")
    public String cart(Model model) {
        List<Cart> cartItems = cartService.getCartItems();
        model.addAttribute("cartItems", cartItems);
        return "usr1/home/copy_cart";
    }

    @PostMapping("/store/cart")
    public String addToCart(@RequestParam("id") Integer id) {
        this.storeService.cart(id);
        return "redirect:/store/cart";
    }
}