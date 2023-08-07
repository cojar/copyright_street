package com.sbp.copyrightStreet.boundedContext.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    Page<Store> findAll(Pageable pageable);



}