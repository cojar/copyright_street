package com.sbp.copyrightStreet.boundedContext.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
