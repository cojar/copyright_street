package com.sbp.copyrightStreet.boundedContext.product;

import com.sbp.copyrightStreet.boundedContext.hash.Hash;
import com.sbp.copyrightStreet.boundedContext.market.Market;
import com.sbp.copyrightStreet.boundedContext.storequestion.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
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

    @Column
    private String isActive;

    private String description;//설명

    @ManyToOne
    private Market market;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Question> questionList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Hash> hashList;

    private String thumbnailImg;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;
}
