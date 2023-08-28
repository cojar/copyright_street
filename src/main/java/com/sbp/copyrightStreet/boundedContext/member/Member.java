package com.sbp.copyrightStreet.boundedContext.member;


import com.sbp.copyrightStreet.base.BaseEntity;
import com.sbp.copyrightStreet.boundedContext.payment.Payment;
import com.sbp.copyrightStreet.boundedContext.article.borad.Board;
import com.sbp.copyrightStreet.boundedContext.cart.Cart;
import com.sbp.copyrightStreet.boundedContext.product.Product;
import com.sbp.copyrightStreet.boundedContext.storequestion.Question;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    private String providerTypeCode; // 일반회원인지, 카카오로 가입한 회원인지, 구글로 가입한 회원인지
    @Column(unique = true)
    private String username;
    private String password;
    @Column
    private String email;
    private String phoneNumber;

    private String nickname;

    @Column(unique = true)
    private String loginId;
//    private String userRole;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Cart> cartList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Question> questionList;

    @OneToMany(mappedBy = "member")
    private List<Payment> payment = new ArrayList<>();  //payment 타입의 객체리스트에 1:다 관계

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Board> boardList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Product> productList;


    private String profileImage;

    // 이 함수 자체는 만들어야 한다. 스프링 시큐리티 규격
    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // 모든 멤버는 member 권한을 가진다.
        grantedAuthorities.add(new SimpleGrantedAuthority("member"));

        // username이 admin인 회원은 추가로 admin 권한도 가진다.
        if ("admin".equals(username)) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }

        return grantedAuthorities;
    }
}