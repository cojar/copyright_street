package com.sbp.copyrightStreet.boundedContext.cart;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.store.Store;
import com.sbp.copyrightStreet.boundedContext.store.StoreRepository;
import com.sbp.copyrightStreet.boundedContext.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<Cart> getList(Member member) {

        return cartRepository.findByMember(member);
    }

    public void deleteCartItem(Integer id) {
        cartRepository.deleteById(id);
    }

    public void deleteCartItemsByStoreId(Integer storeId) {
        List<Cart> cartList = cartRepository.findByStoreId(storeId);
        cartRepository.deleteAll(cartList);
    }

    public Cart getCartFromStore(Store store) {
        Cart cart = new Cart();
        cart.setTitle(store.getTitle());
        cart.setContent(store.getContent());
        cart.setCreate_Date(LocalDateTime.now());
        cart.setFilepath(store.getFilepath());
        cart.setFilename(store.getFilename());
        cart.setCategory(store.getCategory());
        cart.setStore(store);
        return cart;
    }

    public void add(Cart cart) {
        cartRepository.save(cart);
    }

}
