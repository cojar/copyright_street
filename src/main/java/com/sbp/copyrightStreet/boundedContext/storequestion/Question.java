package com.sbp.copyrightStreet.boundedContext.storequestion;



import com.sbp.copyrightStreet.base.BaseEntity;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
public class Question extends BaseEntity {
    private String content;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Product product;
}
