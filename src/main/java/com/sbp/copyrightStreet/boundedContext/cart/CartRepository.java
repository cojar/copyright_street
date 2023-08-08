package com.sbp.copyrightStreet.boundedContext.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByStoreId(Integer StoreId);
}
