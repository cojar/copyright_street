package com.sbp.copyrightStreet.boundedContext.store;

import com.sbp.copyrightStreet.boundedContext.cart.Cart;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    Page<Store> findAll(Pageable pageable);

    List<Store> findByMember(Member member);
    Page<Store> findAll(Specification<Store> spec, Pageable pageable);

}