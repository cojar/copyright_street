package com.sbp.copyrightStreet.boundedContext.cart;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByStoreId(Integer StoreId);

    Optional<Cart> findById(Member member);
}
