package com.sbp.copyrightStreet.boundedContext.home.controller.Cart;

import com.sbp.copyrightStreet.boundedContext.home.controller.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
