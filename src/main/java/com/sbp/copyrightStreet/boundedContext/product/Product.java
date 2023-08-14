package com.sbp.copyrightStreet.boundedContext.product;

import com.sbp.copyrightStreet.boundedContext.hash.Hash;
import com.sbp.copyrightStreet.boundedContext.market.Market;
import com.sbp.copyrightStreet.boundedContext.storequestion.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "item")
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private int price;

    private int hitCount;

    private String isActive;

    private String description;//설명

    @ManyToOne
    private Market market;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Question> questionList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Hash> hashList;

    private LocalDateTime regTime; //상품 등록시간

    private LocalDateTime modifyTime; //상품 수정시간
}
