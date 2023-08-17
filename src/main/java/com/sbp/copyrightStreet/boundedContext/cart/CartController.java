package com.sbp.copyrightStreet.boundedContext.cart;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import com.sbp.copyrightStreet.boundedContext.store.Store;
import com.sbp.copyrightStreet.boundedContext.store.StoreRepository;
import com.sbp.copyrightStreet.boundedContext.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class CartController {
    private final StoreService storeService;
    private final CartService cartService;
    private final MemberService memberService;
    private final StoreRepository storeRepository;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cart")
    public String cart( Model model,Principal principal) {
        Optional<Member> member = memberService.findByUsername(principal.getName());
        Optional<Cart> cartItems = cartService.getCartItems(member.get());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("memberId",member.get().getId());
        return "store/cart";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/cart")
    public String addToCart(@RequestParam("id") Integer id,Principal principal) {
        Optional<Member> member = memberService.findByUsername(principal.getName());
        Optional<Store> store = this.storeRepository.findById(id);
        Cart cart =this.cartService.getCartFromStore(store.get());
        cart.setMember(member.get());//멤버에 저장.
        this.cartService.add(cart);

        return "redirect:/store/cart";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/store/cart/delete/{id}")
    public String deleteCartItem(@PathVariable("id") Integer id) {
        this.cartService.deleteCartItem(id); // 카트 내용 삭제 메서드 추가
        return "redirect:/store/cart";
    }
}