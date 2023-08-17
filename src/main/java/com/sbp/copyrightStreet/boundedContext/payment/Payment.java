package com.sbp.copyrightStreet.boundedContext.payment;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Setter
    @Column
    private String paymentKey;  //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num", nullable = false, unique = true)
    private Long num;   //순번

    @Column(nullable = false)
    private Long amount;   //가격 basic:15000, primium:29000, pro:49000

    @Column(nullable = false)
    private String orderNum; //주문번호

    @Column(nullable = false)
    private String orderName;  //상품이름

    @Column(nullable = false)
    private String customerEmail; //주문자이메일

    @Column(nullable = false)
    private String customerName;  //주문자이름

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;  // 결제날짜

    @OneToOne
    @JoinColumn(name="loginId")
    private Member member;


}
