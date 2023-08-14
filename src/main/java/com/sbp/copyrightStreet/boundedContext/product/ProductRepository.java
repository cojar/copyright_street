package com.sbp.copyrightStreet.boundedContext.product;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select "
            + "distinct p "
            + "from Product p "
            + "where "
            + "   p.name like %:kw% "
            + "   or p.description like %:kw% ")
    Page<Product> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
