package com.sbp.copyrightStreet.boundedContext.storequestion;


import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Question  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Product product;

    private LocalDateTime regTime;

    private LocalDateTime modifyTime;


}
