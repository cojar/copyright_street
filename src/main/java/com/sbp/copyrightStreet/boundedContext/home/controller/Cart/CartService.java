package com.sbp.copyrightStreet.boundedContext.home.controller.Cart;

import com.sbp.copyrightStreet.boundedContext.home.controller.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<Cart> getCartItems() {
        return cartRepository.findAll();
    }
}
