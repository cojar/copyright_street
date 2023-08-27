package com.sbp.copyrightStreet.boundedContext.admin;

import com.sbp.copyrightStreet.boundedContext.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Product, Long> {
}
