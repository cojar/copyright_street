package com.sbp.copyrightStreet.boundedContext.hash;

import com.sbp.copyrightStreet.boundedContext.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Hash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String keyword;

    @ManyToOne
    private Product product;


}
