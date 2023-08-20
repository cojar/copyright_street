package com.sbp.copyrightStreet.boundedContext.cart;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<Cart> getCartItems() {

        return cartRepository.findAll();
    }

    public void deleteCartItem(Integer id) {
        cartRepository.deleteById(id);
    }

    public void deleteCartItemsByStoreId(Integer storeId) {
        List<Cart> cartList = cartRepository.findByStoreId(storeId);
        cartRepository.deleteAll(cartList);
    }

    public void add(Product product, Member member) {
        Cart c = new Cart();
        c.setProduct(product);
        c.setMember(member);
        c.setCreateDate(LocalDateTime.now());
        this.cartRepository.save(c);
    }
}
