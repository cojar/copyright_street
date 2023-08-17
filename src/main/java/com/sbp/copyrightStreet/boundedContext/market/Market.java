package com.sbp.copyrightStreet.boundedContext.market;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String info;

    @OneToOne
    private Member member;

    @OneToMany(mappedBy = "market", cascade = CascadeType.REMOVE)
    private List<Product> productList;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}

